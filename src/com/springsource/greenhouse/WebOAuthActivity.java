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

import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.greenhouse.api.Greenhouse;
import org.springframework.social.greenhouse.connect.GreenhouseConnectionFactory;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Roy Clarkson
 */
public class WebOAuthActivity extends AbstractGreenhouseActivity {
	
	protected static final String TAG = WebOAuthActivity.class.getSimpleName();
	
	private static final String REQUEST_TOKEN_KEY = "request_token";
	
	private static final String REQUEST_TOKEN_SECRET_KEY = "request_token_secret";
	
	private WebView webView;
	
	private ConnectionRepository connectionRepository;
	
	private GreenhouseConnectionFactory connectionFactory;
	
	private SharedPreferences greenhousePreferences;


	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
		
		webView = new WebView(this);		
		setContentView(webView);
		
		webView.setWebViewClient(new OAuthWebViewClient());
		webView.getSettings().setSaveFormData(false);
		webView.getSettings().setSavePassword(false);
		
		final Activity activity = this;
		
		webView.setWebChromeClient(
				new WebChromeClient() {
		            public void onProgressChanged(WebView view, int progress) {
		            	activity.setTitle("Loading...");
		            	activity.setProgress(progress * 100);
		            	
		            	if (progress == 100) {
		            		activity.setTitle(R.string.app_name);
		            	}
		            }
				}
		);
		
		
		connectionRepository = getApplicationContext().getConnectionRepository();
		connectionFactory = getApplicationContext().getConnectionFactory();
		greenhousePreferences = getSharedPreferences("GreenhouseConnectPreferences", Context.MODE_PRIVATE);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		new GreenhousePreConnectTask().execute();
	}
		
	
	//***************************************
    // Private methods
    //***************************************
	private String getOAuthCallbackUrl() {
		return getString(R.string.oauth_callback_url);
	}
	
	private void displayGreenhouseAuthorization(OAuthToken requestToken) {
		
		if (requestToken == null) {
			return;
		}
				
		// save for later use
		saveRequestToken(requestToken);
				
		// Generate the Greenhouse authorization URL to be used in the browser or web view
		String authUrl = connectionFactory.getOAuthOperations().buildAuthorizeUrl(requestToken.getValue(), OAuth1Parameters.NONE);
		
		// display the Greenhouse authorization screen
		webView.loadUrl(authUrl);
	}
	
	private void displayGreenhouseOptions() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
	    startActivity(intent);
    	finish();
	}
	
	private void saveRequestToken(OAuthToken requestToken) {
		SharedPreferences.Editor editor = greenhousePreferences.edit();
		editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
		editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
		editor.commit();
	}
	
	private OAuthToken retrieveRequestToken() {		
		String token = greenhousePreferences.getString(REQUEST_TOKEN_KEY, null);
		String secret = greenhousePreferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
		return new OAuthToken(token, secret);
	}
	
	private void deleteRequestToken() {
		greenhousePreferences.edit().clear().commit();
	}
	
	private void displayAppAuthorizationError(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
		     	signOut();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	//***************************************
    // Private classes
    //***************************************
	private class GreenhousePreConnectTask extends AsyncTask<Void, Void, OAuthToken> {
		
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog("Initializing OAuth Connection...");
		}
		
		@Override
		protected OAuthToken doInBackground(Void... params) {
			try {
				// Fetch a one time use Request Token from Greenhouse
				return connectionFactory.getOAuthOperations().fetchRequestToken(getOAuthCallbackUrl(), null);
			} catch(Exception e) {
				this.exception = e;
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(OAuthToken requestToken) {
			dismissProgressDialog();
			
			if (exception != null && exception instanceof HttpClientErrorException) {
				if (((HttpClientErrorException)exception).getStatusCode() == HttpStatus.UNAUTHORIZED) {
					displayAppAuthorizationError("This application is not authorized to connect to Greenhouse");
				}
			} else {
				displayGreenhouseAuthorization(requestToken);
			}
		}
	}
	
	private class GreenhousePostConnectTask extends AsyncTask<String, Void, Void> {
		
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog("Finalizing OAuth Connection...");
		}
		
		@Override
		protected Void doInBackground(String... params) {
			if (params.length <= 0) {
				return null;
			}
			
			final String verifier = params[0];
			OAuthToken requestToken = retrieveRequestToken();

			// Authorize the Request Token
			AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, verifier);
			
			OAuthToken accessToken = null;
			try {
				// Exchange the Authorized Request Token for the Access Token
				accessToken = connectionFactory.getOAuthOperations().exchangeForAccessToken(authorizedRequestToken, null);
			} catch(Exception e) {
				exception = e;
				return null;
			}
			
			deleteRequestToken();
			
			// Persist the connection and Access Token to the repository 
			Connection<Greenhouse> connection = connectionFactory.createConnection(accessToken);
			
			try {
				connectionRepository.addConnection(connection);
			} catch (DuplicateConnectionException e) {
				Log.i(TAG, "attempting to add duplicate connection", e);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void v) {
			dismissProgressDialog();
			
			if (exception != null && exception instanceof HttpServerErrorException) {
				if (((HttpServerErrorException)exception).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
					displayAppAuthorizationError("You are already connected with another Android device. Please remove the connection at Greenhouse and try again.");
				}
			} else {
				displayGreenhouseOptions();
			}
		}
	}
	
	private class OAuthWebViewClient extends WebViewClient {
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Uri uri  = Uri.parse(url);

			if (uri.getScheme().equals("x-com-springsource-greenhouse") && uri.getHost().equals("oauth-response")) {
				String oauthVerifier = uri.getQueryParameter("oauth_verifier");
				
				if (oauthVerifier != null) {
					Log.d(TAG, "oauth_verifier: " + oauthVerifier);
					new GreenhousePostConnectTask().execute(oauthVerifier);
					return true;
				}
			}
			
			return false;
		}
	}
}
