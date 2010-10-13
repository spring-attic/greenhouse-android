package com.springsource.greenhouse.controllers;

import java.util.concurrent.RejectedExecutionException;

import android.content.Context;
import android.content.Intent;

public class NavigationManager {
		
	public static boolean redirect(Context context, Class<?> to) {
		try {				
			Intent intent = new Intent();
		    intent.setClass(context, to);
		    context.startActivity(intent);
		} catch (RejectedExecutionException ex){ }
		
	    return true;
	}
}
