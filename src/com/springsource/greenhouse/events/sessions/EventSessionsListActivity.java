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
package com.springsource.greenhouse.events.sessions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.AbstractGreenhouseListActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public abstract class EventSessionsListActivity extends AbstractGreenhouseListActivity {
	
	private Event event;
	
	private List<EventSession> sessions;
	
	
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
		
		if (getIntent().hasExtra("event")) {
			event = (Event) getIntent().getSerializableExtra("event");
		}
		
		downloadSessions();
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent();
		intent.setClass(v.getContext(), EventSessionDetailsActivity.class);
		intent.putExtra("event", event);
		intent.putExtra("session", getSessions().get(position));
		startActivity(intent);
	}
	
	
	//***************************************
	// Public methods
	//***************************************
	protected Event getEvent() {
		return event;
	}
	
	protected List<EventSession> getSessions() {
		return sessions;
	}
	
	protected void setSessions(List<EventSession> sessions) {
		this.sessions = sessions;
		refreshSessions();
	}
	

	//***************************************
	// Protected methods
	//***************************************
	protected void refreshSessions() {
		if (sessions == null) {
			return;
		}

		List<Map<String,String>> sessionMaps = new ArrayList<Map<String,String>>();
		
		// TODO: Is there w way to populate the table from a Session instead of a Map?
		for (EventSession session : sessions) {
			Map<String, String> map = new HashMap<String, String>();			
			map.put("title", session.getTitle());
			map.put("leaders", session.getJoinedLeaders(", "));
			sessionMaps.add(map);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				sessionMaps,
				R.layout.event_sessions_list_item,
				new String[] { "title", "leaders" },
				new int[] { R.id.title, R.id.subtitle } );
		
		setListAdapter(adapter);
	}
	
	abstract void downloadSessions();
}
