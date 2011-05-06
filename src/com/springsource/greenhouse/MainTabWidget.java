package com.springsource.greenhouse;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.springsource.greenhouse.R;
import com.springsource.greenhouse.events.EventsActivity;
import com.springsource.greenhouse.profile.ProfileActivity;

public class MainTabWidget extends TabActivity {
	
	//***************************************
    // Activity methods
    //***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Resources res = getResources();
		TabHost tabHost = getTabHost();
		TabHost.TabSpec tabSpec;
		Intent intent;
				
		// add events tab
		intent = new Intent();
		intent.setClass(this, EventsActivity.class);
		
		tabSpec = tabHost.newTabSpec("events");
		tabSpec.setIndicator("Events", res.getDrawable(R.drawable.ic_tab_events));
		tabSpec.setContent(intent);
		tabHost.addTab(tabSpec);
		
		// add profile tab
		intent = new Intent();
		intent.setClass(this, ProfileActivity.class);
		
		tabSpec = tabHost.newTabSpec("profile");
		tabSpec.setIndicator("Profile", res.getDrawable(R.drawable.ic_tab_profile));
		tabSpec.setContent(intent);
		tabHost.addTab(tabSpec);
		
		// add info tab
		intent = new Intent();
		intent.setClass(this, InfoActivity.class);
		
		tabSpec = tabHost.newTabSpec("info");
		tabSpec.setIndicator("Info", res.getDrawable(R.drawable.ic_tab_info));
		tabSpec.setContent(intent);
		tabHost.addTab(tabSpec);
	}
}
