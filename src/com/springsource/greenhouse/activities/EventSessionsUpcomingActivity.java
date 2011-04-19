package com.springsource.greenhouse.activities;

import java.util.List;

import org.springframework.social.greenhouse.types.Event;
import org.springframework.social.greenhouse.types.EventSession;

import android.os.AsyncTask;
import android.util.Log;

import com.springsource.greenhouse.controllers.EventSessionsController;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsUpcomingActivity extends EventSessionsListActivity {
//	private static final String TAG = "EventSessionsUpcomingActivity";


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
		private Exception mException;
		private EventSessionsController mEventSessionsController;
		private Event mEvent;
				
		@Override
		protected void onPreExecute() {
			showProgressDialog();
			mEventSessionsController = new EventSessionsController(getApplicationContext());
			mEvent = SharedDataManager.getCurrentEvent();
		}
		
		@Override
		protected List<EventSession> doInBackground(Void... params) {
			try {
				if (mEvent == null) {
					return null;
				}
				return mEventSessionsController.getSessionsUpcoming(mEvent.getId());
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				mException = e;
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<EventSession> result) {
			dismissProgressDialog();
			processException(mException);
			setSessions(result);
		}
	}
}
