package com.springsource.greenhouse.controllers;

import org.springframework.social.greenhouse.GreenhouseApi;

import android.content.Context;

public class BaseController 
{	
	protected static final String TAG = BaseController.class.getSimpleName();
	
	private static GreenhouseConnectManager _sharedGreenhouseConnectManager;
	
	private Context _context;
	
	
	//***************************************
    // Constructors
    //***************************************
	protected BaseController(Context context) 
	{
		_context = context;
	}
	
	
	//***************************************
    // Protected methods
    //***************************************
	protected GreenhouseApi getGreenhouseApi() 
	{ 
		return getGreenhouseConnectManager().getGreenhouseApi();
	}

//	protected static void signOut(Activity activity) {
//		Prefs.disconnect(mGreenhousePreferences);
//    	NavigationManager.startActivity(activity, FrontDoorActivity.class);
//    	activity.finish();
//	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private GreenhouseConnectManager getGreenhouseConnectManager() 
	{
		if (_sharedGreenhouseConnectManager == null) 
		{
			_sharedGreenhouseConnectManager = new GreenhouseConnectManager(_context);
		}
		
		return _sharedGreenhouseConnectManager;
	}
}
