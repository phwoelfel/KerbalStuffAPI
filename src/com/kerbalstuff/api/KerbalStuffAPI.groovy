package com.kerbalstuff.api

import wslite.rest.RESTClient
import wslite.rest.Response

class KerbalStuffAPI {

	RESTClient client
	
	public KerbalStuffAPI(){
		client = new RESTClient("https://kerbalstuff.com/api")
	}
	
	public List<Mod> search(String search){
		Response response = client.get(path:'/search/mod', query:[query:search]);
		List<Mod> modList = new ArrayList<Mod>();
		
		
		if(response?.statusCode == 200){
			//println(response.json);
			response.json.each { m ->
				modList.add(new Mod(m));
			}
		}
		else{
			error(response)
		}
		
		return modList;
	}
	
	public Mod getMod(int modId){
		Response response = client.get(path:"/mod/${modId}");
		if(response?.statusCode == 200){
			return new Mod(response.json);
		}
		else{
			error(response);
		}
		return null;
	}
	
	public ModVersion getLatestVersion(int modId){
		Response response = client.get(path:"/mod/${modId}/latest");
		if(response?.statusCode == 200){
			return new ModVersion(response.json);
		}
		else{
			error(response);
		}
		return null;
	}
	
	protected void error(Response response){
		// TODO: error
		println("error");
		println(response);
		println(new String(response.data));
	}
	
}
