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
package org.springframework.social.greenhouse.connect;

import org.springframework.social.greenhouse.api.Greenhouse;
import org.springframework.social.greenhouse.api.impl.GreenhouseTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * Greenhouse ServiceProvider implementation.
 * @author Roy Clarkson
 */
public class GreenhouseServiceProvider extends AbstractOAuth2ServiceProvider<Greenhouse> {
	
	private final String apiUrlBase;
	
	/**
	 * Creates a GreenhouseServiceProvider for the given client ID and secret.
	 * @param clientId The application's Client ID as assigned by Greenhouse 
	 * @param clientSecret The application's Client Secret as assigned by Greenhouse
	 */
	public GreenhouseServiceProvider(String clientId, String clientSecret) {
		super(new GreenhouseOAuth2Template(clientId, clientSecret));
		this.apiUrlBase = null;
	}
	
	/**
	 * Creates a GreenhouseServiceProvider for the given client ID and secret.
	 * @param clientId The application's Client ID as assigned by Greenhouse 
	 * @param clientSecret The application's Client Secret as assigned by Greenhouse
	 * @param apiUrlBase The application's API base URL
	 */
	public GreenhouseServiceProvider(String clientId, String clientSecret, String apiUrlBase) {
		super(new GreenhouseOAuth2Template(clientId, clientSecret));
		this.apiUrlBase = apiUrlBase;
	}

	public Greenhouse getApi(String accessToken) {
		return new GreenhouseTemplate(accessToken, getApiUrlBase());
	}
	
	private String getApiUrlBase() {
		return apiUrlBase;
	}
	
}
