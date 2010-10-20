package com.springsource.greenhouse.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.social.greenhouse.Event;
import org.springframework.web.client.HttpClientErrorException;

import android.app.Activity;

public class EventsController extends BaseController {
	
	public static List<Event> getUpcomingEvents(Activity activity) {

		try {
			return getGreenhouseOperations(activity).getUpcomingEvents();
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
		    	signOut(activity);
			}
		} 
		
		return null;
	}

}
