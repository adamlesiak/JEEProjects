package com.adamlesiak.visma;

/**
 * 
 * Visma Open Auth Connector - based on OAuth 2.0 protocol and Visma specifications
 * 
 * Base class 
 * Class enable to create main object of Client class and set basic parameters
 * 
 * 
 * @author Adam Lesiak <adamlesiak@adamlesiak.com>
 *
 */

public class Client {
	
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	
	private String clientId = null;
	private String clientSecret = null;
	private String redirectURI = null;	
	private String grantTypeAuhorizationCode = "authorization_code";
	private String scope = "sales";
	private String responseType = "code";
		
			
	/**
	 * Constructor. Creates Open Auth for Visma Client object.
	 * 
	 * @param String clientId  - Client ID
	 * @param String clientSecret - Client secret string. In terminal curl parameter also called "Password"
	 * @param String redirectURI - An url address for redirect after Visma online authentication 
	 * 
	 */
	public Client(String clientId, String clientSecret, String redirectURI) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectURI = redirectURI;
	}
	
	/*
	 * Getters and setters
	 */
			
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getGrantTypeAuhorizationCode() {
		return grantTypeAuhorizationCode;
	}

	public void setGrantTypeAuhorizationCode(String grantTypeAuhorizationCode) {
		this.grantTypeAuhorizationCode = grantTypeAuhorizationCode;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}
	
	
	
	
			
}
