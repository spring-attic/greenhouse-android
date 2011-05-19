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
import java.util.List;


/**
 * Interface defining the operations for retrieving {@link Event} information.
 * 
 * @author Roy Clarkson
 */
public interface EventOperations {
	
	/**
	 * Retrieves a list of events occurring in the future.
	 *
	 * @return a list of upcoming {@link Event} objects.
	 */
	List<Event> getUpcomingEvents();
	
	/**
	 * Retrieve a list of events that take place after a given time.
	 * 
	 * @param date the starting point used to filter the list of events.
	 * @return a list of {@link Event} objects that take place after the given time.
	 */
	List<Event> getEventsAfter(Date date);
}
