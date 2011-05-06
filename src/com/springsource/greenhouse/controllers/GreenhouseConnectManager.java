package com.springsource.greenhouse.controllers;


/**
 * @author Roy Clarkson
 */
public class GreenhouseConnectManager 
{
	private static final String TAG = GreenhouseConnectManager.class.getSimpleName();
	private static final String GREENHOUSE_PREFERENCES = "GreenhousePreferences";
	private static final String REQUEST_TOKEN_KEY = "request_token";
	private static final String REQUEST_TOKEN_SECRET_KEY = "request_token_secret";
	
//	private Context _context;
//	private MapServiceProviderConnectionFactoryRegistry _connectionFactoryRegistry;
//	private GreenhouseServiceProviderConnectionFactory _connectionFactory;
//	private SQLiteOpenHelper _repositoryHelper;
//	private SqliteServiceProviderConnectionRepository _connectionRepository;
		
		
	//***************************************
    // Constructors
    //***************************************
//	public GreenhouseConnectManager(Context context)
//	{
//		_context = context;
//		_connectionFactoryRegistry = new MapServiceProviderConnectionFactoryRegistry();
//		_connectionFactory = new GreenhouseServiceProviderConnectionFactory(getConsumerKey(), getConsumerKeySecret());
//		_connectionFactoryRegistry.addConnectionFactory(_connectionFactory);
//		_repositoryHelper = new SqliteServiceProviderConnectionRepositoryHelper(_context);
//		_connectionRepository = new SqliteServiceProviderConnectionRepository(getLocalUserId(), 
//				new SqliteServiceProviderConnectionRepositoryHelper(_context), _connectionFactoryRegistry, Encryptors.noOpText());
//	}
	
	
	//***************************************
    // Accessor methods
    //***************************************
//	private Context getContext() 
//	{
//		return _context;
//	}
//	
//	private String getLocalUserId() 
//	{
//		return getContext().getString(R.string.local_user_id);
//	}
//	
//	private String getProviderId() 
//	{
//		return getContext().getString(R.string.greenhouse_provider_id);
//	}
//	
//	private String getBasUrl() 
//	{
//		return getContext().getString(R.string.greenhouse_base_url);
//	}
//
//	private String getConsumerKey() 
//	{
//		return getContext().getString(R.string.greenhouse_consumer_key);
//	}
//
//	private String getConsumerKeySecret() 
//	{
//		return getContext().getString(R.string.greenhouse_consumer_key_secret);
//	}
//
//	private String getOAuthCallbackUrl() 
//	{
//		return getContext().getString(R.string.greenhouse_oauth_callback_url);
//	}

	
	//***************************************
    // Public methods
    //***************************************
//	@SuppressWarnings("unchecked")
//	public GreenhouseApi getGreenhouseApi() 
//	{
//		List<ServiceProviderConnection<?>> connections = _connectionRepository.findConnectionsToProvider(getProviderId());
//		ServiceProviderConnection<GreenhouseApi> greenhouse = (ServiceProviderConnection<GreenhouseApi>) connections.get(0);
//		return greenhouse.getServiceApi();
//	}
//	
//	public boolean isConnected() 
//	{
//		return !_connectionRepository.findConnectionsToProvider(getProviderId()).isEmpty();
//	}
//	
//	public boolean isCallbackUrl(Uri uri) 
//	{
//		return (uri != null && Uri.parse(getOAuthCallbackUrl()).getScheme().equals(uri.getScheme()));
//	}
//	
//	public String getGreenhouseAuthorizeUrl() 
//	{ 
//		OAuth1Operations oauth = _connectionFactory.getOAuthOperations();
//		
//		// Fetch a one time use Request Token from Greenhouse
//		OAuthToken requestToken = oauth.fetchRequestToken(getOAuthCallbackUrl(), null);
//		
//		// save the Request Token to be used later
//		SharedPreferences preferences = _context.getSharedPreferences(GREENHOUSE_PREFERENCES, Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
//		editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
//		editor.commit();
//		
//		// Generate the Greenhouse authorization url to be used in the browser or web view
//		return oauth.buildAuthorizeUrl(requestToken.getValue(), getOAuthCallbackUrl());
//	}
//	
//	public void updateGreenhouseAccessToken(String verifier) 
//	{
//		// Retrieve the Request Token that we saved earlier
//		SharedPreferences preferences = _context.getSharedPreferences(GREENHOUSE_PREFERENCES, Context.MODE_PRIVATE);		
//		String token = preferences.getString(REQUEST_TOKEN_KEY, null);
//		String secret = preferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
//		OAuthToken requestToken = new OAuthToken(token, secret);
//		
//		if (token == null || secret == null) 
//		{
//			return;
//		}
//
//		// Authorize the Request Token
//		AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, verifier);
//		
//		// Exchange the Authorized Request Token for the Access Token
//		OAuthToken accessToken = _connectionFactory.getOAuthOperations().exchangeForAccessToken(authorizedRequestToken, null);
//		
//		// The Request Token is no longer needed, so it can be removed
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.remove(REQUEST_TOKEN_KEY);
//		editor.remove(REQUEST_TOKEN_SECRET_KEY);
//		editor.commit();
//		
//		// Persist the connection and Access Token to the local SQLite 
//		ServiceProviderConnection<GreenhouseApi> connection = _connectionFactory.createConnection(accessToken);
//		
//		try 
//		{
//			_connectionRepository.addConnection(connection);
//		} 
//		catch (DuplicateServiceProviderConnectionException e)
//		{
//			Log.i(TAG, "Connection already exists in repository!");
//		}
//		
//	}
//	
//	public void disconnect()
//	{
//		_connectionRepository.removeConnectionsToProvider(getProviderId());
//	}
	
//	public static String getAuthorizeUrl() 
//	{
//		// Fetch a one time use Request Token from Greenhouse
//		OAuthToken requestToken = getServiceProvider().getOAuthOperations().fetchNewRequestToken(getOAuthCallbackUrl());
//		
//		// save the Request Token to be used later
//		SharedPreferences preferences = getContext().getSharedPreferences(GREENHOUSE_PREFERENCES, Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
//		editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
//		editor.commit();	
//		
//		// Generate the Greenhouse authorization url to be used in the browser or web view
//		return getServiceProvider().getOAuthOperations().buildAuthorizeUrl(requestToken.getValue(), getOAuthCallbackUrl());
//	}
//	
//	public static void updateAccessToken(String verifier) 
//	{
//		// Retrieve the Request Token that we saved earlier
//		SharedPreferences preferences = getContext().getSharedPreferences(GREENHOUSE_PREFERENCES, Context.MODE_PRIVATE);		
//		String token = preferences.getString(REQUEST_TOKEN_KEY, null);
//		String secret = preferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
//		OAuthToken requestToken = new OAuthToken(token, secret);
//		
//		if (token == null || secret == null) 
//		{
//			return;
//		}
//
//		// Authorize the Request Token
//		AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, verifier);
//		
//		// Exchange the Authorized Request Token for the Access Token
//		OAuthToken accessToken = getServiceProvider().getOAuthOperations().exchangeForAccessToken(authorizedRequestToken);
//		
//		// The Request Token is no longer needed, so it can be removed
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.remove(REQUEST_TOKEN_KEY);
//		editor.remove(REQUEST_TOKEN_SECRET_KEY);
//		editor.commit();
//		
//		// Persist the connection and Access Token. Once done, the connection
//		// is stored in the local SQLite database for use next time the user
//		// opens the app.
//		getServiceProvider().connect(getDefaultConnectAccountId(), accessToken);
//	}
//	
//	public static void disconnect()
//	{
//		if (getServiceProvider().isConnected(getDefaultConnectAccountId()))
//		{
//			// get all the Greenhouse connections for our default Spring Social account id
//			List<ServiceProviderConnection<GreenhouseApi>> connections = getServiceProvider().getConnections(getDefaultConnectAccountId());
//			
//			// we are only working with a single Greenhouse connection, so get the first one from the list
//			ServiceProviderConnection<GreenhouseApi> defaultConnection = connections.get(0);
//			
//			// disconnect will remove the record from the local database managed by Spring Social
//			defaultConnection.disconnect();
//		}
//	}
	
		
//	public static OAuthToken getGreenhouseRequestToken(Context context) {
//		return getGreenhouseOauthOperations(context).fetchNewRequestToken(OAUTH_CALLBACK_URL);
//	}
//	
//	public static String getGreenhouseAuthorizeUrl(Context context) {
//		OAuthToken requestToken = getGreenhouseOauthOperations(context).fetchNewRequestToken(OAUTH_CALLBACK_URL);
//		saveRequestToken(context, requestToken);
//		return getGreenhouseOauthOperations(context).buildAuthorizeUrl(requestToken.getValue(), OAUTH_CALLBACK_URL);
//	}
//		
//	public static void updateGreenhouseAccessToken(Context context, String verifier) {
//		OAuthToken requestToken = getSavedRequestToken(context);
//		AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(requestToken, verifier);
//		OAuthToken accessToken = getGreenhouseOauthOperations(context).exchangeForAccessToken(authorizedRequestToken);
//		deleteSavedRequestToken(context);
//		getGreenhouseServiceProvider(context).connect(DEFAULT_CONNECT_ACCOUNT_ID, accessToken);
//	}
	
	
	//***************************************
    // Private methods
    //***************************************
	
//	private static OAuth1Operations getGreenhouseOauthOperations(Context context) {
//		return getGreenhouseServiceProvider(context).getOAuthOperations();
//	}
//	
//	private static void saveRequestToken(Context context, OAuthToken requestToken) {
//		SharedPreferences preferences = context.getSharedPreferences(GREENHOUSE_PREFERENCES, Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.putString(REQUEST_TOKEN_KEY, requestToken.getValue());
//		editor.putString(REQUEST_TOKEN_SECRET_KEY, requestToken.getSecret());
//		editor.commit();		
//	}
//	
//	private static OAuthToken getSavedRequestToken(Context context) {
//		SharedPreferences preferences = context.getSharedPreferences(GREENHOUSE_PREFERENCES, Context.MODE_PRIVATE);		
//		String token = preferences.getString(REQUEST_TOKEN_KEY, null);
//		String secret = preferences.getString(REQUEST_TOKEN_SECRET_KEY, null);
//		return new OAuthToken(token, secret);
//	}
//
//	private static void deleteSavedRequestToken(Context context) {
//		SharedPreferences preferences = context.getSharedPreferences(GREENHOUSE_PREFERENCES, Context.MODE_PRIVATE);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.remove(REQUEST_TOKEN_KEY);
//		editor.remove(REQUEST_TOKEN_SECRET_KEY);
//		editor.commit();
//	}
}
