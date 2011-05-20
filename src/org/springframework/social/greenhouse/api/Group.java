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


/**
 * Model class representing a Greenhouse group.
 * 
 * @author Craig Walls
 */
public class Group {

	private String id;

	private String label;

	/**
	 * The group ID.
	 * 
	 * @return the group ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * The group's label.
	 * 
	 * @return the group's label.
	 */
	public String getLabel() {
		return label;
	}
}
