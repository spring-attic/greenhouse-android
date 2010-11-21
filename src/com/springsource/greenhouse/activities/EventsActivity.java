package com.springsource.greenhouse.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.greenhouse.Event;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.controllers.EventsController;
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.util.SharedDataManager;

public class EventsActivity extends BaseListActivity {
//	private static final String TAG = "EventsActivity";
	private List<Event> mUpcomingEvents;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		downloadEvents();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.events_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.events_menu_refresh:
	    	downloadEvents();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	
	//***************************************
    // ListActivity methods
    //***************************************
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Event event = mUpcomingEvents.get(position);
		SharedDataManager.setCurrentEvent(event);
		
		NavigationManager.startActivity(v.getContext(), EventDetailsActivity.class);
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void refreshEvents(List<Event> upcomingEvents) {	
		mUpcomingEvents = upcomingEvents;

		if (mUpcomingEvents == null) {
			return;
		}
		
		List<Map<String,String>> events = new ArrayList<Map<String,String>>();
		
		// TODO: Is there w way to populate the table from an Event instead of a Map?
		for (Event event : upcomingEvents) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", event.getTitle());
			map.put("groupName", event.getGroupName());
			events.add(map);
		}		
		
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				events,
				R.layout.events_list_item,
				new String[] { "title", "groupName" },
				new int[] { R.id.title, R.id.subtitle } );
		
		setListAdapter(adapter);
	}
		
	private void downloadEvents() {
		new DownloadEventsTask().execute();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class DownloadEventsTask extends AsyncTask<Void, Void, List<Event>> {
		private Exception mException;
		private EventsController mEventsController;
		
		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog(); 
			mEventsController = new EventsController(getContext());
		}
		
		@Override
		protected List<Event> doInBackground(Void... params) {
			try {
				return mEventsController.getUpcomingEvents();
			} catch(Exception e) {
				logException(e);
				mException = e;
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<Event> result) {
			dismissProgressDialog();
			processException(mException);
			refreshEvents(result);
		}
	}
}