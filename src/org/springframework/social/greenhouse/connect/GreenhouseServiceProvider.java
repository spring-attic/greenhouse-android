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
package org.springframework.social.greenhouse.connect;

import org.springframework.social.greenhouse.api.GreenhouseApi;
import org.springframework.social.greenhouse.api.impl.GreenhouseTemplate;
import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Template;

/**
 * Greenhouse ServiceProvider implementation.
 * 
 * @author Roy Clarkson
 */
public class GreenhouseServiceProvider extends AbstractOAuth1ServiceProvider<GreenhouseApi> {

	public GreenhouseServiceProvider(String consumerKey, String consumerSecret) {
		super(consumerKey, consumerSecret, new OAuth1Template(consumerKey, consumerSecret,
			"http://10.0.2.2:8080/greenhouse/oauth/request_token",
			"http://10.0.2.2:8080/greenhouse/oauth/confirm_access",
			"http://10.0.2.2:8080/greenhouse/oauth/access_token"));
	}

	public GreenhouseApi getApi(String accessToken, String secret) {
		return new GreenhouseTemplate(getConsumerKey(), getConsumerSecret(), accessToken, secret);
	}
}
