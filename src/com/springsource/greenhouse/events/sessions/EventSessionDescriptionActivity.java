/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse.events.sessions;

import org.springframework.social.greenhouse.api.EventSession;

import android.os.Bundle;
import android.widget.TextView;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class EventSessionDescriptionActivity extends AbstractGreenhouseActivity {
	
	@SuppressWarnings("unused")
	private static final String TAG = EventSessionDescriptionActivity.class.getSimpleName();
	
	private EventSession session;
	

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
		session = getApplicationContext().getSelectedSession();		
		refreshSessionDescription();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshSessionDescription() {		
		if (session == null) {
			return;
		}
		
		final TextView textViewDescription = (TextView) findViewById(R.id.event_session_description_textview);	
		textViewDescription.setText(session.getDescription());		
	}
}
