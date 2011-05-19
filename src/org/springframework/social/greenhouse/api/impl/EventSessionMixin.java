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

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.greenhouse.api.Leader;
import org.springframework.social.greenhouse.api.Room;

/**
 * Mixin class for adding Jackson annotations to EventSession.
 * 
 * @author Roy Clarkson
 */
@JsonIgnoreProperties(ignoreUnknown=true)
abstract class EventSessionMixin {
	@JsonCreator
	EventSessionMixin(
			@JsonProperty("id") long id, 
			@JsonProperty("title") String title, 
			@JsonProperty("description") String description,
			@JsonProperty("startTime") Date startTime,
			@JsonProperty("endTime") Date endTime,
			@JsonProperty("hashtag") String hashtag,
			@JsonProperty("rating") float rating,
			@JsonProperty("favorite") boolean favorite,
			@JsonProperty("room") Room room,
			@JsonProperty("leaders") List<Leader> leaders) {}
}
