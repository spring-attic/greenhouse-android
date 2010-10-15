package com.springsource.greenhouse.util;

import org.scribe.builder.api.DefaultApi10a;

public class GreenhouseApi extends DefaultApi10a {
	@Override
	public String getAccessTokenEndpoint() {
		return Prefs.getUrlBase() + "/oauth/access_token";
	}

	@Override
	public String getRequestTokenEndpoint() {
		return Prefs.getUrlBase() + "/oauth/request_token";
	}
}
