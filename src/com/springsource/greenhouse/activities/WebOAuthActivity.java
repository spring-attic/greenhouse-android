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
package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * @author Roy Clarkson
 */
public class WebOAuthActivity extends Activity
{
	protected static final String TAG = WebOAuthActivity.class.getSimpleName();
	
	private WebView _webView;
	
		
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		_webView = new WebView(this);
		setContentView(_webView);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		if (getIntent().hasExtra("authUrl"))
		{
			String authUrl = getIntent().getExtras().getString("authUrl");
			
			if (authUrl != null)
			{  
				_webView.loadUrl(authUrl);
			}
		}
	}
}
