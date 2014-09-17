package com.kerbalstuff.api

import groovy.json.JsonSlurper
import wslite.rest.RESTClient
import wslite.rest.RESTClientException
import wslite.rest.Response

class KerbalStuffAPI {

	protected RESTClient client
	protected def authCookie;
	
	public KerbalStuffAPI(){
		client = new RESTClient("https://kerbalstuff.com/api")
		//client.httpClient.sslTrustAllCerts = true
	}
	
	public boolean authenticate(String user, String password) throws KerbalStuffAPIException{
		Response resp = post("/login", [username:user, password:password]);
		def setCookie = resp.headers.find{k,v -> k=="Set-Cookie"};
		//println(setCookie.getKey() + ": " +setCookie.getValue());
		authCookie = setCookie.getValue().split(";")[0].split("=")[1];
		return true;
	}
	
	public List<Mod> searchMod(String search, int page=1){
		Response response = get(path:'/search/mod', query:[query:search]);
		List<Mod> modList = new ArrayList<Mod>();
		
		response.json.each { m ->
			modList.add(new Mod(m));
		}
	
		
		return modList;
	}
	
	public Mod getMod(int modId){
		Response response = get(path:"/mod/${modId}");
		return new Mod(response.json);
	}
	
	public ModVersion getLatestModVersion(int modId){
		Response response = get(path:"/mod/${modId}/latest");
		return new ModVersion(response.json);
	}
	
	public List<User> searchUser(String search, int page=1){
		Response response = get(path:'/search/user', query:[query:search]);
		List<Mod> userList = new ArrayList<Mod>();
		
		response.json.each { u ->
			userList.add(new User(u));
		}
	
		
		return userList;
	}
	
	
	
	
	protected Response post(def data) throws KerbalStuffAPIException{
		Response response=null;
		def headers = [];
		if(authCookie){
			headers['Cookie'] = 'session=${authCookie}';
		}
		try{
			response = client.post(path:data['path'], headers:headers){
				params.each { k, v ->
					multipart k, v.bytes
				}
			}
			if(checkResponse(response)){
				return response;
			}
		}
		catch(RESTClientException ex){
			// catch http 400 etc errors
			
			String resp = new String(ex.response.data);
			def json = new JsonSlurper().parseText(resp);
			if(json?.error){
				throw new KerbalStuffAPIException(json?.reason, ex.response, ex.request)
			}
			throw new KerbalStuffAPIException(ex.response, ex.request)
		}
		return response;
	}
	
	protected Response get(def data) throws KerbalStuffAPIException{
		Response response=null;
		try{
			response = client.get(path:data['path'], query:data['query'])
			if(checkResponse(response)){
				return response;
			}
		}
		catch(RESTClientException ex){
			// catch http 400 etc errors
			
			String resp = new String(ex.response.data);
			def json = new JsonSlurper().parseText(resp);
			if(json?.error){
				throw new KerbalStuffAPIException(json?.reason, ex.response, ex.request)	
			}
			throw new KerbalStuffAPIException(ex.response, ex.request)
		}
		return response;
	}
	
	
	protected boolean checkResponse(Response response) throws KerbalStuffAPIException{
		if(response?.json?.error == true || (response?.json?.error instanceof List && response?.json?.error?.contains(true))){ //if we search and get multiple results, we get a list of "errors" (nulls)
			throw new KerbalStuffAPIException(response?.json?.reason, response.response, response.request);
			return false;
		}
		return true;
	}
	
}
