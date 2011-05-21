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
	 * 
	 * @param eventId
	 * 				the id of the event
	 * 
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEvent(long eventId);
	
	/**
	 * Retrieves a list of tweets for an Event. Use the page parameter to retrieve older tweets.
	 * 
	 * @param eventId
	 * 				the id of the event
	 * 
	 * @param page
	 * 				the page of tweets 
	 *
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEvent(long eventId, int page);
	
	/**
	 * Retrieves a list of tweets for an Event Session.
	 * 
	 * @param eventId
	 * 				the id of the event
	 * 
	 * @param sessionId
	 * 				the id of the session
	 *
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEventSession(long eventId, long sessionId);
	
	/**
	 * Retrieves a list of tweets for an Event. Use the page parameter to retrieve older tweets.
	 * 
	 * @param eventId
	 * 				the id of the event
	 * 
	 * @param sessionId
	 * 				the id of the session
	 * 
	 * @param page
	 * 				the page of tweets 
	 *
	 * @return a {@link TweetFeed} object that contains a list of {@link Tweet} objects.
	 */
	TweetFeed getTweetsForEventSession(long eventId, long sessionId, int page);
	
}
