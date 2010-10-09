package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = new TextView(this);
		textView.setText("This is the Profile tab");
		setContentView(textView);
	}
}
