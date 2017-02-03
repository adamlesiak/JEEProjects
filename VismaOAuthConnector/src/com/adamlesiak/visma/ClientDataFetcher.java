package com.adamlesiak.visma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

/**
 * 
 * Class for data fetching and sending after authorize
 * 
 * @author Adam Lesiak <adamlesiak@adamlesiak.com>
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
	 * @param Cient client
	 * @param String code - char sequence taken from Visma callback after success user authentication
	 */
	public ClientDataFetcher(Client client, String code) {
		this.client = client;
		this.code = code;
	}
	
	/**
	 * This method gets a token named "access_token" - a unique char sequence for authorize user.
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
	 * @throws AuthenticationException 
	 */	
	public void requestAccessToken(String tokenEndPoint) throws IOException, AuthenticationException {
						
		StringBuffer accessTokenBuffer = new StringBuffer();			
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(tokenEndPoint);
	    
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("grant_type", client.getGrantTypeAuhorizationCode()));
		nameValuePairs.add(new BasicNameValuePair("redirect_uri", client.getRedirectURI()));
		nameValuePairs.add(new BasicNameValuePair("code", code));
		nameValuePairs.add(new BasicNameValuePair("client_id", client.getClientId()));
		nameValuePairs.add(new BasicNameValuePair("client_secret", client.getClientSecret()));
		nameValuePairs.add(new BasicNameValuePair("username", client.getClientId()));
		nameValuePairs.add(new BasicNameValuePair("password", client.getClientSecret()));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		String userAndPassord = new String (org.apache.commons.codec.binary.Base64.encodeBase64((client.getClientId() + ":" + client.getClientSecret()).getBytes()));		
		post.setHeader("Authorization", "Basic " + userAndPassord);
		
		HttpResponse response = httpClient.execute(post);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		String line = "";
		while ((line = reader.readLine()) != null) {
			accessTokenBuffer.append(line);
		}
		
		JSONObject jsonObject = new JSONObject(accessTokenBuffer.toString());
		accessToken = jsonObject.getString(accesTokenParameterName);		
	}
	
	/**
	 * 
	 * Sends a JSON data to Visma app and returns a HttpResponse object with feedback informations
	 * 
	 * From Visma documentation cURL looks like:
	 * curl [LINK] -H "Authorization: Bearer [ACCESS_TOKEN]" -H "Content-Type: application/json; charset=utf-8" -X POST -d '[JSON_DATA]'
	 * 
	 * 
	 * @param url - url to Visma application
	 * @param accessToken - received access token
	 * @param JSON - string JSON data
	 * 
	 * @return HttpResponse
	 * 
	 */
	
	public HttpResponse sendDataJSON(String url, String accesToken, String JSON) throws IOException {			
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
	    		
		StringEntity entity = new StringEntity(JSON, ContentType.APPLICATION_JSON);
		post.setEntity(entity);
		post.setHeader("Authorization", "Bearer " + accesToken);				
		HttpResponse response = httpClient.execute(post);								
		return response;		
	}
	
	/**
	 * 
	 * Fetching from HttpResponse to ResponseLog for more flexible read and managing response data
	 * 
	 * @param response - HTTP response
	 * @return com.adamlesiak.visma.ResponseLog
	 * @throws IOException
	 */
	
	public ResponseLog fetchResponse(HttpResponse response) throws IOException {
		String responseJSON = "";
		String line = "";
		String message = "";
		String additionalMessage = "";
		String createdObjectId = null;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));		
		while ((line = reader.readLine()) != null) {
			responseJSON = line;
		}
						
		try {
			JSONObject jsonObject = new JSONObject(responseJSON);
			createdObjectId = jsonObject.getString("Id");
		} catch (org.json.JSONException e) {
			//:TODO: Exception handling is not necessary right now
		}
		try {
			JSONObject jsonObject = new JSONObject(responseJSON);
			message = jsonObject.getString("Message");			
		} catch (org.json.JSONException e) {
			//:TODO: Exception handling is not necessary right now
		}
		try {
			JSONObject jsonObject = new JSONObject(responseJSON);
			JSONObject modelState = jsonObject.getJSONObject("ModelState");
			Iterator<?> keys = modelState.keys();
			while( keys.hasNext() ) {
			    String key = (String)keys.next();
			    if ( modelState.get(key) instanceof JSONObject ) {
			    	additionalMessage += key + ":" + modelState.get(key).toString();
			    }
			    if ( modelState.get(key) instanceof JSONArray ) {
			    	JSONArray a = (JSONArray) modelState.get(key);
			    	for (int i = 0; i < a.length(); i++) {
			    		additionalMessage += key + ":" + a.getString(i);	
			    	}
			    	
			    }
			}			
		} catch (org.json.JSONException e) {
			//:TODO: Exception handling is not necessary right now
		}
		
		ResponseLog responseObject = new ResponseLog(response.getStatusLine().getStatusCode(), createdObjectId, message, additionalMessage);
		return responseObject;
	}
	
	/**
	 * 
	 * Sends a request o Visma app and returns a JSON data
	 * 
	 * From Visma documentation cURL looks like:
	 * curl [LINK] -H "Authorization: Bearer [ACCESS_TOKEN]" -H "Content-Type: application/json; charset=utf-8"'
	 * 
	 * 
	 * @param url - url to Visma application
	 * @param accessToken - received access token
	 * 
	 * @return HttpResponse
	 * 
	 */
	
	public HttpResponse getResponse(String url, String accesToken) throws IOException {			
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
	    		
		get.setHeader("Authorization", "Bearer " + accesToken);				
		HttpResponse response = httpClient.execute(get);
		System.out.println(response);
		return response;		
	}
	
	/**
	 * 
	 * Fetching from HttpResponse to ResponseLog for more flexible read and managing response data
	 * 
	 * @param response - HTTP response
	 * @return com.adamlesiak.visma.ResponseLog
	 * @throws IOException
	 */
	
	public String getJSON(HttpResponse response) throws IOException {
		String responseJSON = "";
		String line = "";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		while ((line = reader.readLine()) != null) {
			responseJSON += line;
		}				
		return responseJSON;
	}
	
	/**
	 * Getters and setters
	 */
		
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
