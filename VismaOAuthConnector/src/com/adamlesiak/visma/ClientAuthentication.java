package com.adamlesiak.visma;

import java.util.HashMap;
import java.util.Map;

public class ClientAuthentication {

	private Client client;
	
	/**
	 * Constructor 
	 * @param com.adamlesiak.visma.Client client
	 */
	public ClientAuthentication(Client client) {
		this.client = client;
	}
	
	/**
	 * Creates and returns a link for redirect to Visma online application for web authentication 
	 * 
	 * @param authorizationEndpoint
	 * @param redirectURI
	 * @return String url
	 */
	
	public String getAuthenticationURL(String authorizationEndpoint, String redirectURI) {
		
		StringBuffer url = new StringBuffer(authorizationEndpoint);
		
		Map<String, String> URIParameters = new HashMap<String, String>();
		
		URIParameters.put("response_type", client.getResponseType());
		URIParameters.put("client_id", client.getClientId());
		URIParameters.put("redirect_uri", redirectURI);
		URIParameters.put("scope", client.getScope());
		URIParameters.put("state", String.valueOf((int)(Math.random() * 100000)));
		
		url.append("?");
		for (Map.Entry<String, String> URIParametersEntry : URIParameters.entrySet()) {
			String URIParameter = URIParametersEntry.getKey();
			url.append(URIParameter);
			url.append("=");
			String URIParameterValue = URIParametersEntry.getValue();
			url.append(URIParameterValue);
			url.append("&");
		}		
		url.deleteCharAt(url.length() - 1); /* Removes last char: & */
		
		return url.toString();
	}
	
}
