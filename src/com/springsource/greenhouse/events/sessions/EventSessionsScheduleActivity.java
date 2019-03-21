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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.api.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.springsource.greenhouse.AbstractGreenhouseListActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class EventSessionsScheduleActivity extends AbstractGreenhouseListActivity {
	
	@SuppressWarnings("unused")
	private static final String TAG = EventSessionsScheduleActivity.class.getSimpleName();
	
	private Event event;
	
	private List<Date> conferenceDates;
	

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		event = getApplicationContext().getSelectedEvent();
		getApplicationContext().setSelectedDay(null);
		refreshScheduleDays();
	}
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Date day = conferenceDates.get(position);
		getApplicationContext().setSelectedDay(day);		
		startActivity(new Intent(v.getContext(), EventSessionsByDayActivity.class));
	}
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshScheduleDays() {
		if (event == null) {
			return;
		}
		
		conferenceDates = new ArrayList<Date>();
		List<String> conferenceDays = new ArrayList<String>();
		Date day = (Date) event.getStartTime().clone();

		while (day.before(event.getEndTime())) {
			conferenceDates.add((Date) day.clone());
			conferenceDays.add(new SimpleDateFormat("EEEE, MMM d").format(day));
			day.setDate(day.getDate() + 1);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.menu_list_item, conferenceDays));
	}
}
