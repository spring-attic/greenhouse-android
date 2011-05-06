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

import java.text.SimpleDateFormat;

import org.springframework.social.greenhouse.api.Event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.events.sessions.EventSessionsFilteredActivity;
import com.springsource.greenhouse.events.sessions.EventSessionsScheduleActivity;

/**
 * @author Roy Clarkson
 */
public class EventDetailsActivity extends Activity 
{
	@SuppressWarnings("unused")
	private static final String TAG = EventDetailsActivity.class.getSimpleName();
	
	private Event _event;
	

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
				
		final ListView listView = (ListView) findViewById(R.id.event_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.event_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() 
		{
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		    {	
		    	Intent intent = new Intent();
		    	intent.putExtra("event", _event);
		    	
		    	switch(position) 
		    	{
			      	case 0:
			      		intent.setClass(view.getContext(), EventDescriptionActivity.class);
			      		break;
			      	case 1:
			      		intent.setClass(view.getContext(), EventSessionsFilteredActivity.class);
			      		break;
			      	case 2:
			      		intent.setClass(view.getContext(), EventSessionsScheduleActivity.class);
			      	default:
			      		break;
		    	}
		    	
		    	startActivity(intent);
		    }
		});
	}
	
	@Override
	public void onStart() 
	{
		super.onStart();
		
		if (getIntent().hasExtra("event"))
		{
			_event = (Event) getIntent().getSerializableExtra("event");
		}
		
		refreshEventDetails();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshEventDetails() 
	{	
		if (_event == null)
		{
			return;
		}
		
		final TextView textViewEventName = (TextView) findViewById(R.id.event_details_textview_name);
		final TextView textViewEventDate = (TextView) findViewById(R.id.event_details_textview_date);
		final TextView textViewEventLocation = (TextView) findViewById(R.id.event_details_textview_location);
		
		textViewEventName.setText(_event.getTitle());
		
		String startTime = new SimpleDateFormat("EEE, MMM d").format(_event.getStartTime());
		String endTime = new SimpleDateFormat("EEE, MMM d, yyyy").format(_event.getEndTime());
		
		textViewEventDate.setText(startTime + " - " + endTime);
		textViewEventLocation.setText(_event.getLocation());
	}
}
