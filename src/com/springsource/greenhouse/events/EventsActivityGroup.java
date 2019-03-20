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
package com.springsource.greenhouse.events;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/*
 * Good example from Henrik Larsen Toft on how to set up a Nested TabActivity  
 * https://blog.henriklarsentoft.com/2010/07/android-tabactivity-nested-activities/
 */

public class EventsActivityGroup extends ActivityGroup {
	  
	public static EventsActivityGroup group;  
   
	// Need to keep track of the history so the back-button will work properly.  
	private ArrayList<View> history;

	//***************************************
	// Activity methods
	//***************************************
	@Override  
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		this.history = new ArrayList<View>();  
		group = this;  
   
		// Start the root activity within the group and get its view
		Intent intent = new Intent();
		intent.setClass(this, EventsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		LocalActivityManager activityManager = getLocalActivityManager();
		Window window = activityManager.startActivity("EventsActivity", intent);
		View view = window.getDecorView();  

		// Replace the view of this ActivityGroup  
		replaceView(view);
	}  
	
	@Override
	public void onBackPressed() {
		EventsActivityGroup.group.back();
		return;
	}

	//***************************************
	// Public methods
	//***************************************
	public void replaceView(View v) {  
	    // Adds the old one to history  
		history.add(v);  
                
		// Changes this Groups View to the new View.  
		setContentView(v);  
	}  
	
	public void back() {
		if (history.size() > 0) {
			history.remove(history.size()-1);
			setContentView(history.get(history.size()-1));
		} else {
			finish();
		}
	}
}
