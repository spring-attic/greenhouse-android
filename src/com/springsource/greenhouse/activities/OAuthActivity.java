package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.springsource.greenhouse.controllers.GreenhouseConnectManager;

public class OAuthActivity extends Activity 
{	
	private static final String TAG = OAuthActivity.class.getSimpleName();
	
	private GreenhouseConnectManager _greenhouseConnectManager;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_greenhouseConnectManager = new GreenhouseConnectManager(getApplicationContext());
	}
	
	@Override
	protected void onStart()
	{
		if (!_greenhouseConnectManager.isConnected())
		{			
			new PreConnectTask(this).execute();
		}
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

		Intent intent = new Intent(this, MainTabWidget.class);

		try 
		{
			String oauthVerifier = uri.getQueryParameter("oauth_verifier");
			_greenhouseConnectManager.updateGreenhouseAccessToken(oauthVerifier);
		}
		catch (Exception e) 
		{
			Log.e(TAG, e.getMessage(), e);
		}
		finally 
		{
			startActivity(intent);
			finish();
		}
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private void displayAuthorization(String authUrl)
	{
		Intent intent = new Intent();
		intent.setClass(this, WebOAuthActivity.class);
		intent.putExtra("authUrl", authUrl);
		startActivity(intent);
		finish();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class PreConnectTask extends ProgressAsyncTask<Void, Void, String> 
	{		
		public PreConnectTask(Context context)
		{
			super(context);
		}
		
		@Override
		protected String doInBackground(Void... params) 
		{			
			return _greenhouseConnectManager.getGreenhouseAuthorizeUrl();
		}
		
		@Override
		protected void onPostExecute(String authUrl)
		{
			super.onPostExecute(authUrl);
			
			displayAuthorization(authUrl);
		}
	}
	
	private class PostConnectTask extends ProgressAsyncTask<String, Void, Void> 
	{
		public PostConnectTask(Context context) 
		{
			super(context);
		}

		@Override
		protected Void doInBackground(String... params) 
		{
			if (params.length <= 0)
			{
				return null;
			}
			
			final String verifier = params[0];
			
			_greenhouseConnectManager.updateGreenhouseAccessToken(verifier);
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void v)
		{
			super.onPostExecute(v);
			
//			showTwitterOptions();
		}
	}
}
