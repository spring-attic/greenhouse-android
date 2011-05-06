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

import java.io.Serializable;

/**
 * A model class containing a Greenhouse user's profile information.
 * 
 * @author Craig Walls
 * @author Roy Clarkson
 */
public class GreenhouseProfile implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private final long id;
	
	private final String screenName;
	
	private final String name;
	
	private final String profileImageUrl;
	
	public GreenhouseProfile(long id, String screenName, String profileImageUrl) 
	{
		this.id = id;
		this.screenName = screenName;
		this.name = screenName;
		this.profileImageUrl = profileImageUrl;		
	}

	/**
	 * The user's Greenhouse ID
	 * 
	 * @return The user's Greenhouse ID
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * The user's Greenhouse screen name
	 * 
	 * @return The user's Greenhouse screen name
	 */
	public String getScreenName() 
	{
		return screenName;
	}
	
	/**
	 * The user's full name
	 * 
	 * @return The user's full name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * <p>
	 * The URL of the user's profile image.
	 * </p>
	 * 
	 * @return The URL of the user's profile image.
	 */
	public String getProfileImageUrl() 
	{
		return profileImageUrl;
	}

	/**
	 * <p>
	 * The URL of the user's profile.
	 * </p>
	 * 
	 * @return The URL of the user's profile.
	 */
	public String getProfileUrl() 
	{
		return "http://10.0.2.2:8080/greenhouse/members/" + id;
	}
}
