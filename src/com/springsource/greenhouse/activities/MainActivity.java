/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.springsource.greenhouse.controllers.GreenhouseConnectManager;

/**
 * @author Roy Clarkson
 */
public class MainActivity extends Activity 
{	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private GreenhouseConnectManager _greenhouseConnectManager;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_greenhouseConnectManager = new GreenhouseConnectManager(getApplicationContext());
		
		Intent intent;
		if (_greenhouseConnectManager.isConnected())
		{		 
			intent = new Intent(this, MainTabWidget.class);
		}
		else 
		{
			intent = new Intent(this, SignInActivity.class);
		}
		
		startActivity(intent);
		finish();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		Uri uri = getIntent().getData();
		if (!_greenhouseConnectManager.isCallbackUrl(uri)) 
		{
			return;
		}

		try 
		{
			String oauthVerifier = uri.getQueryParameter("oauth_verifier");
			_greenhouseConnectManager.updateGreenhouseAccessToken(oauthVerifier);
		}
		catch (Exception e) 
		{
			Log.e(TAG, e.getMessage(), e);
			return;
		}
		
		Intent intent = new Intent(this, MainTabWidget.class);
		startActivity(intent);
		finish();
	}
}
