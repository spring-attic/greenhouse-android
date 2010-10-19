package com.springsource.greenhouse.activities;

import org.springframework.social.greenhouse.Event;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventDetailsActivity extends Activity {

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
				
		final ListView listView = (ListView) findViewById(R.id.event_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.event_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	
		    	switch(position) {
			      	case 0:
			      		NavigationManager.startActivity(view.getContext(), EventDescriptionActivity.class);
			      		break;
			      	case 1:
			      		NavigationManager.startActivity(view.getContext(), EventSessionsFilteredActivity.class);
			      		break;
			      	case 2:
			      		NavigationManager.startActivity(view.getContext(), EventSessionsScheduleActivity.class);
			      	default:
			      		break;
		    	}
		    }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		refreshEventDetails();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshEventDetails() {
		
		Event event = SharedDataManager.getCurrentEvent();
		
		if (event == null) {
			return;
		}
		
		final TextView textViewEventName = (TextView) findViewById(R.id.event_details_textview_name);
		final TextView textViewEventDate = (TextView) findViewById(R.id.event_details_textview_date);
		final TextView textViewEventLocation = (TextView) findViewById(R.id.event_details_textview_location);
		
		textViewEventName.setText(event.getTitle());
		textViewEventDate.setText(event.getStartTime() + " " + event.getEndTime()); // ("Tue. Oct 19 - Fri, Oct 22, 2010");
		textViewEventLocation.setText(event.getLocation());
	}
}
