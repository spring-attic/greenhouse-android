package com.springsource.greenhouse.activities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.springsource.greenhouse.util.GreenhouseApi;
import com.springsource.greenhouse.util.Prefs;

public class OAuthActivity extends Activity {
	private SharedPreferences _settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getData() == null) {
			try {
				_settings = getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
				ServiceBuilder serviceBuilder = new ServiceBuilder();
				OAuthService oAuthService = serviceBuilder.apiKey(Prefs.getConsumerKey()).apiSecret(Prefs.getConsumerSecret()).provider(GreenhouseApi.class).callback(Prefs.CALLBACK_URI_STRING).build();
				Token requestToken = oAuthService.getRequestToken();

				Prefs.saveRequestInformation(_settings, requestToken.getToken(), requestToken.getSecret());
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2:8080/greenhouse/oauth/confirm_access?oauth_token=" + requestToken.getToken())));
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
