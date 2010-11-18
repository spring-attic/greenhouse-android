package com.springsource.greenhouse.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.springframework.social.greenhouse.Event;

import android.app.Activity;
import android.util.Log;

public class EventsController extends BaseController {
	
	private static final String TAG = "EventsController";
	
	//***************************************
    // Public methods
    //***************************************
	public static List<Event> getUpcomingEvents(Activity activity) {

		showProgressDialog(activity);
		try {
			return getGreenhouseOperations(activity).getUpcomingEvents();
//		} catch(ResourceAccessException e) {
//			Log.e(TAG, e.getLocalizedMessage());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			Writer result = new StringWriter();
			e.printStackTrace(new PrintWriter(result));
		} finally {
			dismissProgressDialog();
		}
		
		return null;
	}
}
