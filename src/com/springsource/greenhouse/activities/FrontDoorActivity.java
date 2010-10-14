package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.springsource.greenhouse.util.Prefs;

public class FrontDoorActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		boolean loggedIn = Prefs.isLoggedIn(getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE));
		Intent intent = new Intent(this, loggedIn ? MainTabWidget.class : SignInActivity.class);		
		startActivity(intent);
		finish();
	}
}
