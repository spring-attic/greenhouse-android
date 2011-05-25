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

import java.net.URI;

import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Roy Clarkson
 */
class AbstractGreenhouseOperations {
	
	private final boolean isAuthorizedForUser;

	public AbstractGreenhouseOperations(boolean isAuthorizedForUser) {
		this.isAuthorizedForUser = isAuthorizedForUser;
	}
	
	protected void requireUserAuthorization() {
		if (!isAuthorizedForUser) {
			throw new IllegalStateException("User authorization required: GreenhouseTemplate must be created with OAuth credentials to perform this operation.");
		}
	}
	
	protected URI buildUri(String path) {
		return buildUri(path, EMPTY_PARAMETERS);
	}
	
	protected URI buildUri(String path, String parameterName, String parameterValue) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set(parameterName, parameterValue);
		return buildUri(path, parameters);
	}
	
	protected URI buildUri(String path, MultiValueMap<String, String> parameters) {
		URIBuilder uriBuilder = URIBuilder.fromUri(API_URL_BASE + path);
		
		for (String paramName : parameters.keySet()) {
			uriBuilder.queryParam(paramName, String.valueOf(parameters.get(paramName)));
		}
		
		URI uri = uriBuilder.build();
		return uri;
	}
	
//	private static final String API_URL_BASE = "http://10.0.2.2:8080/greenhouse/";
	private static final String API_URL_BASE = "https://greenhouse.springsource.org/";
	
	private static final LinkedMultiValueMap<String, String> EMPTY_PARAMETERS = new LinkedMultiValueMap<String, String>();
}