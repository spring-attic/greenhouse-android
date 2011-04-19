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
package org.springframework.social.greenhouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.greenhouse.types.Event;
import org.springframework.social.greenhouse.types.EventSession;
import org.springframework.social.greenhouse.types.GreenhouseProfile;
import org.springframework.social.oauth1.ProtectedResourceClientFactory;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * This is the central class for interacting with Greenhouse.
 * </p>
 * <p>
 * Greenhouse operations require OAuth authentication with the server.
 * Therefore, GreenhouseTemplate must be constructed with the minimal information
 * required to sign requests with and OAuth 1 Authorization header.
 * </p>
 * @author Roy Clarkson
 */
public class GreenhouseTemplate implements GreenhouseApi {

	private final RestTemplate restTemplate;
	
	private final HttpHeaders jsonAcceptingHeaders;
	
	/**
	 * Creates a new GreenhouseTemplate given the minimal amount of information needed to sign requests with OAuth 1 credentials.
	 * @param apiKey the application's API key
	 * @param apiSecret the application's API secret
	 * @param accessToken an access token acquired through OAuth authentication with GreenhouseTemplate
	 * @param accessTokenSecret an access token secret acquired through OAuth authentication with GreenhouseTemplate
	 */
	public GreenhouseTemplate(String apiKey, String apiSecret, String accessToken, String accessTokenSecret) {
		this.restTemplate = ProtectedResourceClientFactory.create(apiKey, apiSecret, accessToken, accessTokenSecret);
		
		this.jsonAcceptingHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		jsonAcceptingHeaders.setAccept(acceptableMediaTypes);
	}
	
	public String getProfileId() {
		return null;
	}
	
	public GreenhouseProfile getUserProfile() {
		return restTemplate.getForObject(GET_CURRENT_USER_INFO_URL, GreenhouseProfile.class);
	}
	
	public List<Event> getUpcomingEvents() {
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Event[]> response = restTemplate.exchange(GET_UPCOMING_EVENTS_URL, HttpMethod.GET, requestEntity, Event[].class);
		return Arrays.asList(response.getBody());
	}
	
	public List<Event> getEventsAfter(Date date) {
		String isoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000-00:00").format(date);
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Event[]> response = restTemplate.exchange(GET_EVENTS_AFTER_DATE_URL, HttpMethod.GET, requestEntity, Event[].class, isoDate);
		return Arrays.asList(response.getBody());		
	}
	
	public List<EventSession> getSessionsOnDay(long eventId, Date day) {
		return null;
	}
	
	public List<EventSession> getFavoriteSessions(long eventId) {
		return null;
	}
	
	public List<EventSession> getConferenceFavoriteSessions(long eventId) {
		return null;
	}
	
	public boolean updateFavoriteSession(long eventId, long sessionId) {
		return false;
	}
	
	// TODO: move the base url to a configuration file
	static final String BASE_URL = "http://10.0.2.2:8080/greenhouse/";
	static final String GET_CURRENT_USER_INFO_URL = BASE_URL + "members/@self";
	static final String GET_UPCOMING_EVENTS_URL = BASE_URL + "events";
	static final String GET_EVENTS_AFTER_DATE_URL = BASE_URL + "events?after={dateTime}";
}
