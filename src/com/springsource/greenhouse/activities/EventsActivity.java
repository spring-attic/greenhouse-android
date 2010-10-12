package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.springsource.greenhouse.R;

public class EventsActivity extends ListActivity {
	
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
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), ((TextView) findViewById(R.id.title)).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private List<HashMap<String,String>> fetchEvents() {
//		List<Event> events = new ArrayList<Event>();
		
//		Event event = new Event();
//		event.setId("1");
//		event.setName("SpringOne/2GX");
//		event.setGroupName("SpringOne/2GX");
//		events.add(event);
		
		List<HashMap<String,String>> events = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("title", "SpringOne/2GX");
		map.put("groupName", "S2GX");
		events.add(map);
		
		return events;
	}
	
}