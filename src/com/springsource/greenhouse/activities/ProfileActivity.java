package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		
		textViewMemberName.setText("John Smithy");
		
		imageViewPicture.setImageResource(drawable.profile);		
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
		Toast.makeText(ProfileActivity.this, "If I was working, I would sign out now.", Toast.LENGTH_SHORT).show();
	}
}
