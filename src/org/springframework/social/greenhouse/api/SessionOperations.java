/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.greenhouse.api;

import java.util.Date;
import java.util.List;


/**
 * Interface defining the operations for retrieving {@link EventSession} information.
 * 
 * @author Roy Clarkson
 */
public interface SessionOperations {
	
	/**
	 * Retrieves a list of sessions for an event that take place on a given day.
	 * 
	 * @param eventId the ID of the {@link Event}
	 * @param date the day for which to retrieve the {@link Event} objects
	 * @return a list of sessions for the given {@link Event} and day
	 */
	List<EventSession> getSessionsOnDay(long eventId, Date date);

	/**
	 * Retrieves a list of sessions marked as favorites by the user
	 * 
	 * @param eventId the ID of the {@link Event}
	 * @return a list of {@link Session} objects marked as favorites
	 */
	List<EventSession> getFavoriteSessions(long eventId);
	
	/**
	 * Retrieves a list of sessions marked as favorites by all users.
	 * 
	 * @param eventId the ID of the {@link Event}
	 * @return a list of {@link Session} objects marked as favorites
	 */
	List<EventSession> getConferenceFavoriteSessions(long eventId);
		
	/**
	 * Updates the favorite status for a session. If the session is not a favorite 
	 * it is marked as a favorite.
	 * 
	 * @param eventId the ID of the {@link Event}
	 * @param sessionId the ID of the {@link Session}
	 * @return true if the {@link Session} is a favorite, and false if it is not
	 */
	boolean updateFavoriteSession(long eventId, long sessionId);
	
	/**
	 * Rate a session. 
	 * @param eventId the ID of the {@link Event}
	 * @param sessionId the ID of the {@link Session}
	 * @param rating a rating value, 1 to 5 for the {@link Session}
	 * @param comment feedback about the {@link Session}
	 * @return the new average rating for the session
	 */
	float rateSession(long eventId, long sessionId, int rating, String comment);
}
