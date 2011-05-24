package org.springframework.social.greenhouse.api.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.social.greenhouse.api.TweetFeed;
import org.springframework.social.greenhouse.api.TweetOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TweetTemplate extends AbstractGreenhouseOperations implements TweetOperations {
	
	private static final String PAGE_SIZE = "20";
	
	private final RestTemplate restTemplate;

	public TweetTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
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
	
	public void retweet(long eventId, long tweetId) {
		String url = new StringBuilder().append("events/").append(eventId).append("/retweet").toString();
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("tweetId", String.valueOf(tweetId));
		restTemplate.exchange(buildUri(url), HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(postData, null), null);
	}

}
