package com.adamlesiak.visma;

public class ClientDataFetcher {

	ClientAuthentication clientAuthentication;
	
	/**
	 * Constructor
	 * 
	 * @param clientAuthentication
	 */
	public ClientDataFetcher(ClientAuthentication clientAuthentication, String code) {
		this.clientAuthentication = clientAuthentication;
	}
	
	/**
	 * This method gets a token named "access_token" - a unique char sequence for authorize user.
	 * This method gets a parametel named "data" - it is a char sequence takef from Visma callback after success user authentication
	 * With this token (valid for 60 minutes) client can fetch or send (insert, update) data.
	 * IMPORTANT: It is a Visma specification only.
	 * 
	 * Url looks like:
	 * curl [TOKEN_ENDPOINT] -u [CLIENT_ID]:[CLIENT_SECRET] -d code=[CODE] -d grant_type=authorization_code -d redirect_uri=[REDIRECT_URI] -X POST
	 * 
	 * 
	 * @param tokenEndpoint
	 * @return
	 */	
	public String getAccessToken(String tokenEndpoint) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(tokenEndpoint);
		
		return stringBuffer.toString();
	}
	
}
