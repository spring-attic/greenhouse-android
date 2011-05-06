package com.springsource.greenhouse;


public interface GreenhouseActivity
{
	MainApplication getApplicationContext(); 
	
	void showProgressDialog();
	
	void showProgressDialog(String message);
	
	void dismissProgressDialog();
}
