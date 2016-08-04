package com.adamlesiak.visma;


public class Client {
	
	private String clientId = null;
	private String clientSecret = null;
	private String grantTypeAuhorizationCode = "authorization_code";
	private String accessToken = null;
	private String accesTokenParameterName = "access_token";
	private String scope = "sales";
	private String responseType = "code";
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

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
			
}
