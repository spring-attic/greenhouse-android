package com.springsource.greenhouse.events.sessions;

import java.text.SimpleDateFormat;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventSession;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.EventSessionsController;
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventSessionDetailsActivity extends AbstractGreenhouseActivity {
//	private static final String TAG = "EventSessionDetailsActivity";
	
	
	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_session_details);
		
		final ListView listView = (ListView) findViewById(R.id.event_session_details_menu);
		
		String[] menu_items = getResources().getStringArray(R.array.event_session_details_options_array);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.menu_list_item, menu_items);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	
		    	switch(position) {
			      	case 0:
			      		NavigationManager.startActivity(view.getContext(), EventSessionDescriptionActivity.class);
			      		break;
			      	case 1:
			      		new UpdateFavoriteTask().execute();
			      		break;
			      	default:
			      		break;
		    	}
		    }
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		refreshEventDetails();
	}
	
	
	//***************************************
	// Private methods
	//***************************************
	private void refreshEventDetails() {
		
		EventSession session = SharedDataManager.getCurrentSession();
		
		if (session == null) {
			return;
		}
		
		final TextView textViewSessionName = (TextView) findViewById(R.id.event_session_details_textview_name);
		final TextView textViewSessionLeaders = (TextView) findViewById(R.id.event_session_details_textview_leaders);
		final TextView textViewSessionTime = (TextView) findViewById(R.id.event_session_details_textview_time);
		final TextView textViewSessionRoom = (TextView) findViewById(R.id.event_session_details_textview_room);
//		final TextView textViewSessionRating = (TextView) findViewById(R.id.event_session_details_textview_rating);
		
		textViewSessionName.setText(session.getTitle());
		textViewSessionLeaders.setText(session.getJoinedLeaders(", "));
		
		String startTime = new SimpleDateFormat("h:mm a").format(session.getStartTime());
		String endTime = new SimpleDateFormat("h:mm a").format(session.getEndTime());
		textViewSessionTime.setText(startTime + " - " + endTime);
		
		textViewSessionRoom.setText("Room: " + session.getRoom().getLabel());
		setFavoriteStatus(session.isFavorite());
//		textViewSessionRating.setText(session.getRating() + " Stars");
	}
	
	private void setFavoriteStatus(Boolean status) {
		final TextView textViewSessionFavorite = (TextView) findViewById(R.id.event_session_details_textview_favorite);
		String text = status ? "Favorite: \u2713" : "Favorite:";
		textViewSessionFavorite.setText(text);
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class UpdateFavoriteTask extends AsyncTask<Void, Void, Boolean> {
		private Exception mException;
		private EventSessionsController mEventSessionsController;
		private Event mEvent;
		private EventSession mSession;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog("Updating favorite ..."); 
			mEvent = SharedDataManager.getCurrentEvent();
			mSession = SharedDataManager.getCurrentSession();
			mEventSessionsController = new EventSessionsController(getApplicationContext());
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				if (mEvent == null || mSession == null) {
					return false;
				}
				return mEventSessionsController.updateFavoriteSession(mEvent.getId(), mSession.getId());
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				mException = e;
			} 
			
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			dismissProgressDialog();
			processException(mException);
			setFavoriteStatus(result);
		}
	}
}
