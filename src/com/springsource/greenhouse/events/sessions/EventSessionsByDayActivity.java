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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class EventSessionsByDayActivity extends EventSessionsListActivity {
	
	private static final String TAG = EventSessionsByDayActivity.class.getSimpleName();
	
	private Date day;
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onStart() {
		super.onStart();
		
		if (getIntent().hasExtra("day")) {
			day = (Date) getIntent().getSerializableExtra("day");
			String title = new SimpleDateFormat("EEEE, MMM d").format(day);
			this.setTitle(title);
		}
	}

	
	//***************************************
    // Protected methods
    //***************************************
	@Override
	protected void downloadSessions() {
		new DownloadSessionsTask().execute();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadSessionsTask extends AsyncTask<Void, Void, List<EventSession>> {
		
		private Exception exception;
				
		@Override
		protected void onPreExecute() {
			showProgressDialog();
		}
		
		@Override
		protected List<EventSession> doInBackground(Void... params) {
			try {
				Event event = getEvent();
				if (event == null || day == null) {
					return null;
				}
				return getApplicationContext().getGreenhouseApi().sessionOperations().getSessionsOnDay(event.getId(), day);				
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<EventSession> result) {
			dismissProgressDialog();
			processException(exception);
			setSessions(result);
		}
	}
}
