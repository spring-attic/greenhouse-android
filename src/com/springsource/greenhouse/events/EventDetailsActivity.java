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
package com.springsource.greenhouse.events;

import org.springframework.social.greenhouse.api.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.R;
import com.springsource.greenhouse.events.sessions.EventSessionsFilteredActivity;
import com.springsource.greenhouse.events.sessions.EventSessionsScheduleActivity;

/**
 * @author Roy Clarkson
 */
public class EventDetailsActivity extends AbstractGreenhouseActivity {
	
	@SuppressWarnings("unused")
	private static final String TAG = EventDetailsActivity.class.getSimpleName();
	
	private Event event;
	

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
				
		final ListView listView = (ListView) findViewById(R.id.event_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.event_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	Intent intent = new Intent();
		    	
		    	switch(position) {
			      	case 0:
			      		intent.setClass(view.getContext(), EventSessionsFilteredActivity.class);
			      		break;
			      	case 1:
			      		intent.setClass(view.getContext(), EventSessionsScheduleActivity.class);
			      		break;
			      	case 2:
			      		intent.setClass(view.getContext(), EventTweetsActivity.class);
			      		break;
			      	default:
			      		break;
		    	}
		    	
		    	startActivity(intent);
		    }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		event = getApplicationContext().getSelectedEvent();		
		refreshEventDetails();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshEventDetails() {
		if (event == null) {
			return;
		}
		
		TextView t = (TextView) findViewById(R.id.event_details_name);
		t.setText(event.getTitle());
		
		t = (TextView) findViewById(R.id.event_details_date);
		t.setText(event.getFormattedTimeSpan());
		
		t = (TextView) findViewById(R.id.event_details_location);
		t.setText(event.getLocation());
		
		t = (TextView) findViewById(R.id.event_details_description);
		t.setText(event.getDescription());		
	}
	
}
