package com.springsource.greenhouse.activities;


public interface GreenhouseActivity 
{	
	void showProgressDialog();
	
	void showProgressDialog(String message);
	
	void dismissProgressDialog();
}
