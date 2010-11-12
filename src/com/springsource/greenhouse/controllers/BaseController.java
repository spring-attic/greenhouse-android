package com.springsource.greenhouse.controllers;

import org.springframework.social.greenhouse.GreenhouseOperations;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.springsource.greenhouse.activities.FrontDoorActivity;
import com.springsource.greenhouse.util.Prefs;

public class BaseController {
	
	//***************************************
    // Protected methods
    //***************************************
	protected static GreenhouseOperations getGreenhouseOperations(Context context) { 
		return Prefs.getGreenhouseOperations(getSharedPreferences(context));
	}

	protected static void signOut(Activity activity) {
		Prefs.disconnect(getSharedPreferences(activity));
    	NavigationManager.startActivity(activity, FrontDoorActivity.class);
    	activity.finish();
	}
	
	//***************************************
    // Private methods
    //***************************************
	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
	}	
}
