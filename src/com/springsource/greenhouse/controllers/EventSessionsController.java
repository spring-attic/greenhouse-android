package com.springsource.greenhouse.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.EventSession;

import android.content.Context;
import android.util.Log;

public class EventSessionsController extends BaseController {
	private static final String TAG = "EventSessionsController";
	
	
	//***************************************
    // Constructors
    //***************************************
	public EventSessionsController(Context context) {
		super(context);
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public List<EventSession> getSessionsCurrent(long eventId) {
		Log.d(TAG, "fetching current sessions. eventId=" + eventId);
		
		if (eventId <= 0) {
			return null;
		}
		
		Date now = new Date();
		List<EventSession> sessions = getGreenhouseOperations().getSessionsOnDay(eventId, now);
		List<EventSession> currentSessions = new ArrayList<EventSession>();
		
		for (EventSession session : sessions) {
			if (session.getStartTime().before(now) && session.getEndTime().after(now)) {
				currentSessions.add(session);
			}
		}
		
		return currentSessions;
	}

	public List<EventSession> getSessionsUpcoming(long eventId) {
		Log.d(TAG, "fetching upcoming sessions. eventId=" + eventId);
		
		if (eventId <= 0) {
			return null;
		}
		
		Date now = new Date();
		List<EventSession> sessions = getGreenhouseOperations().getSessionsOnDay(eventId, now);
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
	}

	public List<EventSession> getSessionsByDay(long eventId, Date day) {
		Log.d(TAG, "fetching sessions by day. eventId=" + eventId + " day=" + day.toLocaleString());
		
		if (eventId <= 0 || day == null) {
			return null;
		}
		
		return getGreenhouseOperations().getSessionsOnDay(eventId, day);
	}
	
	public List<EventSession> getFavoriteSessions(long eventId) {
		Log.d(TAG, "fetching favorite sessions. eventId=" + eventId);
		
		if (eventId <= 0) {
			return null;
		}
		
		return getGreenhouseOperations().getFavoriteSessions(eventId);
	}

	public List<EventSession> getConferenceFavoriteSessions(long eventId) {
		Log.d(TAG, "fetching conference favorite sessions. eventId=" + eventId);
		
		if (eventId <= 0) {
			return null;
		}
		
		return getGreenhouseOperations().getConferenceFavoriteSessions(eventId);
	}
	
	public boolean updateFavoriteSession(long eventId, long sessionId) {
		Log.d(TAG, "updating favorite session. eventId=" + eventId + " sessionId=" + sessionId);		
		return getGreenhouseOperations().updateFavoriteSession(eventId, sessionId);
	}
}
