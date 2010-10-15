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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		final Button buttonSignOut = (Button) findViewById(R.id.profile_button_signout);
		
		GreenhouseOperations greenhouse = Prefs.getGreenhouseOperations(getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE));
		
		GreenhouseProfile profile = greenhouse.getUserProfile();		
		textViewMemberName.setText(profile.getDisplayName());		
		imageViewPicture.setImageBitmap(getImageBitmap(profile.getPictureUrl()));
		
		buttonSignOut.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	Prefs.disconnect(getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE));
		    	Intent intent = new Intent(ProfileActivity.this, FrontDoorActivity.class);
		    	startActivity(intent);
		    	finish();
		    }
		});
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
