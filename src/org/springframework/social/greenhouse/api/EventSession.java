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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Model class that represents a Greenhouse event session.
 * 
 * @author Craig Walls
 * @author Roy Clarkson
 */
public class EventSession {
	
	private long id;

	private String title;

	private String description;

	private Date startTime;

	private Date endTime;

	private String hashtag;

	private float rating;

	private boolean favorite;

	private Room room;

	private List<Leader> leaders;
	
	public EventSession(long id, String title, String description, Date startTime, Date endTime, String hashtag, float rating, boolean favorite, Room room, List<Leader> leaders) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hashtag = hashtag;
		this.rating = rating;
		this.favorite = favorite;
		this.room = room;
		this.leaders = leaders;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
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

	public String getHashtag() {
		return hashtag;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public float getRating() {
		return rating;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public Room getRoom() {
		return room;
	}

	public List<Leader> getLeaders() {
		return leaders;
	}
	
//	public void setLeaders(List<Leader> leaders) 
//	{
//		this.leaders = leaders;
//	}
	
	public String getJoinedLeaders(String separator) {		
		String s = "";
		int size = leaders.size();
		
		for (int i = 0; i < size; i++) {
			Leader leader = leaders.get(i);
			s += leader.getName();
			
			if (i < size-1) {
				s += separator;
			}
		}
		
		return s;
	}
	
	public String getFormattedTimeSpan() {
		String startTime = new SimpleDateFormat("h:mma").format(getStartTime());
		String endTime = new SimpleDateFormat("h:mma, EEE").format(getEndTime());
		return startTime + " - " + endTime;
	}
	
}
