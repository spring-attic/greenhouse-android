/*
 * Copyright 2012 the original author or authors.
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
package com.springsource.greenhouse.authorization;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.greenhouse.api.Greenhouse;
import org.springframework.social.greenhouse.connect.GreenhouseConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.MainActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class SignInActivity extends AbstractGreenhouseActivity {

	private static final String TAG = SignInActivity.class.getSimpleName();

	//***************************************
	// Activity methods
	//***************************************
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);

		findViewById(R.id.cancel_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View view) {
				finish();
			}
		});

		findViewById(R.id.sign_in_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View view) {
				if (validateFormData()) {
					new SignInTask().execute();
				} else {
					displayAppAuthorizationError("Your email or password was entered incorrectly.");
				}
			}
		});
	}

	// ***************************************
	// Private methods
	// ***************************************
	private boolean validateFormData() {
		EditText editText = (EditText) findViewById(R.id.username);
		String username = editText.getText().toString().trim();
		editText = (EditText) findViewById(R.id.password);
		String password = editText.getText().toString().trim();
		if (username.length() > 0 && password.length() > 0) {
			return true;
		}
		return false;
	}
	
	private void displayAppAuthorizationError(String message) {
		new AlertDialog.Builder(this).setMessage(message).setCancelable(false)
				.setPositiveButton("OK", null).create().show();
	}

	private void displayGreenhouseOptions() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		setResult(RESULT_OK);
		finish();
	}

	private AccessGrant extractAccessGrant(Map<String, Object> result) {
		return new AccessGrant((String) result.get("access_token"), (String) result.get("scope"),
				(String) result.get("refresh_token"), getIntegerValue(result, "expires_in"));
	}

	// Retrieves object from map into an Integer, regardless of the object's actual type. Allows for flexibility in object type (eg, "3600" vs 3600).
	private Integer getIntegerValue(Map<String, Object> map, String key) {
		try {
			return Integer.valueOf(String.valueOf(map.get(key))); // normalize to String before creating integer value;			
		} catch (NumberFormatException e) {
			return null;
		}
	}

	//***************************************
	// Private classes
	//***************************************
	private class SignInTask extends AsyncTask<Void, Void, Void> {
		private MultiValueMap<String, String> formData;

		private Exception exception;

		@Override
		protected void onPreExecute() {
			showProgressDialog("Signing in...");

			EditText editText = (EditText) findViewById(R.id.username);
			String username = editText.getText().toString().trim();

			editText = (EditText) findViewById(R.id.password);
			String password = editText.getText().toString().trim();

			formData = new LinkedMultiValueMap<String, String>();
			formData.add("grant_type", "password");
			formData.add("username", username);
			formData.add("password", password);
			formData.add("client_id", getApplicationContext().getClientId());
			formData.add("client_secret", getApplicationContext().getClientSecret());
			formData.add("scope", "read,write");
			Log.d(TAG, formData.toString());
		}

		@Override
		@SuppressWarnings("unchecked")
		protected Void doInBackground(Void... params) {
			try {
				final String url = getApplicationContext().getApiUrlBase() + "oauth/token";
				Log.d(TAG, url);
				HttpHeaders requestHeaders = new HttpHeaders();
				requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(formData, requestHeaders);
				RestTemplate restTemplate = new RestTemplate(true);
				Map<String, Object> responseBody = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class).getBody();
				Log.d(TAG, responseBody.toString());

				// Persist the connection and AccessGrant to the repository
				AccessGrant accessGrant = extractAccessGrant(responseBody);
				GreenhouseConnectionFactory connectionFactory = getApplicationContext().getConnectionFactory();
				ConnectionRepository connectionRepository = getApplicationContext().getConnectionRepository();
				Connection<Greenhouse> connection = connectionFactory.createConnection(accessGrant);
				connectionRepository.addConnection(connection);
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				this.exception = e;
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			dismissProgressDialog();
			if (exception != null) {
				String message;
				if (exception instanceof HttpClientErrorException
						&& (((HttpClientErrorException) exception).getStatusCode() == HttpStatus.BAD_REQUEST)
						|| ((HttpClientErrorException) exception).getStatusCode() == HttpStatus.UNAUTHORIZED) {
					message = "Your email or password was entered incorrectly.";
				} else if (exception instanceof DuplicateConnectionException) {
					message = "The connection already exists.";
				} else {
					Log.e(TAG, exception.getLocalizedMessage(), exception);
					message = "A problem occurred with the network connection. Please try again in a few minutes.";
				}
				displayAppAuthorizationError(message);
			} else {
				displayGreenhouseOptions();
			}
		}
	}

}
