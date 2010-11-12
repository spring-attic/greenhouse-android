package com.springsource.greenhouse.util;

import java.util.List;

import org.springframework.social.greenhouse.Event;

public class SharedDataManager {
	
	private static List<Event> upcomingEvents;
	private static Event currentEvent;
	
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

}
