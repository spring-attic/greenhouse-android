package com.springsource.greenhouse.activities;

import com.springsource.greenhouse.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDescriptionActivity extends Activity {

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_description);
		
		final TextView textViewDescription = (TextView) findViewById(R.id.event_description_textview);
		
		textViewDescription.setText("event description goes here");
	}
}
