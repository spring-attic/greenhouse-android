package com.springsource.greenhouse.controllers;

import java.util.List;

import org.springframework.social.greenhouse.Event;

import android.content.Context;
import android.util.Log;

public class EventsController extends BaseController {
	private static final String TAG = "EventsController";
	
	//***************************************
    // Constructors
    //***************************************
	public EventsController(Context context) {
		super(context);
	}
	
	//***************************************
    // Public methods
    //***************************************	
	public List<Event> getUpcomingEvents() {
		Log.d(TAG, "fetching upcoming events");
		return getGreenhouseOperations().getUpcomingEvents();
	}
}
