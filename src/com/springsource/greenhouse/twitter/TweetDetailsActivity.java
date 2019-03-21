/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse.twitter;

import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;
import org.springframework.social.greenhouse.api.Tweet;
import org.springframework.web.client.HttpClientErrorException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class TweetDetailsActivity extends AbstractGreenhouseActivity {
	
	protected static final String TAG = TweetDetailsActivity.class.getSimpleName();
	
	private Event event;
	
	private EventSession session;
	
	private Tweet tweet;
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tweet_details);
		
		final ListView listView = (ListView) findViewById(R.id.tweet_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.tweet_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	switch(position) {
			      	case 0:
			      		Intent replyIntent = new Intent(view.getContext(), PostTweetActivity.class);
			      		replyIntent.putExtra("reply", tweet.getFromUser());
			      		startActivity(replyIntent);
			      		break;
			      	case 1:
			      		showRetweetDialog();
			      		break;
			      	case 2:
			      		Intent quoteIntent = new Intent(view.getContext(), PostTweetActivity.class);
			      		String quote = new StringBuilder().append("\"@").append(tweet.getFromUser()).append(" ").append(tweet.getText()).append("\"").toString();
			      		quoteIntent.putExtra("quote", quote);
			      		startActivity(quoteIntent);
			      		break;
			      	default:
			      		break;
		    	}
		    }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		event = getApplicationContext().getSelectedEvent();
		session = getApplicationContext().getSelectedSession();
		tweet = getApplicationContext().getSelectedTweet();
		refreshTweetDetails();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshTweetDetails() {
		if (tweet == null) {
			return;
		}
		
		TextView t = (TextView) findViewById(R.id.tweet_details_fromuser);
		t.setText(tweet.getFromUser());
		
		t = (TextView) findViewById(R.id.tweet_details_time);
		t.setText(new SimpleDateFormat("MMM d h:mm a").format(tweet.getCreatedAt()));
		
		t = (TextView) findViewById(R.id.tweet_details_text);
		t.setText(tweet.getText());		
	}
	
	private void showRetweetDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to Retweet?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int id) {
		        	   retweet();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void retweet() {
		new RetweetTask().execute();
	}
	
	private void showResult(String result) {
//		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(result);
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
		     	dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
		
	
	//***************************************
    // Private classes
    //***************************************
	private class RetweetTask extends AsyncTask<Void, Void, String> {
		
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog("Retweeting..."); 
		}
		
		@Override
		protected String doInBackground(Void... params) {
			try {
				if (session != null) {
					getApplicationContext().getGreenhouseApi().tweetOperations().retweetForEventSession(event.getId(), session.getId(), tweet.getId());
				} else {
					getApplicationContext().getGreenhouseApi().tweetOperations().retweetForEvent(event.getId(), tweet.getId());
				}
				return "Thank you for tweeting about this event!";
			} catch(HttpClientErrorException e) {
				if (e.getStatusCode() == HttpStatus.PRECONDITION_FAILED) {
					return "Your account is not connected to Twitter. Please sign in to greenhouse.springsource.org to connect.";
				} else {
					Log.e(TAG, e.getLocalizedMessage(), e);
					return "A problem occurred while posting to Twitter. Please verify your account is connected at greenhouse.springsource.org.";
				}
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "A problem occurred while posting to Twitter. Please verify your account is connected at greenhouse.springsource.org.";
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			processException(exception);
			showResult(result);
		}
	}

}
