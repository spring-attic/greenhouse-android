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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.social.greenhouse.api.TweetFeed;
import org.springframework.social.greenhouse.api.TweetOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Roy Clarkson
 */
public class TweetTemplate extends AbstractGreenhouseOperations implements TweetOperations {
	
	private static final String PAGE_SIZE = "40";
	
	private final RestTemplate restTemplate;

	public TweetTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, String apiBaseUrl) {
		super(isAuthorizedForUser, apiBaseUrl);
		this.restTemplate = restTemplate;
	}

	public TweetFeed getTweetsForEvent(long eventId) {
		return getTweetsForEvent(eventId, 1);
	}

	public TweetFeed getTweetsForEvent(long eventId, int page) {
		String url = new StringBuilder().append("events/").append(eventId).append("/tweets").append("?page=").append(page).append("&pageSize=").append(PAGE_SIZE).toString();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
//		parameters.add("page", page));
//		parameters.add("pageSize", PAGE_SIZE);
		return restTemplate.getForObject(buildUri(url, parameters), TweetFeed.class);
	}
	
	public TweetFeed getTweetsForEventSession(long eventId, long sessionId) {
		return getTweetsForEventSession(eventId, sessionId, 1);
	}

	public TweetFeed getTweetsForEventSession(long eventId, long sessionId, int page) {
		String url = new StringBuilder().append("events/").append(eventId).append("/sessions/").append(sessionId).append("/tweets").append("?page=").append(page).append("&pageSize=").append(PAGE_SIZE).toString();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
//		parameters.add("page", page));
//		parameters.add("pageSize", PAGE_SIZE);
		return restTemplate.getForObject(buildUri(url, parameters), TweetFeed.class);
	}
	
	public void postTweetForEvent(long eventId, String status) {
		requireAuthorization();
		String url = new StringBuilder().append("events/").append(eventId).append("/tweets").toString();
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("status", status);
		restTemplate.exchange(buildUri(url), HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(postData, null), null);
	}
	
	public void postTweetForEventSession(long eventId, long sessionId, String status) {
		requireAuthorization();
		String url = new StringBuilder().append("events/").append(eventId).append("/sessions/").append(sessionId).append("/tweets").toString();
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("status", status);
		restTemplate.exchange(buildUri(url), HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(postData, null), null);
	}
	
	public void retweetForEvent(long eventId, long tweetId) {
		requireAuthorization();
		String url = new StringBuilder().append("events/").append(eventId).append("/retweet").toString();
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("tweetId", String.valueOf(tweetId));
		restTemplate.exchange(buildUri(url), HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(postData, null), null);
	}
	
	public void retweetForEventSession(long eventId, long sessionId, long tweetId) {
		requireAuthorization();
		String url = new StringBuilder().append("events/").append(eventId).append("/sessions/").append(sessionId).append("/retweet").toString();
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("tweetId", String.valueOf(tweetId));
		restTemplate.exchange(buildUri(url), HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(postData, null), null);
	}
	
}
