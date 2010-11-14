package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.greenhouse.Event;
import org.springframework.social.greenhouse.EventSession;
import org.springframework.social.greenhouse.EventSession.Leader;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.EventSessionsController;
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsByDayActivity extends ListActivity {
	
	private static final String TAG = "EventSessionsByDayActivity";
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
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		SharedDataManager.setCurrentSession(sessions.get(position));
		NavigationManager.startActivity(v.getContext(), EventSessionDetailsActivity.class);
	}
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshSessions() {
		
		Event event = SharedDataManager.getCurrentEvent();
		Date day = SharedDataManager.getConferenceDay();
		
		if (event == null || day == null) {
			return;
		}
		
		sessions = EventSessionsController.getSessionsByDay(this, event.getId(), day);
		
		List<Map<String,String>> sessionMaps = new ArrayList<Map<String,String>>();
		
		// TODO: Is there w way to populate the table from an Event instead of a Map?
		for (EventSession session : sessions) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", session.getTitle());
			map.put("leaders", formatLeaders(session));
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
	
	private String formatLeaders(EventSession session) {
		
		List<Leader> leaders = session.getLeaders();
		String s = "";
		int size = leaders.size();
		
		for (int i = 0; i < size; i++) {
			Leader leader = leaders.get(i);
			s += leader.getName();
			
			if (i < size-1) {
				s += ", ";
			}
		}
		
		return s;
	}

}
