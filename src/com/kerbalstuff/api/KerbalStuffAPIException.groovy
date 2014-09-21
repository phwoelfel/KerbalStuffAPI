package com.kerbalstuff.api

import wslite.http.HTTPRequest;
import wslite.http.HTTPResponse;
import wslite.rest.Response

public class KerbalStuffAPIException extends Throwable{

	String reason;
	HTTPResponse response;
	HTTPRequest request;
	
	public KerbalStuffAPIException(HTTPResponse response, HTTPRequest request){
		this.response = response;
		this.request = request;
	}
	
	public KerbalStuffAPIException(String reason, HTTPResponse response, HTTPRequest request){
		this.reason = reason;
		this.response = response;
		this.request = request;
	}

	public String getReason() {
		return reason;
	}

	public HTTPResponse getResponse() {
		return response;
	}
	
	public HTTPRequest getRequest() {
		return request;
	}
	
}
