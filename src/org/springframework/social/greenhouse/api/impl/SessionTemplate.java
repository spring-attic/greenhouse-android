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
package org.springframework.social.greenhouse.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.social.greenhouse.api.EventSession;
import org.springframework.social.greenhouse.api.SessionOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author Roy Clarkson
 */
public class SessionTemplate extends AbstractGreenhouseOperations implements SessionOperations {
	
	private final RestTemplate restTemplate;

	public SessionTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}
	
	public List<EventSession> getSessionsOnDay(long eventId, Date date) {
		String isoDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String url = new StringBuilder().append("events/").append(eventId).append("/sessions/").append(isoDate).toString();
		return restTemplate.getForObject(buildUri(url), EventSessionList.class);
	}

	public List<EventSession> getFavoriteSessions(long eventId) {
		requireUserAuthorization();
		String url = new StringBuilder().append("events/").append(eventId).append("/sessions/favorites").toString();
		return restTemplate.getForObject(buildUri(url), EventSessionList.class);
	}

	public List<EventSession> getConferenceFavoriteSessions(long eventId) {
		String url = new StringBuilder().append("events/").append(eventId).append("/favorites").toString();
		return restTemplate.getForObject(buildUri(url), EventSessionList.class);
	}

	public boolean updateFavoriteSession(long eventId, long sessionId) {
		requireUserAuthorization();
		String url = new StringBuilder().append("events/").append(eventId).append("/sessions/").append(sessionId).append("favorite").toString();
		return restTemplate.exchange(buildUri(url), HttpMethod.PUT, null, Boolean.class).getBody();
	}

	@SuppressWarnings("serial")
	private static class EventSessionList extends ArrayList<EventSession> {}
}
