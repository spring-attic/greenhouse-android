package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.springsource.greenhouse.R;

public class EventDetailsActivity extends Activity {

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		
		final TextView textViewEventName = (TextView) findViewById(R.id.event_details_name);
		final TextView textViewEventDate = (TextView) findViewById(R.id.event_details_date);
		final TextView textViewEventLocation = (TextView) findViewById(R.id.event_details_location);
		
		textViewEventName.setText("SpringOne2GX");
		textViewEventDate.setText("Tue. Oct 19 - Fri, Oct 22, 2010");
		textViewEventLocation.setText("Westin Lombard Yorktown Center");
	}
}
