package com.springsource.greenhouse.controllers;

import org.springframework.social.greenhouse.GreenhouseProfile;
import org.springframework.web.client.ResourceAccessException;

import android.app.Activity;
import android.util.Log;


public class ProfileController extends BaseController {

	//***************************************
    // Public methods
    //***************************************	
	public static GreenhouseProfile getProfile(Activity activity) {

		try {
			return getGreenhouseOperations(activity).getUserProfile();
		} catch(ResourceAccessException e) {
			Log.e("ProfileController", e.getLocalizedMessage());
		}
		
		return null;
	}
}
