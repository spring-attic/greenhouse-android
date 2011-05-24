package com.springsource.greenhouse.twitter;

import java.text.SimpleDateFormat;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.Tweet;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class TweetDetailsActivity extends AbstractGreenhouseActivity {
	
	protected static final String TAG = TweetDetailsActivity.class.getSimpleName();
	
	private Event event;
	
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
			      		// TODO: reply
			      		break;
			      	case 1:
			      		showRetweetDialog();
			      		break;
			      	case 2:
			      		// TODO: quote
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
		t.setText("@" + tweet.getFromUser());
		
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
		
	
	//***************************************
    // Private classes
    //***************************************
	private class RetweetTask extends AsyncTask<Void, Void, Void> {
		
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog("Retweeting..."); 
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				getApplicationContext().getGreenhouseApi().tweetOperations().retweet(event.getId(), tweet.getId());
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			dismissProgressDialog();
			processException(exception);
		}
	}

}
