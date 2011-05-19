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

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.greenhouse.api.EventOperations;
import org.springframework.social.greenhouse.api.GreenhouseApi;
import org.springframework.social.greenhouse.api.SessionOperations;
import org.springframework.social.greenhouse.api.UserOperations;
import org.springframework.social.oauth1.ProtectedResourceClientFactory;
import org.springframework.web.client.RestTemplate;

/**
 * This is the central class for interacting with Greenhouse.
 * 
 * @author Roy Clarkson
 */
public class GreenhouseTemplate implements GreenhouseApi {
	
	private boolean isAuthorizedForUser;
	
	private final RestTemplate restTemplate;
	
	private final UserOperations userOperations;
	
	private final EventOperations eventOperations;
	
	private final SessionOperations sessionOperations;
	
	/**
	 * Create a new instance of GreenhouseTemplate.
	 * @param apiKey the application's API key
	 * @param apiSecret the application's API secret
	 * @param accessToken an access token acquired through OAuth authentication with Greenhouse
	 * @param accessTokenSecret an access token secret acquired through OAuth authentication with Greenhouse
	 */
	public GreenhouseTemplate(String apiKey, String apiSecret, String accessToken, String accessTokenSecret) {
		this(ProtectedResourceClientFactory.create(apiKey, apiSecret, accessToken, accessTokenSecret), true);
	}
	
	private GreenhouseTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		this.restTemplate = restTemplate;
		this.isAuthorizedForUser = isAuthorizedForUser;
		registerGreenhouseModule(restTemplate);
		restTemplate.setErrorHandler(new GreenhouseErrorHandler());
		this.userOperations = new UserTemplate(restTemplate, isAuthorizedForUser);
		this.eventOperations = new EventTemplate(restTemplate, isAuthorizedForUser);
		this.sessionOperations = new SessionTemplate(restTemplate, isAuthorizedForUser);
	}
	
	public boolean isAuthorizedForUser() {
		return isAuthorizedForUser;
	}
	
	public UserOperations userOperations() {
		return userOperations;
	}
	
	public EventOperations eventOperations() {
		return eventOperations;
	}
	
	public SessionOperations sessionOperations() {
		return sessionOperations;
	}
	
	// private helper 

	private void registerGreenhouseModule(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		
		for (HttpMessageConverter<?> converter : converters) {
			if(converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				ObjectMapper objectMapper = new ObjectMapper();				
				objectMapper.registerModule(new GreenhouseModule());
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}
	
	// subclassing hooks

	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}
}