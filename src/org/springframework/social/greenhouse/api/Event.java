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
import java.util.Date;
import java.util.List;

/**
 * Model class that represents a Greenhouse event.
 * 
 * @author Craig Walls
 * @author Roy Clarkson
 */
public class Event implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long id;

	private String title;

	private String location;

	private String hashtag;

	private String description;

	private Date startTime;

	private Date endTime;

	private String slug;

	private String groupName;

	private String groupSlug;

	private Group group;

	private TimeZone timeZone;

	private List<Venue> venues;
	
	public Event(long id, String title, String location, String hashtag, String description, Date startTime, Date endTime, String slug, String groupName, String groupSlug, Group group, TimeZone timeZone, List<Venue> venues) {
		this.id = id;
		this.title = title;
		this.location = location;
		this.hashtag = hashtag;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.slug = slug;
		this.groupName = groupName;
		this.groupSlug = groupSlug;
		this.group = group;
		this.timeZone = timeZone;
		this.venues = venues;
	}

	public List<Venue> getVenues() {
		return venues;
	}

	public void setVenues(List<Venue> venues) {
		this.venues = venues;
	}

	public String getSlug() {
		return slug;
	}

	public Group getGroup() {
		return group;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupSlug() {
		return groupSlug;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public String getHashtag() {
		return hashtag;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}
}
