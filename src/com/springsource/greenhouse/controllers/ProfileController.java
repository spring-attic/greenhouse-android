package com.springsource.greenhouse.controllers;

import org.springframework.social.greenhouse.GreenhouseProfile;

import android.content.Context;
import android.util.Log;

public class ProfileController extends BaseController {
	private static final String TAG = "ProfileController";

	
	//***************************************
    // Constructors
    //***************************************
	public ProfileController(Context context) {
		super(context);
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public GreenhouseProfile getProfile() {
		Log.d(TAG, "fetching profile");
		return getGreenhouseOperations().getUserProfile();
	}
}
