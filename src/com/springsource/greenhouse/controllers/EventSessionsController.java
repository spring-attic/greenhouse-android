package com.springsource.greenhouse.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.social.greenhouse.EventSession;
import org.springframework.web.client.HttpClientErrorException;

import android.app.Activity;

public class EventSessionsController extends BaseController {
	
	private static final String TAG = "EventSessionsController";
	
	//***************************************
    // Public methods
    //***************************************
	public static List<EventSession> getSessionsCurrent(Activity activity, long eventId) {
		
		try {
			Date now = new Date();
			List<EventSession> sessions = getGreenhouseOperations(activity).getSessionsOnDay(eventId, now);
			List<EventSession> currentSessions = new ArrayList<EventSession>();
			
			for (EventSession session : sessions) {
				if (session.getStartTime().before(now) && session.getEndTime().after(now)) {
					currentSessions.add(session);
				}
			}
			
			return currentSessions;
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				signOut(activity);
			}
		}
		
		return null;
	}

	public static List<EventSession> getSessionsUpcoming(Activity activity, long eventId) {
		
		try {
			Date now = new Date();
			List<EventSession> sessions = getGreenhouseOperations(activity).getSessionsOnDay(eventId, now);
			List<EventSession> upcomingSessions = new ArrayList<EventSession>();

			Date upcomingTime = null;
			
			for (EventSession session : sessions) {
				if (upcomingTime == null && session.getStartTime().after(now)) {
					upcomingTime = session.getStartTime();
				} 
				
				if (upcomingTime != null && session.getStartTime().compareTo(upcomingTime) == 0) {
					upcomingSessions.add(session);
				}
			}
			
			return upcomingSessions;
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				signOut(activity);
			}
		}
		
		return null;
	}

	public static List<EventSession> getSessionsByDay(Activity activity, long eventId, Date day) {
		
		try {
			return getGreenhouseOperations(activity).getSessionsOnDay(eventId, day);
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				signOut(activity);
			}
		}
		
		return null;
	}
}
