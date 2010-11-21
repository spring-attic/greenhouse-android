package com.springsource.greenhouse.controllers;

import org.springframework.social.greenhouse.GreenhouseOperations;

import android.content.Context;
import android.content.SharedPreferences;

import com.springsource.greenhouse.util.Prefs;

public class BaseController {
//	private static final String TAG = "BaseController";
	private SharedPreferences mGreenhousePreferences;
	
	
	//***************************************
    // Constructors
    //***************************************
	protected BaseController(Context context) {
		mGreenhousePreferences = context.getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
	}
	
	//***************************************
    // Protected methods
    //***************************************
	protected GreenhouseOperations getGreenhouseOperations() { 
		return Prefs.getGreenhouseOperations(mGreenhousePreferences);
	}

//	protected static void signOut(Activity activity) {
//		Prefs.disconnect(mGreenhousePreferences);
//    	NavigationManager.startActivity(activity, FrontDoorActivity.class);
//    	activity.finish();
//	}
			
	//***************************************
    // Private methods
    //***************************************
//	private static SharedPreferences getSharedPreferences(Context context) {
//		return context.getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
//	}	
}
