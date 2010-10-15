package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.springsource.greenhouse.R;

public class EventSessionsMenuActivity extends Activity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_sessions_menu);
		
		ListView listView = (ListView) findViewById(R.id.event_sessions_filtered_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.event_sessions_menu_array);
		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items));
	}
}
