/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse;

import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

import com.springsource.greenhouse.R;

/**
 * @author Roy Clarkson
 */
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
