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

import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;
import org.springframework.social.greenhouse.api.Tweet;
import org.springframework.social.greenhouse.api.TweetFeed;

import android.os.AsyncTask;
import android.util.Log;

import com.springsource.greenhouse.twitter.TweetsListActivity;

/**
 * @author Roy Clarkson
 */
public class EventSessionTweetsActivity extends TweetsListActivity {

	private static final String TAG = EventSessionTweetsActivity.class.getSimpleName();
	
	
	//***************************************
    // TweetsListActivity methods
    //***************************************
	@Override
	protected void downloadTweets() {
		new DownloadTweetsTask().execute();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadTweetsTask extends AsyncTask<Void, Void, List<Tweet>> {
		
		private Exception exception;
				
		@Override
		protected void onPreExecute() {
			showProgressDialog();
		}
		
		@Override
		protected List<Tweet> doInBackground(Void... params) {
			try {
				Event event = getSelectedEvent();
				EventSession session = getSelectedSession();
				if (event == null && session == null) {
					return null;
				}
				TweetFeed feed = getApplicationContext().getGreenhouseApi().tweetOperations().getTweetsForEventSession(event.getId(), session.getId());
				return feed.getTweets();
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<Tweet> result) {
			dismissProgressDialog();
			processException(exception);
			setTweets(result);
		}
	}

}
