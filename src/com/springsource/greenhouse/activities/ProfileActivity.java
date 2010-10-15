package com.springsource.greenhouse.activities;

import org.springframework.social.greenhouse.GreenhouseProfile;
import org.springframework.social.greenhouse.GreenhouseTemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.R.drawable;
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
		
		final SharedPreferences settings = getSharedPreferences(Prefs.PREFS, Context.MODE_PRIVATE);
		String[] tokenAndSecret = Prefs.getAccessTokenAndSecret(settings);
		
		
		GreenhouseTemplate greenhouse = new GreenhouseTemplate(
				Prefs.getConsumerKey(), Prefs.getConsumerSecret(), tokenAndSecret[0], tokenAndSecret[1], "http://10.0.2.2:8080/greenhouse");
		GreenhouseProfile profile = greenhouse.getUserProfile();
		
		textViewMemberName.setText(profile.getDisplayName());
		
		imageViewPicture.setImageResource(drawable.profile);
		
		buttonSignOut.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	Prefs.disconnect(settings);
		    	Intent intent = new Intent(ProfileActivity.this, FrontDoorActivity.class);
		    	startActivity(intent);
		    	finish();
		    }
		});
	}
}
