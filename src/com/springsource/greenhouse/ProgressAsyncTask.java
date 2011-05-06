/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springsource.greenhouse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * @author Roy Clarkson
 */
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
