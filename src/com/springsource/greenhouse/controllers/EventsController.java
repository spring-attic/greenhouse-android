package com.springsource.greenhouse.controllers;

import java.util.List;

import org.springframework.social.greenhouse.types.Event;

import android.content.Context;

public class EventsController extends BaseController {

	protected static final String TAG = EventsController.class.getSimpleName();
	
	
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
		return getGreenhouseApi().getUpcomingEvents();
	}
}
