package com.springsource.greenhouse.util;

import org.scribe.builder.api.DefaultApi10a;

public class GreenhouseApi extends DefaultApi10a {
	@Override
	public String getAccessTokenEndpoint() {
		return "http://10.0.2.2:8080/greenhouse/oauth/access_token";
	}

	@Override
	public String getRequestTokenEndpoint() {
		return "http://10.0.2.2:8080/greenhouse/oauth/request_token";
	}
}
