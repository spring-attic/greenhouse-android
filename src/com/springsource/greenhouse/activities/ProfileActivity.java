package com.springsource.greenhouse.activities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.social.greenhouse.GreenhouseOperations;
import org.springframework.social.greenhouse.GreenhouseProfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.util.Prefs;

public class ProfileActivity extends Activity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		final TextView textViewMemberName = (TextView) findViewById(R.id.profile_textview_member_name);
		final ImageView imageViewPicture = (ImageView) findViewById(R.id.profile_imageview_picture);
		
		GreenhouseOperations greenhouse = Prefs.getGreenhouseOperations(getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE));
		
		GreenhouseProfile profile = greenhouse.getUserProfile();		
		textViewMemberName.setText(profile.getDisplayName());		
		imageViewPicture.setImageBitmap(getImageBitmap(profile.getPictureUrl()));
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
	    case R.id.sign_out:
	        signOut();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	//***************************************
    // Private methods
    //***************************************
	private void signOut() {
    	Prefs.disconnect(getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE));
    	Intent intent = new Intent(ProfileActivity.this, FrontDoorActivity.class);
    	startActivity(intent);
    	finish();
    }
	
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
           Log.e("profile-activity", "Error getting bitmap", e);
       }
       return bm;
    } 
}
