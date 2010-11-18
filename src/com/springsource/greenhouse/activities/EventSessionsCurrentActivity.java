package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.greenhouse.Event;
import org.springframework.social.greenhouse.EventSession;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.EventSessionsController;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsCurrentActivity extends ListActivity {
	
	private static final String TAG = "EventSessionsCurrentActivity";
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
		refreshSessions();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshSessions() {
		
		Event event = SharedDataManager.getCurrentEvent();
		
		if (event == null) {
			return;
		}
		
		sessions = EventSessionsController.getSessionsCurrent(this, event.getId());
		
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
				new String[] { "title", "leader" },
				new int[] { R.id.title, R.id.subtitle } );
		
		setListAdapter(adapter);
	}
}
