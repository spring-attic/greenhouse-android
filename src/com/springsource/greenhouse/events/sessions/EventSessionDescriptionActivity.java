package com.springsource.greenhouse.events.sessions;

import org.springframework.social.greenhouse.api.EventSession;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionDescriptionActivity extends Activity {

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_session_description);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		refreshSessionDescription();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshSessionDescription() {
		
		final TextView textViewDescription = (TextView) findViewById(R.id.event_session_description_textview);
		
		EventSession session = SharedDataManager.getCurrentSession();
		
		if (session == null) {
			return;
		}
		
		textViewDescription.setText(session.getDescription());		
	}
}
