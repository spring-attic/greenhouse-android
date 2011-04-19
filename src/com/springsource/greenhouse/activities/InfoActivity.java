package com.springsource.greenhouse.activities;

import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.springsource.greenhouse.R;

public class InfoActivity extends AbstractGreenhouseActivity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		final TextView textViewInfoVersion = (TextView) findViewById(R.id.info_textview_version);
	
		try {
			ComponentName componentName = new ComponentName(this, AbstractGreenhouseActivity.class);
			String packageName = componentName.getPackageName();
			PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, 0);
			textViewInfoVersion.setText("Version " + packageInfo.versionName);
		} catch (NameNotFoundException e) { }
		
	}
}
