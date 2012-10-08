/*
 * Copyright 2011-2012 the original author or authors.
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
import org.springframework.social.greenhouse.api.Greenhouse;
import org.springframework.social.greenhouse.api.SessionOperations;
import org.springframework.social.greenhouse.api.TweetOperations;
import org.springframework.social.greenhouse.api.UserOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * This is the central class for interacting with Greenhouse.
 * @author Roy Clarkson
 */
public class GreenhouseTemplate extends AbstractOAuth2ApiBinding implements Greenhouse {
	
	private final String apiUrlBase;
		
	private UserOperations userOperations;
	
	private EventOperations eventOperations;
	
	private SessionOperations sessionOperations;
	
	private TweetOperations tweetOperations;
	
	/**
	 * Create a new instance of GreenhouseTemplate.
	 * @param accessToken an access token acquired through OAuth 2 authentication with Greenhouse
	 */
	public GreenhouseTemplate(String accessToken, String apiUrlBase) {
		super(accessToken);
		this.apiUrlBase = apiUrlBase;
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
	
	private String getApiUrlBase() {
		return apiUrlBase;
	}
	
	private void initSubApis() {
		this.userOperations = new UserTemplate(getRestTemplate(), isAuthorized(), getApiUrlBase());
		this.eventOperations = new EventTemplate(getRestTemplate(), isAuthorized(), getApiUrlBase());
		this.sessionOperations = new SessionTemplate(getRestTemplate(), isAuthorized(), getApiUrlBase());
		this.tweetOperations = new TweetTemplate(getRestTemplate(), isAuthorized(), getApiUrlBase());
	}
	
}