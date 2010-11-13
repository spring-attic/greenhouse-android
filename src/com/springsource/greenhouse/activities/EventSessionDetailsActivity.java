package com.springsource.greenhouse.activities;

import java.text.SimpleDateFormat;

import org.springframework.social.greenhouse.EventSession;

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

public class EventSessionDetailsActivity extends Activity {
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_session_details);
		
		final ListView listView = (ListView) findViewById(R.id.event_session_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.event_session_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	
		    	switch(position) {
			      	case 0:
			      		NavigationManager.startActivity(view.getContext(), EventSessionDescriptionActivity.class);
			      		break;
			      	case 1:
//			      		NavigationManager.startActivity(view.getContext(), EventSessionsFilteredActivity.class);
			      		break;
			      	case 2:
//			      		NavigationManager.startActivity(view.getContext(), EventSessionsScheduleActivity.class);
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
		
		EventSession session = SharedDataManager.getCurrentSession();
		
		if (session == null) {
			return;
		}
		
		final TextView textViewSessionName = (TextView) findViewById(R.id.event_session_details_textview_name);
		final TextView textViewSessionLeaders = (TextView) findViewById(R.id.event_session_details_textview_leaders);
		final TextView textViewSessionTime = (TextView) findViewById(R.id.event_session_details_textview_time);
		final TextView textViewSessionRoom = (TextView) findViewById(R.id.event_session_details_textview_room);
		final TextView textViewSessionRating = (TextView) findViewById(R.id.event_session_details_textview_rating);
		
		textViewSessionName.setText(session.getTitle());
		textViewSessionLeaders.setText("leaders");
		
		String startTime = new SimpleDateFormat("h:mm a").format(session.getStartTime());
		String endTime = new SimpleDateFormat("h:mm a").format(session.getEndTime());
		textViewSessionTime.setText(startTime + " - " + endTime);
		
		textViewSessionRoom.setText(session.getRoom().getLabel());
		textViewSessionRating.setText(session.getRating() + " Stars");
	}
}
