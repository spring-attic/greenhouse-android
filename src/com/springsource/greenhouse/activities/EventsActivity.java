package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.NavigationManager;

public class EventsActivity extends ListActivity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<HashMap<String,String>> events = fetchEvents();

		SimpleAdapter adapter = new SimpleAdapter(
				this,
				events,
				R.layout.events_list_item,
				new String[] { "title", "groupName" },
				new int[] { R.id.title, R.id.subtitle } );
		
		this.setListAdapter(adapter);		
	}
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
//		Intent intent = new Intent(this, EventDetailsActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		LocalActivityManager activityManager = EventsActivityGroup.group.getLocalActivityManager();
//		Window window = activityManager.startActivity("event_details", intent);
//		View view = window.getDecorView();
//		
//		EventsActivityGroup.group.replaceView(view);
		
		NavigationManager.startActivity(v.getContext(), EventDetailsActivity.class);
	}
	
	//***************************************
    // Private methods
    //***************************************
	private List<HashMap<String,String>> fetchEvents() {
//		List<Event> events = new ArrayList<Event>();
		
//		Event event = new Event();
//		event.setId("1");
//		event.setName("SpringOne/2GX");
//		event.setGroupName("SpringOne/2GX");
//		events.add(event);
		
		List<HashMap<String,String>> events = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("title", "SpringOne2GX");
		map.put("groupName", "SpringOne2GX");
		events.add(map);
		
		return events;
	}
	
}