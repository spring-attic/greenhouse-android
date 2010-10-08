package com.springsource.greenhouse;

import android.app.Activity;
import android.os.Bundle;

public class Greenhouse extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}