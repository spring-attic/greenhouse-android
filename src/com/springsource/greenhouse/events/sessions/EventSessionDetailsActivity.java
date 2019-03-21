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

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

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
import com.springsource.greenhouse.twitter.PostTweetActivity;

/**
 * @author Roy Clarkson
 */
public class EventSessionDetailsActivity extends AbstractGreenhouseActivity {
	
	private static final String TAG = EventSessionDetailsActivity.class.getSimpleName();
	
	private Event event;
	
	private EventSession session;
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_session_details);
		
		final ListView listView = (ListView) findViewById(R.id.event_session_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.event_session_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	switch(position) {
			      	case 0:
			      		new UpdateFavoriteTask().execute();
			      		break;
			      	case 1:
			      		startActivity(new Intent(view.getContext(), EventSessionRatingActivity.class));
			      		break;
			      	case 2:
			      		startActivity(new Intent(view.getContext(), PostTweetActivity.class));
			      		break;
			      	case 3:
			      		startActivity(new Intent(view.getContext(), EventSessionTweetsActivity.class));
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
		refreshEventDetails(); 
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshEventDetails() {		
		if (session == null) {
			return;
		}
		
		TextView t = (TextView) findViewById(R.id.event_session_details_name);
		t.setText(session.getTitle());
		
		t = (TextView) findViewById(R.id.event_session_details_leaders);
		t.setText(session.getJoinedLeaders(", "));
		
		t = (TextView) findViewById(R.id.event_session_details_time_and_room);
		t.setText(session.getFormattedTimeSpan() + " in " + session.getRoom().getLabel());
		
		t = (TextView) findViewById(R.id.event_session_details_rating);
		if (session.getRating() == 0) {
			t.setText("No Ratings");
		} else {
			t.setText(session.getRating() + " Stars");
		}
		
		t = (TextView) findViewById(R.id.event_session_details_description);
		t.setText(session.getDescription());
		
		setFavoriteStatus(session.isFavorite());
	}
	
	private void setFavoriteStatus(Boolean status) {
		final TextView textViewSessionFavorite = (TextView) findViewById(R.id.event_session_details_favorite);
		String text = status ? "Favorite: \u2713" : "Not a Favorite";
		textViewSessionFavorite.setText(text);
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class UpdateFavoriteTask extends AsyncTask<Void, Void, Boolean> {
		
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog("Updating favorite ..."); 
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				if (event == null || session == null) {
					return false;
				}
				return getApplicationContext().getGreenhouseApi().sessionOperations().updateFavoriteSession(event.getId(), session.getId());
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			} 
			
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dismissProgressDialog();
			processException(exception);
			setFavoriteStatus(result);
		}
	}
}
