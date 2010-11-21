package com.springsource.greenhouse.activities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.springsource.greenhouse.util.Prefs;

public class OAuthActivity extends Activity {
	
	private static final String TAG = "OAuthActivity";
	private SharedPreferences _settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getData() == null) {
			try {
				_settings = getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
				
				// scribe
//				ServiceBuilder serviceBuilder = new ServiceBuilder();
//				OAuthService oAuthService = serviceBuilder.apiKey(Prefs.getConsumerKey()).apiSecret(Prefs.getConsumerSecret()).provider(GreenhouseApi.class).callback(Prefs.CALLBACK_URI_STRING).build();
//				Token requestToken = oAuthService.getRequestToken();
//				String requestTokenValue = requestToken.getToken();
//				String requestTokenSecret = requestToken.getSecret();
//				Uri uri = Uri.parse(Prefs.getUrlBase() + "/oauth/confirm_access?oauth_token=" + requestToken.getToken());
				
				// signpost
				OAuthConsumer oauthConsumer = new CommonsHttpOAuthConsumer(Prefs.getConsumerKey(), Prefs.getConsumerSecret());
				OAuthProvider oauthProvider = new CommonsHttpOAuthProvider(Prefs.getRequestTokenUrl(), Prefs.getAccessTokenUrl(), Prefs.getAuthorizeUrl());
				String authUrl = oauthProvider.retrieveRequestToken(oauthConsumer, Prefs.CALLBACK_URI_STRING);
				String requestTokenValue = oauthConsumer.getToken();
				String requestTokenSecret = oauthConsumer.getTokenSecret();
				Uri uri = Uri.parse(authUrl);
				
				Log.d(TAG, requestTokenValue);
				Log.d(TAG, requestTokenSecret);
				Prefs.saveRequestInformation(_settings, requestTokenValue, requestTokenSecret);
				
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				finish();
			} catch (Exception e) {
				Log.e("ErrorHandler", e.getMessage(), e);
				Writer result = new StringWriter();
				e.printStackTrace(new PrintWriter(result));
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Uri uri = getIntent().getData();
		if (uri == null || !Prefs.getCallbackUri().getScheme().equals(uri.getScheme())) {
			return;
		}
		
		Log.d(TAG, uri.toString());

		_settings = getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
		String[] tokenAndSecret = Prefs.getRequestTokenAndSecret(_settings);
		String requestTokenValue = tokenAndSecret[0];
		String requestTokenSecret = tokenAndSecret[1];
		Intent intent = new Intent(this, MainTabWidget.class);

		try {
			String verifierValue = uri.getQueryParameter("oauth_verifier");

			// scribe
//			Token requestToken = new Token(requestTokenValue, requestTokenSecret);
//			ServiceBuilder serviceBuilder = new ServiceBuilder();
//			OAuthService oAuthService = serviceBuilder.apiKey(Prefs.getConsumerKey()).apiSecret(Prefs.getConsumerSecret()).provider(GreenhouseApi.class).callback(Prefs.CALLBACK_URI_STRING).build();
//			Verifier verifier = new Verifier(verifierValue);
//			Token accessToken = oAuthService.getAccessToken(requestToken, verifier);
//			String accessTokenValue = accessToken.getToken();
//			String accessTokenSecret = accessToken.getSecret();
			
			// signpost
			OAuthConsumer oauthConsumer = new CommonsHttpOAuthConsumer(Prefs.getConsumerKey(), Prefs.getConsumerSecret());
			oauthConsumer.setTokenWithSecret(requestTokenValue, requestTokenSecret);
			OAuthProvider oauthProvider = new CommonsHttpOAuthProvider(Prefs.getRequestTokenUrl(), Prefs.getAccessTokenUrl(), Prefs.getAuthorizeUrl());
			oauthProvider.retrieveAccessToken(oauthConsumer, verifierValue);
			String accessTokenValue = oauthConsumer.getToken();
			String accessTokenSecret = oauthConsumer.getTokenSecret();

			Log.d(TAG, accessTokenValue);
			Log.d(TAG, accessTokenSecret);
			Prefs.saveAuthInformation(_settings, accessTokenValue, accessTokenSecret);
			
			// Clear the request stuff, now that we have the real thing
			Prefs.resetRequestInformation(_settings);
		}
		catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			Writer result = new StringWriter();
			e.printStackTrace(new PrintWriter(result));
		}
		finally {
			startActivity(intent);
			finish();
		}
	}
}
