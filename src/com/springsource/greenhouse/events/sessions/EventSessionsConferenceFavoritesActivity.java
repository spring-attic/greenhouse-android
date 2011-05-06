package com.springsource.greenhouse.events.sessions;

import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

import android.os.AsyncTask;
import android.util.Log;

import com.springsource.greenhouse.controllers.EventSessionsController;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionsConferenceFavoritesActivity extends EventSessionsListActivity {

	
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
				return mEventSessionsController.getConferenceFavoriteSessions(mEvent.getId());
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
