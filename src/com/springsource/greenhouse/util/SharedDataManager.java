package com.springsource.greenhouse.util;

import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.types.Event;
import org.springframework.social.greenhouse.types.EventSession;

public class SharedDataManager {
	
	private static List<Event> upcomingEvents;
	private static Event currentEvent;
	private static Date conferenceDay;
	private static EventSession currentSession;
	
	public static void setUpcomingEvents(List<Event> upcomingEvents) {
		SharedDataManager.upcomingEvents = upcomingEvents;
	}

	public static List<Event> getUpcomingEvents() {
		return upcomingEvents;
	}

	public static void setCurrentEvent(Event currentEvent) {
		SharedDataManager.currentEvent = currentEvent;
	}

	public static Event getCurrentEvent() {
		return currentEvent;
	}

	public static void setConferenceDay(Date day) {
		SharedDataManager.conferenceDay = day;
	}
	
	public static Date getConferenceDay() {
		return conferenceDay;
	}

	public static void setCurrentSession(EventSession currentSession) {
		SharedDataManager.currentSession = currentSession;
	}

	public static EventSession getCurrentSession() {
		return currentSession;
	}
}
