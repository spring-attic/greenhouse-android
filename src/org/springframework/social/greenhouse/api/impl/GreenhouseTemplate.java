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
import org.springframework.social.greenhouse.api.TweetOperations;
import org.springframework.social.greenhouse.api.UserOperations;
import org.springframework.social.oauth1.AbstractOAuth1ApiTemplate;

/**
 * This is the central class for interacting with Greenhouse.
 * 
 * @author Roy Clarkson
 */
public class GreenhouseTemplate extends AbstractOAuth1ApiTemplate implements GreenhouseApi {
		
	private UserOperations userOperations;
	
	private EventOperations eventOperations;
	
	private SessionOperations sessionOperations;
	
	private TweetOperations tweetOperations;
	
	/**
	 * Create a new instance of GreenhouseTemplate.
	 * @param apiKey the application's API key
	 * @param apiSecret the application's API secret
	 * @param accessToken an access token acquired through OAuth authentication with Greenhouse
	 * @param accessTokenSecret an access token secret acquired through OAuth authentication with Greenhouse
	 */
	public GreenhouseTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		registerGreenhouseJsonModule();
		getRestTemplate().setErrorHandler(new GreenhouseErrorHandler());
		initSubApis();
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
	
	public TweetOperations tweetOperations() {
		return tweetOperations;
	}
	
	// private helper 

	private void registerGreenhouseJsonModule() {
		List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if(converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				ObjectMapper objectMapper = new ObjectMapper();				
				objectMapper.registerModule(new GreenhouseModule());
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}
	
	private void initSubApis() {
		this.userOperations = new UserTemplate(getRestTemplate(), isAuthorizedForUser());
		this.eventOperations = new EventTemplate(getRestTemplate(), isAuthorizedForUser());
		this.sessionOperations = new SessionTemplate(getRestTemplate(), isAuthorizedForUser());
		this.tweetOperations = new TweetTemplate(getRestTemplate(), isAuthorizedForUser());
	}
}