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
package com.springsource.greenhouse;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.greenhouse.api.GreenhouseApi;
import org.springframework.social.greenhouse.connect.GreenhouseConnectionFactory;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * @author Roy Clarkson
 */
public class WebOAuthActivity extends AbstractGreenhouseActivity
{
	protected static final String TAG = WebOAuthActivity.class.getSimpleName();
	
	private static final String REQUEST_TOKEN_KEY = "request_token";
	
	private static final String REQUEST_TOKEN_SECRET_KEY = "request_token_secret";
	
	private WebView _webView;
	
	private ConnectionRepository _connectionRepository;
	
	private GreenhouseConnectionFactory _connectionFactory;
	
	private SharedPreferences _greenhousePreferences;


	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
		
		_webView = new WebView(this);
		setContentView(_webView);
		
//		_webView.getSettings().setJavaScriptEnabled(true);
//		_webView.getSettings().setNavDump(true);
//		_webView.getSettings().setSaveFormData(true);
		
		final Activity activity = this;
		
		_webView.setWebChromeClient(
				new WebChromeClient() 
				{
		            public void onProgressChanged(WebView view, int progress)
		            {
		            	activity.setTitle("Loading...");
		            	activity.setProgress(progress * 100);
		            	
		            	if (progress == 100)
		            	{
		            		activity.setTitle(R.string.app_name);
		            	}
		            }
				}
		);
		
		
		_connectionRepository = getApplicationContext().getConnectionRepository();
		_connectionFactory = getApplicationContext().getConnectionFactory();
		_greenhousePreferences = getSharedPreferences("GreenhouseConnectPreferences", Context.MODE_PRIVATE);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		Log.d(TAG, "onStart()");
		
		Uri uri = getIntent().getData();
		
		if (uri != null)
		{
			Log.d(TAG, "callback url: " + uri.toString());
			String oauthVerifier = uri.getQueryParameter("oauth_verifier");
		
			if (oauthVerifier != null)
			{
				_webView.clearView();
				new GreenhousePostConnectTask().execute(oauthVerifier);
			}
		}
		else
		{
			Log.d(TAG, "pre connect");
			new GreenhousePreConnectTask().execute();
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		Log.d(TAG, "onResume()");
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	private String getOAuthCallbackUrl()
	{
		return getString(R.string.greenhouse_oauth_callback_url);
	}
	
	private void displayGreenhouseAuthorization(OAuthToken requestToken)
	{
		// save for later use
		saveRequestToken(requestToken);
		
		Log.d(TAG, "requestToken value: " + requestToken.getValue());
		Log.d(TAG, "requestToken secret: " + requestToken.getSecret());
		
		// Generate the Greenhouse authorization URL to be used in the browser or web view
		String authUrl = _connectionFactory.getOAuthOperations().buildAuthorizeUrl(requestToken.getValue(), new OAuth1Parameters(getOAuthCallbackUrl()));
		Log.d(TAG, "authorize url: " + authUrl);
		
		// display the Greenhouse authorization screen
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(authUrl));
		startActivity(intent);

//		_webView.loadUrl(authUrl);
	}
	
	private void displayGreenhouseOptions()
	{
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
	    startActivity(intent);
    	finish();
	}
	
	private void saveRequestToken(OAuthToken requestToken)
	{
		SharedPreferences.Editor editor = _greenhousePreferences.edit();
		editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
		editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
		editor.commit();
	}
	
	private OAuthToken retrieveRequestToken()
	{		
		String token = _greenhousePreferences.getString(REQUEST_TOKEN_KEY, null);
		String secret = _greenhousePreferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
		return new OAuthToken(token, secret);
	}
	
	private void deleteRequestToken()
	{
		_greenhousePreferences.edit().clear().commit();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class GreenhousePreConnectTask extends AsyncTask<Void, Void, OAuthToken> 
	{		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Initializing OAuth Connection...");
		}
		
		@Override
		protected OAuthToken doInBackground(Void... params) 
		{			
			// Fetch a one time use Request Token from Greenhouse
			return _connectionFactory.getOAuthOperations().fetchRequestToken(getOAuthCallbackUrl(), null);
		}
		
		@Override
		protected void onPostExecute(OAuthToken requestToken)
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			displayGreenhouseAuthorization(requestToken);
		}
	}
	
	private class GreenhousePostConnectTask extends AsyncTask<String, Void, Void> 
	{		
		@Override
		protected void onPreExecute() 
		{
			// before the network request begins, show a progress indicator
			showProgressDialog("Finalizing OAuth Connection...");
		}
		
		@Override
		protected Void doInBackground(String... params) 
		{
			if (params.length <= 0)
			{
				return null;
			}
			
			final String verifier = params[0];
			
			OAuthToken requestToken = retrieveRequestToken();

			// Authorize the Request Token
			AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, verifier);
			
			// Exchange the Authorized Request Token for the Access Token
			OAuthToken accessToken = _connectionFactory.getOAuthOperations().exchangeForAccessToken(authorizedRequestToken, null);
			
			deleteRequestToken();
			
			// Persist the connection and Access Token to the repository 
			Connection<GreenhouseApi> connection = _connectionFactory.createConnection(accessToken);
			
			try 
			{
				_connectionRepository.addConnection(connection);
				Log.d(TAG, "connection created");
			} 
			catch (DuplicateConnectionException e)
			{
				Log.d(TAG, e.getLocalizedMessage());
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void v)
		{
			// after the network request completes, hide the progress indicator
			dismissProgressDialog();
			
			displayGreenhouseOptions();
		}
	}
}
