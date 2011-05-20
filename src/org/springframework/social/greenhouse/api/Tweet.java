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

import java.util.Date;

/**
 * @author Roy Clarkson
 */
public class Tweet {
	
	private long id;
	
	private String text;
	
	private Date createdAt;
	
	private String fromUser;
	
	private String profileImageUrl;
	
	private long userId;
	
	private String languageCode;
	
	private String source;
	
//	private Bitmap profileImage;
	
	public Tweet(long id, String text, Date createdAt, String fromUser, String profileImageUrl, long userId, String languageCode, String source) {
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.fromUser = fromUser;
		this.profileImageUrl = profileImageUrl;
		this.userId = userId;
		this.languageCode = languageCode;
		this.source = source;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getFromUser() {
		return fromUser;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public long getUserId() {
		return userId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public String getSource() {
		return source;
	}

}
