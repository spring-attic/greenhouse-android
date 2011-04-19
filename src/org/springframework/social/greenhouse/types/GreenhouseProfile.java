/*
 * Copyright 2010 the original author or authors.
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
package org.springframework.social.greenhouse.types;

import java.io.Serializable;
import java.util.Date;

/**
 * A model class containing a Greenhouse user's profile information.
 * 
 * @author Craig Walls
 * @author Roy Clarkson
 */
public class GreenhouseProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	private final long id;
	private final String screenName;
//	private final String name;
	private final String url;
	private final String profileImageUrl;
//	private final String description;
//	private final String location;
//	private final Date createdDate;
	
	public GreenhouseProfile(long id, String screenName, String name, String url, String profileImageUrl, String description, String location, Date createdDate) {
		this.id = id;
		this.screenName = screenName;
//		this.name = name;
		this.url = url;
		this.profileImageUrl = profileImageUrl;
//		this.description = description;
//		this.location = location;
//		this.createdDate = createdDate;		
	}

	/**
	 * The user's Greenhouse ID
	 * 
	 * @return The user's Greenhouse ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * The user's Greenhouse screen name
	 * 
	 * @return The user's Greenhouse screen name
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * The user's full name
	 * 
	 * @return The user's full name
	 */
//	public String getName() {
//		return name;
//	}

	/**
	 * The user's URL
	 * 
	 * @return The user's URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * The user's description
	 * 
	 * @return The user's description
	 */
//	public String getDescription() {
//		return description;
//	}

	/**
	 * The user's location
	 * 
	 * @return The user's location
	 */
//	public String getLocation() {
//		return location;
//	}

	/**
	 * <p>
	 * The URL of the user's profile image in "normal" size (48x48).
	 * </p>
	 * 
	 * @return The URL of the user's normal-sized profile image.
	 */
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	/**
	 * <p>
	 * The URL of the user's profile.
	 * </p>
	 * 
	 * @return The URL of the user's profile.
	 */
	public String getProfileUrl() {
		return "http://10.0.2.2:8080/greenhouse/members/" + screenName;
	}

	/**
	 * The date that the Greenhouse profile was created.
	 * 
	 * @return The date that the Greenhouse profile was created.
	 */
//	public Date getCreatedDate() {
//		return createdDate;
//	}
}
