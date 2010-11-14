package com.springsource.greenhouse.controllers;

import org.springframework.social.greenhouse.GreenhouseOperations;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.springsource.greenhouse.activities.FrontDoorActivity;
import com.springsource.greenhouse.util.Prefs;

public class BaseController {
	
	private static final String TAG = "BaseController";
	private static ProgressDialog progressDialog;
	
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
	
	protected static void displayNetworkError(Context context) {
		Toast toast = Toast.makeText(context, "A problem occurred with the network connection while attempting to communicate with Greenhouse.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	protected static void showProgressDialog(Context context) {
		Log.d(TAG, "showing progress dialog");
		BaseController.progressDialog = ProgressDialog.show(context, "",  "Loading. Please wait...", true);
	}
	
	protected static void dismissProgressDialog() {
		Log.d(TAG, "dismissing progress dialog");
		if (BaseController.progressDialog != null) {
			BaseController.progressDialog.dismiss();
		}
	}
	
	//***************************************
    // Private methods
    //***************************************
	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
	}	
}
