package com.springsource.greenhouse.activities;

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

public class EventDetailsActivity extends Activity {

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		
		final TextView textViewEventName = (TextView) findViewById(R.id.event_details_textview_name);
		final TextView textViewEventDate = (TextView) findViewById(R.id.event_details_textview_date);
		final TextView textViewEventLocation = (TextView) findViewById(R.id.event_details_textview_location);
		final ListView listView = (ListView) findViewById(R.id.event_details_menu);
		
		textViewEventName.setText("SpringOne2GX");
		textViewEventDate.setText("Tue. Oct 19 - Fri, Oct 22, 2010");
		textViewEventLocation.setText("Westin Lombard Yorktown Center");
		
		String[] menu_items = getResources().getStringArray(R.array.event_details_menu_array);
		listView.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items));
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	
		    	switch(position) {
			      	case 0:
			      		NavigationManager.startActivity(view.getContext(), EventDescriptionActivity.class);
			      		break;
			      	case 1:
			      		NavigationManager.startActivity(view.getContext(), EventSessionsMenuActivity.class);
			      		break;
			      	default:
			      		break;
		    	}
		    }
		});
	}
}
