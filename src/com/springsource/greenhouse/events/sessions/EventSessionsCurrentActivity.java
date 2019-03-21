/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse.events.sessions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Roy Clarkson
 */
public class EventSessionsCurrentActivity extends EventSessionsListActivity {
	
	private static final String TAG = EventSessionsCurrentActivity.class.getSimpleName();
	
	
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
				Event event = getSelectedEvent();
				if (event == null) {
					return null;
				}
				
				Date now = new Date();
				List<EventSession> sessions = getApplicationContext().getGreenhouseApi().sessionOperations().getSessionsOnDay(event.getId(), now);
				List<EventSession> currentSessions = new ArrayList<EventSession>();
				
				for (EventSession session : sessions) {
					if (session.getStartTime().before(now) && session.getEndTime().after(now)) {
						currentSessions.add(session);
					}
				}
				
				return currentSessions;
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