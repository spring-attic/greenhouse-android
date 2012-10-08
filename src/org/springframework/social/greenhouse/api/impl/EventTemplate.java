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
package org.springframework.social.greenhouse.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.social.greenhouse.api.Event;
import org.springframework.social.greenhouse.api.EventOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author Roy Clarkson
 */
public class EventTemplate extends AbstractGreenhouseOperations implements EventOperations {
	
	private final RestTemplate restTemplate;

	public EventTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, String apiUrlBase) {
		super(isAuthorizedForUser, apiUrlBase);
		this.restTemplate = restTemplate;
	}
	
	public List<Event> getUpcomingEvents() {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("events"), EventList.class);
	}

	public List<Event> getEventsAfter(Date date) {
		String isoDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000-00:00").format(date);
		return restTemplate.getForObject(buildUri("events", "after", isoDate), EventList.class);
	}

	@SuppressWarnings("serial")
	private static class EventList extends ArrayList<Event> {}
}
