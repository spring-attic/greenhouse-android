package com.springsource.greenhouse.events;

import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.Tweet;
import org.springframework.social.greenhouse.api.TweetFeed;

import android.os.AsyncTask;
import android.util.Log;

import com.springsource.greenhouse.twitter.TweetsListActivity;

public class EventTweetsActivity extends TweetsListActivity {
	
	private static final String TAG = EventTweetsActivity.class.getSimpleName();
	
	
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
				if (event == null) {
					return null;
				}
				TweetFeed feed = getApplicationContext().getGreenhouseApi().tweetOperations().getTweetsForEvent(event.getId());
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
