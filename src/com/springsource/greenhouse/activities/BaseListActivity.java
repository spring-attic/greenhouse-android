package com.springsource.greenhouse.activities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.springsource.greenhouse.util.Prefs;

public class BaseListActivity extends ListActivity {
	private static final String TAG = "BaseListActivity";
	private ProgressDialog mProgressDialog;
	
	
	//***************************************
    // Protected classes
    //***************************************
	protected SharedPreferences getGreenhousePreferences() {
		return this.getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
	}
	
	protected void showLoadingProgressDialog() {
		mProgressDialog = ProgressDialog.show(this, "",  "Loading. Please wait...", true);
	}
	
	protected void showProgressDialog(String message) {
		mProgressDialog = ProgressDialog.show(this, "",  message, true);
	}
	
	protected void dismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}
	
	protected void logException(Exception e) {
		Log.e(TAG, e.getMessage(), e);
		Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));
	}
	
	protected void processException(Exception e) {
		if (e != null) {
			if (e instanceof ResourceAccessException) {
				displayNetworkError();
			}
			else if (e instanceof HttpClientErrorException) {
				HttpClientErrorException httpError = (HttpClientErrorException) e;
				if (httpError.getStatusCode() ==  HttpStatus.UNAUTHORIZED) {
					displayAuthorizationError();
				}
			}
		}
	}
	
	protected void displayNetworkError() {
		Toast toast = Toast.makeText(this, "A problem occurred with the network connection while attempting to communicate with Greenhouse.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	protected void displayAuthorizationError() {
		Toast toast = Toast.makeText(this, "You are not authorized to connect to Greenhouse. Please sign out and reauthorize the app.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	protected Context getContext() {
		return this;
	}
}
