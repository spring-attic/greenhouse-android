package com.springsource.greenhouse.controllers;

import java.util.concurrent.RejectedExecutionException;

import android.content.Context;
import android.content.Intent;

public class NavigationManager {
		
	//***************************************
    // Public methods
    //***************************************
	public static boolean startActivity(Context context, Class<?> activity) {
		try {				
			Intent intent = new Intent();
		    intent.setClass(context, activity);
		    context.startActivity(intent);
		} catch (RejectedExecutionException ex) { }
		
	    return true;
	}	
}
