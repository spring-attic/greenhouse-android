package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.greenhouse.Event;
import org.springframework.social.greenhouse.EventSession;
import org.springframework.social.greenhouse.EventSession.Leader;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.EventSessionsController;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsUpcomingActivity extends ListActivity {
	
	private static final String TAG = "EventSessionsUpcomingActivity";
	private List<EventSession> upcomingSessions;
	
	
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
		Log.d(TAG, "Refreshing Sessions");
		
		Event event = SharedDataManager.getCurrentEvent();
		upcomingSessions = EventSessionsController.getSessionsUpcoming(this, event.getId());
		
		if (event == null || upcomingSessions == null) {
			return;
		}

		List<Map<String,String>> sessions = new ArrayList<Map<String,String>>();
				
		// TODO: Is there w way to populate the table from a Session instead of a Map?
		for (EventSession session : upcomingSessions) {
			Map<String, String> map = new HashMap<String, String>();
			
			List<Leader> leaders = session.getLeaders();
			String leaderDisplay = "";
			
			for (int i = 0; i < leaders.size(); i++) {
				
				Leader leader = leaders.get(i);
				leaderDisplay = leader.getName();
				
				if (i+1 < leaders.size()) {
					leaderDisplay += ", ";
				}
			}
			
			map.put("title", session.getTitle());
			map.put("leader", leaderDisplay);
			sessions.add(map);
		}		
		
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				sessions,
				R.layout.events_list_item,
				new String[] { "title", "leader" },
				new int[] { R.id.title, R.id.subtitle } );
		
		setListAdapter(adapter);
	}
}
