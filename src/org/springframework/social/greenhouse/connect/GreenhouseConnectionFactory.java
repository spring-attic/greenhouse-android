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

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.greenhouse.api.Greenhouse;

/**
 * Greenhouse ConnectionFactory implementation.
 * @author Roy Clarkson
 */
public class GreenhouseConnectionFactory extends OAuth2ConnectionFactory<Greenhouse> {

	/**
	 * Creates a GreenhouseConnectionFactory for the given client ID and secret.
	 * Using this constructor, no application namespace is set
	 * @param clientId The application's Client ID as assigned by Greenhouse 
	 * @param clientSecret The application's Client Secret as assigned by Greenhouse
	 */
	public GreenhouseConnectionFactory(String clientId, String clientSecret) {
		super("greenhouse", new GreenhouseServiceProvider(clientId, clientSecret), new GreenhouseAdapter());
	}

	/**
	 * Creates a GreenhouseConnectionFactory for the given application ID, secret, and namespace.
	 * @param clientId The application's Client ID as assigned by Greenhouse 
	 * @param clientSecret The application's Client Secret as assigned by Greenhouse
	 * @param apiUrlBase The application's API base URL
	 */
	public GreenhouseConnectionFactory(String clientId, String clientSecret, String apiUrlBase) {
		super("greenhouse", new GreenhouseServiceProvider(clientId, clientSecret, apiUrlBase), new GreenhouseAdapter());
	}
	
}
