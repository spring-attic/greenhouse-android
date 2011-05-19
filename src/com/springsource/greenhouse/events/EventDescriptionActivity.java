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
package com.springsource.greenhouse.events;

import org.springframework.social.greenhouse.api.Event;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
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
		if (!getIntent().hasExtra("event")) {
			return;
		}
		
		final TextView textViewDescription = (TextView) findViewById(R.id.event_description_textview);
		Event event = (Event) getIntent().getSerializableExtra("event");
		textViewDescription.setText(event.getDescription());
	}
}
