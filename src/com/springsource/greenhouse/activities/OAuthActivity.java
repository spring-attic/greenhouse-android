package com.springsource.greenhouse.activities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
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

		_settings = getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
		String[] tokenAndSecret = Prefs.getRequestTokenAndSecret(_settings);
		String requestTokenValue = tokenAndSecret[0];
		String requestTokenSecret = tokenAndSecret[1];
		Intent intent = new Intent(this, MainTabWidget.class);

		try {
			Token requestToken = new Token(requestTokenValue, requestTokenSecret);
			String verifier = uri.getQueryParameter("oauth_verifier");

			ServiceBuilder serviceBuilder = new ServiceBuilder();
			OAuthService oAuthService = serviceBuilder.apiKey(Prefs.getConsumerKey()).apiSecret(Prefs.getConsumerSecret()).provider(GreenhouseApi.class).callback(Prefs.CALLBACK_URI_STRING).build();

			Token accessToken = oAuthService.getAccessToken(requestToken, new Verifier(verifier));
			
			Prefs.saveAuthInformation(_settings, accessToken.getToken(), accessToken.getSecret());
			// Clear the request stuff, now that we have the real thing
			Prefs.resetRequestInformation(_settings);
//			intent.putExtra(Prefs.USER_TOKEN, token); // TODO needed?
//			intent.putExtra(Prefs.USER_SECRET, secret);
		}
		catch (Exception e) {
			Log.e("ErrorHandler", e.getMessage(), e);

			Writer result = new StringWriter();
			e.printStackTrace(new PrintWriter(result));
		}
		finally {
			startActivity(intent);
			finish();
		}
	}
}
