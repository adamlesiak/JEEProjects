package com.adamlesiak.visma;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {
	
	private String clientId = null;
	private String clientSecret = null;
	private String grantTypeAuhorizationCode = "authorization_code";
	private String accesToken = null;
	private String accesTokenParameterName = "access_token";
	private String scope = "sales";
	private String redirectUri = null;
	
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	
			
	/**
	 * Constructor. Creates Open Auth for Visma Client object.
	 * 
	 * @param String clientId  - Client ID
	 * @param String clientSecret - Client secret string. In terminal curl parameter also called "Password" 
	 * 
	 */
	public Client(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	
	public String getAuthenticationURL(String authorizationEndpoint, String redirectURI) {
		
		StringBuffer url = new StringBuffer(authorizationEndpoint);
		
		Map<String, String> URIParameters = new HashMap<String, String>();
		URIParameters.put("response_type", "code");
		URIParameters.put("client_id", clientId);
		URIParameters.put("redirect_uri", redirectURI);
		URIParameters.put("scope", scope);
		URIParameters.put("state", String.valueOf(Math.random() * 100000));
		
		url.append("?");
		for (Map.Entry<String, String> URIParametersEntry : URIParameters.entrySet()) {
			String URIParameter = URIParametersEntry.getKey();
			url.append("=");
			String URIParameterValue = URIParametersEntry.getValue();
			url.append("&");
		}
		url.deleteCharAt(url.length()); /* Removes last char: & */
		
		return url;
	}
	

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

	public String getAccesToken() {
		return accesToken;
	}

	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}

	public String getAccesTokenParameterName() {
		return accesTokenParameterName;
	}

	public void setAccesTokenParameterName(String accesTokenParameterName) {
		this.accesTokenParameterName = accesTokenParameterName;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	
	
}
