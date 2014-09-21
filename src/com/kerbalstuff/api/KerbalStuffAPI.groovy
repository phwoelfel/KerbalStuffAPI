package com.kerbalstuff.api

import groovy.json.JsonException
import groovy.json.JsonSlurper
import wslite.rest.RESTClient
import wslite.rest.RESTClientException
import wslite.rest.Response

/**
 * An API implementation of the KerbalStuff (http://kerbalstuff.com) Web API for Groovy.
 * <br />
 * <h2>Exceptions</h2>
 * If an error occurs, a KerbalStuffAPIException will be thrown. KerbalStuffAPIException.getReason() is not null if it is an error returned by the API, else it is possibly a HTTP error.
 */
public class KerbalStuffAPI {

	protected RESTClient client
	protected def authCookie;
	
	public KerbalStuffAPI(){
		client = new RESTClient("https://kerbalstuff.com/api")
		//client.httpClient.sslTrustAllCerts = true
	}
	
	/**
	 * See: {@link #authenticate(String, String) authenticate}
	 */
	public boolean login(String user, String password) throws KerbalStuffAPIException{
		return authenticate(user, password);
	}
	
	/**
	 * Authenticates a User to the KerbalStuff API. On success the returned Cookie will be saved by the API.
	 * @param user the username
	 * @param password the password
	 * @return true if the user was authenticated successfull, else false
	 * @throws KerbalStuffAPIException 
	 */
	public boolean authenticate(String user, String password) throws KerbalStuffAPIException{
		Response resp = post("/login", [username:user, password:password]);
		def setCookie = resp.headers.find{k,v -> k=="Set-Cookie"};
		//println(setCookie.getKey() + ": " +setCookie.getValue());
		authCookie = setCookie?.getValue()?.split(";")[0]?.split("=")[1];
		if(authCookie){
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the authentication cookie.
	 * @return If already authenticated or manually set, the authentication cookie, else null.
	 */
	public def getAuthCookie(){
		return authCookie;
	}
	
	/**
	 * Set the value of the cookie used to authenticate a user.
	 * @param cookie The value of the authentication cookie.
	 */
	public void setAuthCookie(def cookie){
		authCookie = cookie;
	}
	
	/**
	 * Searches the KerbalStuff API for an user.
	 * @param search The name of the user to search.
	 * @param page Optional parameter which allows to search for more users.
	 * @return A List of User Objects.
	 * @throws KerbalStuffAPIException
	 */
	public List<User> searchUser(String search, int page=1) throws KerbalStuffAPIException{
		Response response = get(path:'/search/user', query:[query:search, page:page]);
		List<Mod> userList = new ArrayList<Mod>();
		
		response.json.each { u ->
			userList.add(new User(u));
		}
	
		
		return userList;
	}
	
	
	/**
	 * Gets a specific user by its username.
	 * @param username
	 * @return A User object containing the user.
	 * @throws KerbalStuffAPIException
	 */
	public User getUser(String username) throws KerbalStuffAPIException{
		Response response = get(path:'/user/${username}');
		return new User(response.json);
	}
	
	
	/**
	 * Searches the KerbalStuff API for a mod.
	 * @param search The name of the mod.
	 * @param page Optional parameter which allows to search for more mods.
	 * @return A List of Mod Objects.
	 * @throws KerbalStuffAPIException
	 */
	public List<Mod> searchMod(String search, int page=1) throws KerbalStuffAPIException{
		Response response = get(path:'/search/mod', query:[query:search, page:page]);
		List<Mod> modList = new ArrayList<Mod>();
		
		response.json.each { m ->
			modList.add(new Mod(m));
		}
	
		
		return modList;
	}
	
	
	/**
	 * Gets a specific mod by its id.
	 * @param modId The ID of the mod.
	 * @return A Mod object containing the mod.
	 * @throws KerbalStuffAPIException
	 */
	public Mod getMod(int modId) throws KerbalStuffAPIException{
		Response response = get(path:"/mod/${modId}");
		return new Mod(response.json);
	}
	
	/**
	 * Gets the latest ModVersion for the specified mod
	 * @param modId The ID of the mod.
	 * @return A ModVersion object containing the mod version info.
	 * @throws KerbalStuffAPIException
	 */
	public ModVersion getLatestModVersion(int modId) throws KerbalStuffAPIException{
		Response response = get(path:"/mod/${modId}/latest");
		return new ModVersion(response.json);
	}
	
	/**
	 * Creates a new mod with the given parameters.
	 * Requires authentication!
	 * @param name The name of the new mod.
	 * @param shortDescription A short description of the new mod. 
	 * @param version The version number of the first published version which will be created.
	 * @param kspVersion The KSP version for which this mod is made.
	 * @param license The license of the mod.
	 * @param zipFile A File object pointing to the zip file containing the mod.
	 * @return The URL to the newly created mod.
	 * @throws KerbalStuffAPIException
	 */
	public String createMod(String name, String shortDescription, String version, String kspVersion, String license, File zipFile) throws KerbalStuffAPIException{
		if(authCookie){
			def data = [:];
			data['name'] = name;
			data['short-description'] = shortDescription;
			data['version'] = version;
			data['ksp-version'] = kspVersion;
			data['license'] = license;
			data['zipball'] = zipFile;
			Response response = post("/mod/create", data)
			return response?.json?.url;
		}
		throw new KerbalStuffAPIException("Not authenticated!", null, null);
	}
	
	/**
	 * See: {@link #addModVersion(int, String, String, String, boolean, File) addModVersion}
	 */
	public String updateMod(int modID, String changelog, String version, String kspVersion, boolean notifyFollowers, File zipFile) throws KerbalStuffAPIException{
		return addModVersion(modID, changelog, version, kspVersion, notifyFollowers, zipFile);
	}

	/**
	 * Add a new version to an already existing mod.
	 * Requires authentication!
	 * @param modID The ID of the mod to update.
	 * @param changelog A String containing the changelog for this version.
	 * @param version The new version number.
	 * @param kspVersion The KSP version this update is compatible with. 
	 * @param notifyFollowers Should the followers of this mod get an E-Mail or not.
	 * @param zipFile The actual ZIP file to upload.
	 * @return The URL to the mod that was updated.
	 * @throws KerbalStuffAPIException
	 */
	public String addModVersion(int modID, String changelog, String version, String kspVersion, boolean notifyFollowers, File zipFile) throws KerbalStuffAPIException{
		if(authCookie){
			def data = [:];
			data['changelog'] = changelog;
			data['version'] = version;
			data['ksp-version'] = kspVersion;
			data['notify-followers'] = notifyFollowers;
			data['zipball'] = zipFile;
			Response response = post("/mod/${modID}/update", data)
			return response?.json?.url;
		}
		throw new KerbalStuffAPIException("Not authenticated!", null, null);
	}
	

	
	/**
	 * Sends a POST request to the given relative API path.
	 * @param path The path relative to http://kerbalstuff.com/api/
	 * @param data A Map containing the data which will be sent multipart/form-data encoded.
	 * @return The Response object from the RESTClient.post() call.
	 * @throws KerbalStuffAPIException
	 */
	protected Response post(def path, def data) throws KerbalStuffAPIException{
		Response response=null;
		def headers = [:];
		if(authCookie){
			headers['Cookie'] = "session=${authCookie}";
		}
		try{
			
			response = client.post(path: path, headers:headers){
				data.each { k, v ->
					if(v instanceof File){
						multipart k, v
					}
					else{
						multipart k, v.toString().bytes
					}
				}
			}
			if(checkResponse(response)){
				return response;
			}
		}
		catch(RESTClientException ex){
			// catch http 400 etc errors
			
			String resp = new String(ex.response.data);
			try{
				def json = new JsonSlurper().parseText(resp);
				if(json?.error){
					throw new KerbalStuffAPIException(json?.reason, ex.response, ex.request)
				}
			}
			catch(JsonException exe){
				
			}
			throw new KerbalStuffAPIException(ex.response, ex.request)
		}
		return response;
	}
	
	/**
	 * Sends a GET request to the path set in the data Map with the query parameters from the data Map.
	 * @param data A Map containing the keys 'path' and 'query'
	 * @return The Response object from the RESTClient.get() call.
	 * @throws KerbalStuffAPIException
	 */
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
	
	
	/**
	 * Checks if a response is valid. (Checks if json contains an error element)
	 * @param response The response to check.
	 * @return If this response contains an error element or not.
	 * @throws KerbalStuffAPIException
	 */
	protected boolean checkResponse(Response response) throws KerbalStuffAPIException{
		if(response?.json?.error == true || (response?.json?.error instanceof List && response?.json?.error?.contains(true))){ //if we search and get multiple results, we get a list of "errors" (nulls)
			throw new KerbalStuffAPIException(response?.json?.reason, response.response, response.request);
			return false;
		}
		return true;
	}
	
}
