package com.adamlesiak.visma;

public class ResponseObject {

	/**
	 * Status Code: 201 - Successfully created and object in Visma database
	 */
	public static final int HTTP_STATUS_CODE_CREATED = 201;
	
	/**
	 * Status Code: 401 - Unauthorized - error in authorization. Client ID or Client Secret invalid
	 */
	public static final int HTTP_STATUS_CODE_UNAUTHORIZED = 401;
	
	/**
	 * Status Code: 409 - Conflict - object with specified field (ID, Number depends on Visma object) existing
	 */
	public static final int HTTP_STATUS_CODE_CONFLICT = 409;
	
	
	private int statusCode;
	private String returnedId;
	
	/**
	 * 
	 * @param statusCode - HTTP status code
	 * @param returnedId - returned ID from Visma after successful insertion of object
	 */	
	public ResponseObject(int statusCode, String returnedId) {
		this.statusCode = statusCode;
		this.returnedId = returnedId;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getReturnedId() {
		return returnedId;
	}
	public void setReturnedId(String returnedId) {
		this.returnedId = returnedId;
	}
		
}
