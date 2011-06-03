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
 * A model class containing a Greenhouse user's profile information.
 * 
 * @author Craig Walls
 * @author Roy Clarkson
 */
public class GreenhouseProfile {

	private final long accountId;
	
	private final String displayName;
	
	private final String pictureUrl;
	
	private String apiUrlBase;
	
	public GreenhouseProfile(long accountId, String displayName, String pictureUrl) {
		this.accountId = accountId;
		this.displayName = displayName;
		
		/*
		 *  Android accesses localhost through a proxy, so the image url address is wrong
		 *  when running on the localhost server.
		 */
		// TODO: figure out a better way to handle this hack
		this.pictureUrl = pictureUrl.replace("localhost", "10.0.2.2");
	}

	/**
	 * The user's Greenhouse Account ID
	 * 
	 * @return The user's Greenhouse Account ID
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * The user's Greenhouse screen name
	 * 
	 * @return The user's Greenhouse screen name
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * The URL of the user's profile image.
	 * 
	 * @return The URL of the user's profile image.
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}

	/**
	 * The URL of the user's profile. 
	 * 
	 * @return The URL of the user's profile.
	 */
	public String getProfileUrl() {
		return apiUrlBase + "members/" + accountId;
	}
	
	/**
	 * Sets the base URL for accessing the Greenhouse API
	 * 
	 * @param apiUrlBase
	 */
	public void setApiUrlBase(String apiUrlBase) {
		this.apiUrlBase = apiUrlBase;
	}
	
}
