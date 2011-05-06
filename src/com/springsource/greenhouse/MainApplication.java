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

import org.springframework.security.crypto.encrypt.AndroidEncryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.sqlite.SQLiteConnectionRepository;
import org.springframework.social.connect.sqlite.support.SQLiteConnectionRepositoryHelper;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.greenhouse.api.GreenhouseApi;
import org.springframework.social.greenhouse.connect.GreenhouseConnectionFactory;

import android.app.Application;

/**
 * @author Roy Clarkson
 */
public class MainApplication extends Application 
{
	private static final String GREENHOUSE_CONSUMER_TOKEN = "e9fbccdae98d5696";
	
	private static final String GREENHOUSE_CONSUMER_TOKEN_SECRET = "9fa283e1eca2d4e8";
	
	private GreenhouseConnectionFactory _connectionFactory;
	
	private ConnectionRepository _connectionRepository;
	
	
	//***************************************
    // Application methods
    //***************************************
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		_connectionFactory = new GreenhouseConnectionFactory(GREENHOUSE_CONSUMER_TOKEN, GREENHOUSE_CONSUMER_TOKEN_SECRET);
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(_connectionFactory);
		_connectionRepository = new SQLiteConnectionRepository("1", new SQLiteConnectionRepositoryHelper(this), registry, AndroidEncryptors.text("password", "5c0744940b5c369b")); 
	}
	
	
	//***************************************
    // Private methods
    //***************************************
	
	
	//***************************************
    // Public methods
    //***************************************
	public ConnectionRepository getConnectionRepository()
	{
		return _connectionRepository;
	}
	
	public GreenhouseConnectionFactory getConnectionFactory()
	{
		return _connectionFactory;
	}
	
	public Connection<GreenhouseApi> getPrimaryConnection()
	{
		return getConnectionRepository().findPrimaryConnectionToApi(GreenhouseApi.class);
	}
	
	public GreenhouseApi getGreenhouseApi()
	{
		Connection<GreenhouseApi> connection = getPrimaryConnection();
		if (connection != null)
		{
			return connection.getApi();
		}

		return null;
	}
}
