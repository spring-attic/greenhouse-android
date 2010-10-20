package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.Event;

import android.app.ListActivity;
import android.os.Bundle;

import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsScheduleActivity extends ListActivity {

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
	// Activity methods
	//***************************************
	private void refreshScheduleDays() {
		Event event = SharedDataManager.getCurrentEvent();		
		List<Date> conferenceDates = new ArrayList<Date>();
		
		Date day = event.getStartTime();

		while (day.before(event.getEndTime())) {
			conferenceDates.add(day);
			day.setDate(day.getDate() + 1);
		}
		
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(null, 0, conferenceDates);
	}
}
