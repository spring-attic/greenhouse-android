/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse.twitter;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class PostTweetActivity extends AbstractGreenhouseActivity {

	protected static final String TAG = PostTweetActivity.class.getSimpleName();
	
	private static final int MAX_TWEET_LENGTH = 140;
	
	private TextWatcher textWatcher;
	
	private TextView textViewCount;

	private Event event;
	
	private EventSession session;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_tweet);
		
		textViewCount = (TextView) this.findViewById(R.id.post_tweet_count);
		textWatcher = new TextWatcher() {
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	           textViewCount.setText(String.valueOf(MAX_TWEET_LENGTH - s.length()));
	        }

	        public void afterTextChanged(Editable s) {}
		};
		
		final EditText editText = (EditText) findViewById(R.id.post_tweet_text);
		editText.addTextChangedListener(textWatcher);
		
		final Button button = (Button) findViewById(R.id.post_tweet_button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
            	// hide the soft keypad
            	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            	EditText editText = (EditText) findViewById(R.id.post_tweet_text);
            	inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            	new PostTweetTask().execute();
            }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		event = getApplicationContext().getSelectedEvent();
		session = getApplicationContext().getSelectedSession();
		
		if (event == null) {
			return;
		}
		
		final EditText editText = (EditText) findViewById(R.id.post_tweet_text);
		String tweetText = null;
		
		if (getIntent().hasExtra("reply")) {
			tweetText = new StringBuilder().append("@").append(getIntent().getStringExtra("reply")).append(" ").append(event.getHashtag()).toString();
		} else if (getIntent().hasExtra("quote")) {
			tweetText = getIntent().getStringExtra("quote");
		} else {
			tweetText = event.getHashtag();
		}
		
		editText.setText(tweetText);
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void showResult(String result) {
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class PostTweetTask extends AsyncTask<Void, Void, String> 
	{	
		private String status;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog("Posting Tweet...");
			EditText editText = (EditText) findViewById(R.id.post_tweet_text);
			status = editText.getText().toString();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			try {
				getApplicationContext().getGreenhouseApi().tweetOperations().postTweetForEvent(event.getId(), status);
				return "Success";
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "An error occurred. See the log for details";
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			showResult(result);
		}
	}
	
}
