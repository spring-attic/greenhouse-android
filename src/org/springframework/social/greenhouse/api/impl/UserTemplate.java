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

import org.springframework.social.greenhouse.api.GreenhouseProfile;
import org.springframework.social.greenhouse.api.UserOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the {@link UserOperations} interface providing binding to Greenhouse's user-oriented REST resources.
 * 
 * @author Roy Clarkson
 */
class UserTemplate extends AbstractGreenhouseOperations implements UserOperations
{
	private final RestTemplate restTemplate;

	public UserTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) 
	{
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	public long getAccountId() 
	{
		requireUserAuthorization();
		return getUserProfile().getAccountId();
	}

	public String getDisplayName() 
	{
		requireUserAuthorization();
		return getUserProfile().getDisplayName();
	}

	public GreenhouseProfile getUserProfile() 
	{
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("members/@self"), GreenhouseProfile.class);
	}

	public GreenhouseProfile getUserProfile(long userId) {
		return restTemplate.getForObject(buildUri("members/" + userId), GreenhouseProfile.class);
	}
	
}
