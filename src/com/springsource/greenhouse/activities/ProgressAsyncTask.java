package com.springsource.greenhouse.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class ProgressAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
{
	private Context _context;
	
	private ProgressDialog _progressDialog;
	
	private String _message;
	
	
	//***************************************
    // Constructors methods
    //***************************************
	@SuppressWarnings("unused")
	private ProgressAsyncTask()
	{
		// context is required
	}
	
	public ProgressAsyncTask(Context context)
	{
		this(context, "Loading. Please wait...");
	}
	
	public ProgressAsyncTask(Context context, String message)
	{
		_context = context;
		_message = message;
	}
		
	
	//***************************************
    // AsyncTask methods
    //***************************************
	@Override
	protected void onPreExecute() 
	{
		// before the network request begins, show a progress indicator
		showProgressDialog(_message);
	}

	@Override
	protected void onPostExecute(Result result)
	{
		// after the network request completes, hide the progress indicator
		dismissProgressDialog();
	}
	
	@Override
	protected void onCancelled()
	{
		dismissProgressDialog();
	}
	
	
	//***************************************
    // Protected methods
    //***************************************
	protected void showProgressDialog(String message) 
	{
		_progressDialog = ProgressDialog.show(_context, "",  message, true);
	}
	
	protected void dismissProgressDialog() 
	{
		if (_progressDialog != null) 
		{
			_progressDialog.dismiss();
		}
	}

}
