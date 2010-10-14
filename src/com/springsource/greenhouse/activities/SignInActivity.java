package com.springsource.greenhouse.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.util.Prefs;

public class SignInActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);

		findViewById(R.id.buttonSignin).setOnClickListener(new OnClickListener() {
			public void onClick(final View view) {
				startActivity(new Intent(SignInActivity.this, OAuthActivity.class));
				finish();
			}
		});
	}
}
