package com.springsource.greenhouse.events.sessions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.api.Event;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsScheduleActivity extends ListActivity {
//	private static final String TAG = "EventSessionsScheduleActivity";
	private List<Date> conferenceDates;

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
		refreshScheduleDays();
	}
	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void  onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Date day = conferenceDates.get(position);
		SharedDataManager.setConferenceDay(day);
		NavigationManager.startActivity(v.getContext(), EventSessionsByDayActivity.class);
	}
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshScheduleDays() {
		Event event = SharedDataManager.getCurrentEvent();
		
		if (event == null) {
			return;
		}
		
		conferenceDates = new ArrayList<Date>();
		List<String> conferenceDays = new ArrayList<String>();
		Date day = (Date) event.getStartTime().clone();

		while (day.before(event.getEndTime())) {
			conferenceDates.add((Date) day.clone());
			conferenceDays.add(new SimpleDateFormat("EEEE, MMM d").format(day));
			day.setDate(day.getDate() + 1);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.menu_list_item, conferenceDays));
	}
}
