package com.springsource.greenhouse.util;

import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {	
	public static final String REQUEST_TOKEN = "request_token";
	public static final String REQUEST_SECRET = "request_secret";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String ACCESS_TOKEN_SECRET = "access_token_secret";
	public static final String PREFS = "GreenhousePreferences";
	
	public static final String CALLBACK_URI_STRING = "x-com-springsource-greenhouse://oauth-response";

	private static final String GREENHOUSE_AUTHORIZATION_URL = "http://10.0.2.2:8080/greenhouse/oauth/confirm_access";
	private static final String GREENHOUSE_API_KEY = "a08318eb478a1ee31f69a55276f3af64";
	private static final String GREENHOUSE_API_SECRET = "80e7f8f7ba724aae9103f297e5fb9bdf";

	public static void saveRequestInformation(final SharedPreferences settings, final String token, final String secret) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(REQUEST_TOKEN, token);
		editor.putString(REQUEST_SECRET, secret);
		editor.commit();
	}
	
	public static String getRequestTokenUrl() {
		return getUrlBase() + "/oauth/request_token";
	}

	public static String getAccessTokenUrl() {
		return getUrlBase() + "/oauth/access_token";
	}

	public static String getAuthorizeUrl() {
		return getUrlBase() + "/oauth/confirm_access";
	}

	private static String getUrlBase() {
		return "http://10.0.2.2:8080/greenhouse";
	}
	
	public static String[] getRequestTokenAndSecret(final SharedPreferences settings) {
		 String token = settings.getString(Prefs.REQUEST_TOKEN, null);
		 String secret = settings.getString(Prefs.REQUEST_SECRET, null);
		 return new String[] { token, secret };
	}
	
	public static void saveAuthInformation(final SharedPreferences settings, final String token, final String secret) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(ACCESS_TOKEN, token);
		editor.putString(ACCESS_TOKEN_SECRET, secret);
		editor.commit();
	}
	
	public static String[] getAccessTokenAndSecret(final SharedPreferences settings) {
		String token = null;
		String tokenSecret = null;
		if (settings.contains(ACCESS_TOKEN) && settings.contains(ACCESS_TOKEN_SECRET)) {
			token = settings.getString(ACCESS_TOKEN, null);
			tokenSecret = settings.getString(ACCESS_TOKEN_SECRET, null);
		}
		return new String[] { token, tokenSecret };
	}
	
	public static void resetRequestInformation(final SharedPreferences settings) {
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(REQUEST_TOKEN);
		editor.remove(REQUEST_SECRET);
		editor.commit();
	}
	
	public static boolean isLoggedIn(final SharedPreferences settings) {
		String[] tokenAndSecret = getAccessTokenAndSecret(settings);
		return tokenAndSecret[0] != null && tokenAndSecret[1] != null;
	}
	
	public static Uri getCallbackUri() {
		return Uri.parse(CALLBACK_URI_STRING);
	}
	
	public static Uri getAuthorizationUrl(String requestToken) {
		return Uri.parse(GREENHOUSE_AUTHORIZATION_URL + "?oauth_token=" + requestToken);
	}
	
	public static String getConsumerKey() {
		return GREENHOUSE_API_KEY;
	}
	
	public static String getConsumerSecret() {
		return GREENHOUSE_API_SECRET;
	}
}
