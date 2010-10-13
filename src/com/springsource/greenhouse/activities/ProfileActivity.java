package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.R.drawable;

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
		
		textViewMemberName.setText("John Smith");
		
		imageViewPicture.setImageResource(drawable.profile);
		
		buttonSignOut.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        Toast.makeText(ProfileActivity.this, "If I was working, I would sign out now.", Toast.LENGTH_SHORT).show();
		    }
		});
	}
}
