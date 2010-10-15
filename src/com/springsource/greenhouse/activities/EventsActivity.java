package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.greenhouse.Event;
import org.springframework.social.greenhouse.GreenhouseOperations;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.util.Prefs;

public class EventsActivity extends ListActivity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		List<Map<String,String>> events = fetchEvents();

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
	private List<Map<String,String>> fetchEvents() {
		GreenhouseOperations greenhouse = Prefs.getGreenhouseOperations(getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE));

		List<Event> upcomingEvents = greenhouse.getUpcomingEvents();		
		List<Map<String,String>> eventList = new ArrayList<Map<String,String>>();		
		
		// TODO: Is there w way to populate the table from an Event instead of a Map?
		for (Event event : upcomingEvents) {
			Map<String, String> map = new HashMap<String, String>();			
			map.put("title", event.getTitle());
			map.put("groupName", event.getGroupName());
			eventList.add(map);
		}		
		
		return eventList;
	}
	
}