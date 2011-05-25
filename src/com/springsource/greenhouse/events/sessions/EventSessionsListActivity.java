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

import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.springsource.greenhouse.AbstractGreenhouseListActivity;

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
		event = getApplicationContext().getSelectedEvent();
		downloadSessions();
	}
	
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		EventSession session = getSession(position);
		getApplicationContext().setSelectedSession(session);
		startActivity(new Intent(v.getContext(), EventSessionDetailsActivity.class));
	}
	
	
	//***************************************
	// Abstract methods
	//***************************************
	abstract void downloadSessions();
	
	
	//***************************************
	// Protected methods
	//***************************************
	protected Event getSelectedEvent() {
		return event;
	}
	
	protected void setSessions(List<EventSession> sessions) {
		this.sessions = sessions;
		getApplicationContext().setSelectedSession(null);
		setListAdapter(new EventSessionsListAdapter(this, sessions));
	}
	
	protected List<EventSession> getSessions() {
		return sessions;
	}
	
	protected EventSession getSession(int position) {
		return sessions.get(position);
	}
	
}
