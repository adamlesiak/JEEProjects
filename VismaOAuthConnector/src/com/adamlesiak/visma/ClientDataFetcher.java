package com.adamlesiak.visma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

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
						
		StringBuffer accessTokenBuffer = new StringBuffer();			
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(tokenEndpoint);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("grant_type", client.getGrantTypeAuhorizationCode()));
		nameValuePairs.add(new BasicNameValuePair("code", code));
		nameValuePairs.add(new BasicNameValuePair("client_id", client.getClientId()));
		nameValuePairs.add(new BasicNameValuePair("client_secret", client.getClientSecret()));
		nameValuePairs.add(new BasicNameValuePair("username", client.getClientId()));
		nameValuePairs.add(new BasicNameValuePair("password", client.getClientSecret()));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		HttpResponse response = httpClient.execute(post);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		String line = "";
		while ((line = reader.readLine()) != null) {
			accessTokenBuffer.append(line);
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
