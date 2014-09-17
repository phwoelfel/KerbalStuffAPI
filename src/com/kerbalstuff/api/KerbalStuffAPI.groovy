package com.kerbalstuff.api

import wslite.rest.RESTClient
import wslite.rest.Response

class KerbalStuffAPI {

	RESTClient client
	
	public KerbalStuffAPI(){
		client = new RESTClient("https://kerbalstuff.com/api")
		//client.httpClient.sslTrustAllCerts = true
	}
	
	public List<Mod> search(String search){
		Response response = client.get(path:'/search/mod', query:[query:search]);
		List<Mod> modList = new ArrayList<Mod>();
		
		if(checkResponse(response)){
			//println(response.json);
			response.json.each { m ->
				modList.add(new Mod(m));
			}
		}
		
		return modList;
	}
	
	public Mod getMod(int modId){
		Response response = client.get(path:"/mod/${modId}");
		
		if(checkResponse(response)){
			return new Mod(response.json);
		}
		
		return null;
	}
	
	public ModVersion getLatestVersion(int modId){
		Response response = client.get(path:"/mod/${modId}/latest");
		
		if(checkResponse(response)){
			return new ModVersion(response.json);
		}
		
		return null;
	}
	
	protected boolean checkResponse(Response response){
		if(response?.statusCode != 200 || response?.json?.error == true || (response?.json?.error instanceof List && response?.json?.error?.contains(true))){ //if we search and get multiple results, we get a list of "errors" (nulls)
			// TODO: error
			println("Error: ${response.statusCode} ${response?.json?.error}");
			println(response);
			println(new String(response.data));
			println(response?.json);
			return false;
		}
		return true;
	}
	
}
