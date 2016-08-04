package com.adamlesiak.visma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Class for data fetching and sending after authorize
 * 
 * @author Adam Lesiak <adamlesiak@adamlesiak.com
 *
 */

public class ClientDataFetcher {

	Client client;
	String code = null;
	private String accessToken = null;
	private String accesTokenParameterName = "access_token";
	
	/**
	 * Constructor
	 * 
	 * @param clientAuthentication
	 */
	public ClientDataFetcher(Client client, String code) {
		this.client = client;
		this.code = code;
	}
	
	/**
	 * This method gets a token named "access_token" - a unique char sequence for authorize user.
	 * This method gets a parametel named "data" - it is a char sequence taken from Visma callback after success user authentication
	 * With this token (valid for 60 minutes) client can fetch or send (insert, update) data.
	 * IMPORTANT: It is a Visma specification only.
	 * 
	 * cUrl looks like:
	 * curl [TOKEN_ENDPOINT] -u [CLIENT_ID]:[CLIENT_SECRET] -d code=[CODE] -d grant_type=authorization_code -d redirect_uri=[REDIRECT_URI] -X POST
	 * 
	 * 
	 * @param tokenEndpoint
	 * @return
	 * @throws IOException 
	 */	
	public void requestAccessToken(String tokenEndpoint) throws IOException {
						
		URL url = new URL(tokenEndpoint);		
		Map<String,String> params = new HashMap<String, String>();
		StringBuffer postData = new StringBuffer();
		StringBuffer accessTokenBuffer = new StringBuffer();
		
		params.put("client_id", client.getClientId());
		params.put("client_secret", client.getClientSecret());
		params.put("code", code);
		params.put("grant_type", client.getGrantTypeAuhorizationCode());
		params.put("redirect_uri", client.getRedirectURI());
		
		postData.append('&');
		for (Map.Entry<String, String> param : params.entrySet()) {
		    postData.append(param.getKey());
		    postData.append('=');
		    postData.append(param.getValue());
		}
		byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		connection.setDoOutput(true);
		connection.getOutputStream().write(postDataBytes);
		
		
		Reader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		for (int row; (row = inputReader.read()) >= 0;) {
			accessTokenBuffer.append((char) row);
		}
		
		accessToken = accessTokenBuffer.toString();

	}
		
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccesTokenParameterName() {
		return accesTokenParameterName;
	}

	public void setAccesTokenParameterName(String accesTokenParameterName) {
		this.accesTokenParameterName = accesTokenParameterName;
	}
	
	
	
}
