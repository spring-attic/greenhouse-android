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
package com.springsource.greenhouse.profile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.social.greenhouse.api.GreenhouseProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.springsource.greenhouse.AbstractGreenhouseActivity;
import com.springsource.greenhouse.MainActivity;
import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
public class ProfileActivity extends AbstractGreenhouseActivity {
	
	protected static final String TAG = ProfileActivity.class.getSimpleName();
	
	private GreenhouseProfile profile;
	
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (profile == null) {
			downloadProfile();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.profile_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
		    case R.id.profile_menu_refresh:
		        downloadProfile();
		        return true;
		    case R.id.profile_menu_sign_out:
		        signOut();
		        return true;
		    default:
		        return super.onOptionsItemSelected(item);
	    }
	}
	
	//***************************************
    // Private methods
    //***************************************
	private void refreshProfile(GreenhouseProfile profile) {
		if (profile == null) {
			return;
		}
		
		this.profile = profile;
		
		final TextView textViewMemberName = (TextView) findViewById(R.id.profile_textview_member_name);		
		textViewMemberName.setText(profile.getDisplayName());
		new DownloadProfileImageTask().execute(profile.getPictureUrl());
	}
	
	private void signOut() {
    	getApplicationContext().getConnectionRepository().removeConnectionsToProvider(getApplicationContext().getConnectionFactory().getProviderId());
    	startActivity(new Intent(this, MainActivity.class));
    	finish();
    } 
    
    private void downloadProfile() {
		new DownloadProfileTask().execute();
	}
    
    
	//***************************************
	// Private classes
	//***************************************
	private class DownloadProfileTask extends AsyncTask<Void, Void, GreenhouseProfile> {
		
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog(); 
		}
		
		@Override
		protected GreenhouseProfile doInBackground(Void... params) {
			try {
				return getApplicationContext().getPrimaryConnection().getApi().userOperations().getUserProfile();
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				exception = e;
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(GreenhouseProfile result) {
			dismissProgressDialog();
			processException(exception);
			refreshProfile(result);
		}
	}
	
	private class DownloadProfileImageTask extends AsyncTask<String, Void, Bitmap> {
		
		@Override
		protected Bitmap doInBackground(String... urls) {
			Bitmap bitmap = null;
			try {
				if (urls.length > 0) {
					URL url = new URL(urls[0]);
					URLConnection conn = url.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					bitmap = BitmapFactory.decodeStream(bis);
					bis.close();
					is.close();
				}
			} catch (IOException e) {
				Log.e(TAG, "Error retrieving profile image", e);
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			final ImageView imageViewPicture = (ImageView) findViewById(R.id.profile_imageview_picture);
			imageViewPicture.setImageBitmap(result);
		}
	}
}
