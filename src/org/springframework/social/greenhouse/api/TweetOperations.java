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


/**
 * Interface defining the operations for retrieving and posting {@link Tweet} information.
 * 
 * @author Roy Clarkson
 */
public interface TweetOperations {

	/**
	 * Retrieves a list of the most recent tweets for an Event.
	 * @param eventId the ID of the event
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEvent(long eventId);
	
	/**
	 * Retrieves a list of tweets for an Event. Use the page parameter to retrieve older tweets.
	 * @param eventId the ID of the event
	 * @param page the page of tweets 
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEvent(long eventId, int page);
	
	/**
	 * Retrieves a list of tweets for an Event Session.
	 * @param eventId the ID of the event 
	 * @param sessionId the ID of the session
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEventSession(long eventId, long sessionId);
	
	/**
	 * Retrieves a list of tweets for an Event Session. Use the page parameter to retrieve older tweets.
	 * @param eventId the ID of the event
	 * @param sessionId the ID of the session
	 * @param page the page of tweets 
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEventSession(long eventId, long sessionId, int page);
	
	/**
	 * Posts a status update to twitter about an event.
 	 * @param eventId the ID of the event
	 * @param status the status to post to twitter
	 */
	void postTweetForEvent(long eventId, String status);
	
	/**
	 * Posts a status update to twitter about a session.
 	 * @param eventId the ID of the event
 	 * @param sessionId the ID of the session
	 * @param status the status to post to twitter
	 */
	void postTweetForEventSession(long eventId, long sessionId, String status);
	
	/**
	 * Posts a retweet of an existing tweet.
 	 * @param eventId the ID of the event
	 * @param tweetId The ID of the tweet to be retweeted
	 */
	void retweetForEvent(long eventId, long tweetId);
	
	/**
	 * Posts a retweet of an existing tweet.
 	 * @param eventId the ID of the event
 	 * @param sessionid the ID of the session
	 * @param tweetId The ID of the tweet to be retweeted
	 */
	void retweetForEventSession(long eventId, long sessionId, long tweetId);
	
}
