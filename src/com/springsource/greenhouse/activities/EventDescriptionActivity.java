package com.springsource.greenhouse.activities;

import org.springframework.social.greenhouse.types.Event;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventDescriptionActivity extends Activity {

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_description);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		refreshEventDescription();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshEventDescription() {
		final TextView textViewDescription = (TextView) findViewById(R.id.event_description_textview);
		
		Event event = SharedDataManager.getCurrentEvent();
		
		if (event == null) {
			return;
		}
		
		textViewDescription.setText(event.getDescription());		
	}
}
