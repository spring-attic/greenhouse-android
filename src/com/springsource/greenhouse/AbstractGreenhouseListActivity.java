package com.springsource.greenhouse;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.view.Gravity;
import android.widget.Toast;

public abstract class AbstractGreenhouseListActivity extends ListActivity implements GreenhouseActivity 
{	
	protected static final String TAG = AbstractGreenhouseListActivity.class.getSimpleName();
	
	private ProgressDialog _progressDialog;
	
	
	//***************************************
    // GreenhouseActivity methods
    //***************************************
	public MainApplication getApplicationContext()
	{
		return (MainApplication) super.getApplicationContext();
	}
	
	public void showProgressDialog() 
	{
		showProgressDialog("Loading. Please wait...");
	}
	
	public void showProgressDialog(String message) 
	{
		if (_progressDialog == null)
		{
			_progressDialog = new ProgressDialog(this);
			_progressDialog.setIndeterminate(true);
		}
		
		_progressDialog.setMessage(message);
		_progressDialog.show();
	}
	
	public void dismissProgressDialog() 
	{
		if (_progressDialog != null) 
		{
			_progressDialog.dismiss();
		}
	}

	
	//***************************************
    // Protected methods
    //***************************************	
	protected void processException(Exception e) 
	{
		if (e != null) 
		{
			if (e instanceof ResourceAccessException) 
			{
				displayNetworkError();
			}
			else if (e instanceof HttpClientErrorException) 
			{
				HttpClientErrorException httpError = (HttpClientErrorException) e;
				if (httpError.getStatusCode() ==  HttpStatus.UNAUTHORIZED) 
				{
					displayAuthorizationError();
				}
			}
		}
	}
	
	protected void displayNetworkError() 
	{
		Toast toast = Toast.makeText(this, "A problem occurred with the network connection while attempting to communicate with Greenhouse.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	protected void displayAuthorizationError() 
	{
		Toast toast = Toast.makeText(this, "You are not authorized to connect to Greenhouse. Please sign out and reauthorize the app.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
