package com.springsource.greenhouse.controllers;

import java.util.List;

import org.springframework.social.greenhouse.Event;
import org.springframework.web.client.ResourceAccessException;

import android.app.Activity;
import android.util.Log;

public class EventsController extends BaseController {
	
	private static final String TAG = "EventsController";
	
	//***************************************
    // Public methods
    //***************************************
	public static List<Event> getUpcomingEvents(Activity activity) {

		try {
			return getGreenhouseOperations(activity).getUpcomingEvents();
		} catch(ResourceAccessException e) {
			Log.e(TAG, e.getLocalizedMessage());
		}
		
		return null;
	}
}
