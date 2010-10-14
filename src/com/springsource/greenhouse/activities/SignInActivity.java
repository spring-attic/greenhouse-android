package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.springsource.greenhouse.R;

public class SignInActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);

		findViewById(R.id.buttonSignin).setOnClickListener(new OnClickListener() {
			public void onClick(final View view) {
				// uncomment the following line (and comment the line after it) to enable the OAuth stuff
//				startActivity(new Intent(SignInActivity.this, OAuthActivity.class));
				startActivity(new Intent(SignInActivity.this, MainTabWidget.class));
				finish();
			}
		});
	}
}
