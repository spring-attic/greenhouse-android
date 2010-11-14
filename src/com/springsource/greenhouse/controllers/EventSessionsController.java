package com.springsource.greenhouse.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.social.greenhouse.EventSession;
import org.springframework.web.client.HttpClientErrorException;

import android.content.Context;
import android.util.Log;

public class EventSessionsController extends BaseController {
	
	private static final String TAG = "EventSessionsController";
	
	//***************************************
    // Public methods
    //***************************************
	public static List<EventSession> getSessionsCurrent(Context context, long eventId) {
		
		showProgressDialog(context);
		try {
			Date now = new Date();
			List<EventSession> sessions = getGreenhouseOperations(context).getSessionsOnDay(eventId, now);
			List<EventSession> currentSessions = new ArrayList<EventSession>();
			
			for (EventSession session : sessions) {
				if (session.getStartTime().before(now) && session.getEndTime().after(now)) {
					currentSessions.add(session);
				}
			}
			
			return currentSessions;
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//				signOut(context);
			}
		} finally {
			dismissProgressDialog();
		}
		
		return null;
	}

	public static List<EventSession> getSessionsUpcoming(Context context, long eventId) {
		
		showProgressDialog(context);
		try {
			Date now = new Date();
			List<EventSession> sessions = getGreenhouseOperations(context).getSessionsOnDay(eventId, now);
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
//				signOut(context);
			}
		} finally {
			dismissProgressDialog();
		}
		
		return null;
	}

	public static List<EventSession> getSessionsByDay(Context context, long eventId, Date day) {
		
		showProgressDialog(context);
		try {
			return getGreenhouseOperations(context).getSessionsOnDay(eventId, day);
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//				signOut(context);
			}
		} finally {
			dismissProgressDialog();
		}
		
		return null;
	}
	
	public static List<EventSession> getFavoriteSessions(Context context, long eventId) {
		
		showProgressDialog(context);
		try {
			return getGreenhouseOperations(context).getFavoriteSessions(eventId);
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//				signOut(context);
			}
		} finally {
			dismissProgressDialog();
		}
		
		return null;
	}

	public static List<EventSession> getConferenceFavoriteSessions(Context context, long eventId) {
		
		showProgressDialog(context);
		try {
			return getGreenhouseOperations(context).getConferenceFavoriteSessions(eventId);
		} catch(HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//				signOut(context);
			}
		} finally {
			dismissProgressDialog();
		}
		
		return null;
	}
	
	public static boolean updateFavoriteSession(Context context, long eventId, long sessionId) {
	
		showProgressDialog(context);
		try {
			return getGreenhouseOperations(context).updateFavoriteSession(eventId, sessionId);
		} catch(Exception e) {
			Log.e(TAG, e.getLocalizedMessage());
		} finally {
			dismissProgressDialog();
		}
		
		return false;
	}
}
