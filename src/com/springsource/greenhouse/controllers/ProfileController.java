package com.springsource.greenhouse.controllers;

import org.springframework.social.greenhouse.api.GreenhouseProfile;

import android.content.Context;

public class ProfileController extends BaseController 
{
	
	//***************************************
    // Constructors
    //***************************************
	
	public ProfileController(Context context) 
	{
		
	}
	
	
	//***************************************
    // Public methods
    //***************************************
	public GreenhouseProfile getProfile() 
	{
//		return getGreenhouseApi().getUserProfile();
		return null;
	}
}
