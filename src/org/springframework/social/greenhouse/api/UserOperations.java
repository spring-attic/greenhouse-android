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
 * Interface defining the operations for retrieving information about Greenhouse users.
 * 
 * @author Roy Clarkson
 */
public interface UserOperations {
	
	/**
	 * Retrieves the user's Greenhouse profile ID.
	 * 
	 * @return the user's Greenhouse profile ID.
	 */
	long getAccountId();
	
	/**
	 * Retrieves the authenticated user's Greenhouse screen name
	 * 
	 * @return the user's screen name
	 */
	String getDisplayName();
	
	/**
	 * Retrieves the authenticated user's Greenhouse profile details.
	 * 
	 * @return a {@link GreenhouseProfile} object representing the user's profile.
	 */
	GreenhouseProfile getUserProfile();
	
	/**
	 * Retrieves a specific user's Greenhouse profile details.
	 * 
	 * @param userId the user ID for the user whose details are to be retrieved.
	 * @return a {@link GreenhouseProfile} object representing the user's profile.
	 */
	GreenhouseProfile getUserProfile(long userId);
}
