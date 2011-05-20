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

import java.util.Map;

/**
 * @author Roy Clarkson
 */
public class Venue {

	private long id;

	private String name;

	private String postalAddress;

	private String locationHint;

	private Map<String, Double> location;
	
	public Venue(long id, String name, String postalAddress, String locationHint, Map<String, Double> location) {
		this.id = id;
		this.name = name;
		this.postalAddress = postalAddress;
		this.locationHint = locationHint;
		this.location = location;
	}

	public double getLatitude() {
		return location.get("latitude");
	}

	public double getLongitude() {
		return location.get("longitude");
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public String getLocationHint() {
		return locationHint;
	}
}
