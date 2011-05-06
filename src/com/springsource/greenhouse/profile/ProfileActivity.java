package com.springsource.greenhouse.profile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.social.greenhouse.api.GreenhouseProfile;

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
import com.springsource.greenhouse.controllers.NavigationManager;
import com.springsource.greenhouse.controllers.ProfileController;

public class ProfileActivity extends AbstractGreenhouseActivity {
	
	protected static final String TAG = ProfileActivity.class.getSimpleName();
	
	
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
		downloadProfile();
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
		
		final TextView textViewMemberName = (TextView) findViewById(R.id.profile_textview_member_name);
		final ImageView imageViewPicture = (ImageView) findViewById(R.id.profile_imageview_picture);
		
		textViewMemberName.setText(profile.getScreenName());		
		imageViewPicture.setImageBitmap(getImageBitmap(profile.getProfileImageUrl()));
	}
	
	private void signOut() {
//    	Prefs.disconnect(getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE));
    	NavigationManager.startActivity(this, MainActivity.class);
    	finish();
    }
	
	// TODO: convert this to apache http client
    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
       } catch (IOException e) {
           Log.e(TAG, "Error getting bitmap", e);
       }
       return bm;
    } 
    
    private void downloadProfile() {
		new DownloadProfileTask().execute();
	}
    
    
  //***************************************
    // Private classes
    //***************************************
	private class DownloadProfileTask extends AsyncTask<Void, Void, GreenhouseProfile> {
		private Exception mException;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog(); 
		}
		
		@Override
		protected GreenhouseProfile doInBackground(Void... params) {
			try {
				return new ProfileController(getApplicationContext()).getProfile();
			} catch(Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				mException = e;
			} 
			
			return null;
		}
		
		@Override
		protected void onPostExecute(GreenhouseProfile result) {
			dismissProgressDialog();
			processException(mException);
			refreshProfile(result);
		}
	}
}
