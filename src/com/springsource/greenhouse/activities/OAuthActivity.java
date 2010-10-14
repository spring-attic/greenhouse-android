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
	private OAuthConsumer _consumer;
	private OAuthProvider _provider;
	private SharedPreferences _settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_consumer = new CommonsHttpOAuthConsumer(Prefs.getConsumerKey(), Prefs.getConsumerSecret());
		_provider = new CommonsHttpOAuthProvider(Prefs.getRequestTokenUrl(),
				Prefs.getAccessTokenUrl(), Prefs.getAuthorizeUrl());
		_provider.setOAuth10a(true);

		_settings = getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);

		if (getIntent().getData() == null) {
			try {
				String authUrl = _provider.retrieveRequestToken(_consumer, "http://www.habuma.com/gh");
				Prefs.saveRequestInformation(_settings, _consumer.getToken(), _consumer.getTokenSecret());
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
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
		
		// TODO: 
		//   1. Ensure that this is the right URL
		//   2. Get verifier from parameters
		//   3. Retrieve access token
		//   4. Stuff access token away for future use
	}
}
