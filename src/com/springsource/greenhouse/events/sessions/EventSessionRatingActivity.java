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
package com.springsource.greenhouse.events.sessions;

import org.springframework.http.HttpStatus;
import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;
import org.springframework.web.client.HttpClientErrorException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class EventSessionRatingActivity extends AbstractGreenhouseActivity {
	
	private static final String TAG = EventSessionRatingActivity.class.getSimpleName();
	
	private static final int MAX_LENGTH = 140;
	
	private TextWatcher textWatcher;
	
	private TextView textViewCount;
	
	private Event event;
	
	private EventSession session;
	
	private int rating;
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_session_rating);
		
		textViewCount = (TextView) this.findViewById(R.id.event_session_rating_count);
		textWatcher = new TextWatcher() {
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	        public void onTextChanged(CharSequence s, int start, int before, int count) {
	           textViewCount.setText(String.valueOf(MAX_LENGTH - s.length()));
	        }

	        public void afterTextChanged(Editable s) {}
		};
		
		final EditText editText = (EditText) findViewById(R.id.event_session_rating_text);
		editText.addTextChangedListener(textWatcher);
		
		final Button submitButton = (Button) findViewById(R.id.event_session_rating_submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
            	// hide the soft keypad
            	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            	EditText editText = (EditText) findViewById(R.id.event_session_rating_text);
            	inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            	submitRating();
            }
		});
		
		final Button selectStartButton = (Button) findViewById(R.id.event_session_rating_select_star);
		selectStartButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showSelectStarRatingDialog();
            }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		event = getApplicationContext().getSelectedEvent();
		session = getApplicationContext().getSelectedSession();
		setRating(5);
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void showSelectStarRatingDialog() {
		final CharSequence[] items = {"5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select a Star Value");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	setRating(5 - item);
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
		
	private void setRating(int rating) {
		this.rating = rating;
		final TextView textView = (TextView) findViewById(R.id.event_session_rating_stars);
		textView.setText(getRating() + " Stars");		
	}
	
	private int getRating() {
		return rating;
	}
	
	private String getComment() {
		EditText editText = (EditText) findViewById(R.id.event_session_rating_text);
		return editText.getText().toString(); 
	}
	
	private void submitRating() {
		new SubmitRatingTask().execute();
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
	private class SubmitRatingTask extends AsyncTask<Void, Void, String> 
	{
		@Override
		protected void onPreExecute() {
			showProgressDialog("Submitting Rating...");
		}
		
		@Override
		protected String doInBackground(Void... params) {
			try {
				float newRating = getApplicationContext().getGreenhouseApi().sessionOperations().rateSession(event.getId(), session.getId(), getRating(), getComment());
				session.setRating(newRating);				
				return "Thank you for rating this session!";
			} catch(HttpClientErrorException e) {
				if (e.getStatusCode() == HttpStatus.PRECONDITION_FAILED) {
					return "This session has not yet finished. Please wait until the session has completed before submitting your rating.";
				} else {
					Log.e(TAG, e.getLocalizedMessage(), e);
					return "A problem occurred while submitting the rating.";
				}
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "A problem occurred while submitting the rating.";
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			dismissProgressDialog();
			showResult(result);
		}
	}

}
