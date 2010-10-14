package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.springsource.greenhouse.R;

public class EventDetailsActivity extends Activity {

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		
		final TextView textViewEventName = (TextView) findViewById(R.id.event_details_textview_name);
		final TextView textViewEventDate = (TextView) findViewById(R.id.event_details_textview_date);
		final TextView textViewEventLocation = (TextView) findViewById(R.id.event_details_textview_location);
		final Button buttonDescription = (Button) findViewById(R.id.event_details_button_description);
		final Button buttonSessions = (Button) findViewById(R.id.event_details_button_sessions);
		final Button buttonTweets = (Button) findViewById(R.id.event_details_button_tweets);
		final Button buttonMap = (Button) findViewById(R.id.event_details_button_map);

		textViewEventName.setText("SpringOne2GX");
		textViewEventDate.setText("Tue. Oct 19 - Fri, Oct 22, 2010");
		textViewEventLocation.setText("Westin Lombard Yorktown Center");
		
		buttonDescription.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	
		    	Intent intent = new Intent(v.getContext(), EventDescriptionActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				LocalActivityManager activityManager = EventsActivityGroup.group.getLocalActivityManager();
				Window window = activityManager.startActivity("event_description", intent);
				View view = window.getDecorView();
				
				EventsActivityGroup.group.replaceView(view);
		    }
		});
		
		buttonSessions.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	
		    	Intent intent = new Intent(v.getContext(), EventSessionsMenuActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				LocalActivityManager activityManager = EventsActivityGroup.group.getLocalActivityManager();
				Window window = activityManager.startActivity("event_sessions_menu", intent);
				View view = window.getDecorView();
				
				EventsActivityGroup.group.replaceView(view);
		    }
		});

		buttonTweets.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        Toast.makeText(EventDetailsActivity.this, "Tweets", Toast.LENGTH_SHORT).show();
		    }
		});

		buttonMap.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        Toast.makeText(EventDetailsActivity.this, "Map", Toast.LENGTH_SHORT).show();
		    }
		});
	}
}
