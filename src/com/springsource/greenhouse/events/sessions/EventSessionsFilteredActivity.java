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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class EventSessionsFilteredActivity extends ListActivity {
	
	@SuppressWarnings("unused")
	private static final String TAG = EventSessionsFilteredActivity.class.getSimpleName();
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] menu_items = getResources().getStringArray(R.array.event_filtered_sessions_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items); 
		setListAdapter(arrayAdapter);
	}
		
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent();
		
		switch(position) {
			case 0:
				intent.setClass(v.getContext(), EventSessionsCurrentActivity.class);
				break;
			case 1:
				intent.setClass(v.getContext(), EventSessionsUpcomingActivity.class);
				break;
			case 2:
				intent.setClass(v.getContext(), EventSessionsMyFavoritesActivity.class);
				break;
			case 3:
				intent.setClass(v.getContext(), EventSessionsConferenceFavoritesActivity.class);
				break;
			default:
				break;
		}
		
		startActivity(intent);
	}
}
