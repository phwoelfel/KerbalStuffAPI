package com.kerbalstuff.api.test

import com.kerbalstuff.api.KerbalStuffAPI
import com.kerbalstuff.api.KerbalStuffAPIException
import com.kerbalstuff.api.Mod
import com.kerbalstuff.api.ModVersion
import com.kerbalstuff.api.User

class KerbalStuffAPITest {

	KerbalStuffAPI api;
	
	String modSearch = "Resource Overview";
	String userSearch = "sir";
	int modID = 8;
	int modVersionID = 611;
	String userName = "SMILIE";
	
	public KerbalStuffAPITest(){
		api = new KerbalStuffAPI();
	}
	
	static main(args) {
		KerbalStuffAPITest test = new KerbalStuffAPITest();
		test.testValid(args);
		
		//KerbalStuffAPITestTableGui tableGui = new KerbalStuffAPITestTableGui();
	}
	
	
	public void testValid(def args){
		try{
			// get new mod list
			println(("#"*10)+" getting new mods");
			List<Mod> newModList = api.getNewMods();
			newModList.each{ m ->
				println(m.getInfo());
			}
			
			// get top mod list
			println(("#"*10)+" getting top mods");
			List<Mod> topModList = api.getTopMods();
			topModList.each{ m ->
				println(m.getInfo());
			}
			
			// get featured mod list
			println(("#"*10)+" getting featured mods");
			List<Mod> featuredModList = api.getFeaturedMods();
			featuredModList.each{ m ->
				println(m.getInfo());
			}
			
			
			// authentication via arguments
			if(args.size()>1){
				println(("#"*10)+" authenticating ${args[0]}")
				def authret = api.authenticate(args[0], args[1]);
				// def authret = api.login(args[0], args[1]);
				if(authret){
					println(("#"*10)+"authenticated ${args[0]} sucessfull!")
				}
			}
			
			
			// search for user
			println(("#"*10)+" searching for user ${userSearch}")
			List<User> userList = api.searchUser(userSearch);
			userList.each { u ->
				println(u.getInfo());
			}
			
			
			// get User
			println(("#"*10)+" getting user ${userName}");
			User u = api.getUser(userName);
			println(u.getInfo());
			
			
			// search for mod
			println(("#"*10)+" searching for mod ${modSearch}");
			List<Mod> modList = api.searchMod(modSearch);
			modList.each{ m ->
				println(m.getInfo());
			}
			
			// get mod
			println(("#"*10)+" getting mod with id: ${modID}");
			Mod m = api.getMod(modID);
			println(m.getInfo());
			
			// get mod version
			println(("#"*10)+" getting mod version with id: ${modVersionID} for mod ${modID}");
			ModVersion mv = api.getModVersion(modID, modVersionID);
			println(mv.getInfo());
			
			// get latest version
			println(("#"*10)+" getting latest version for mod ${modID}");
			ModVersion lmv = api.getLatestModVersion(modID);
			println(lmv.getInfo());
			
			
			
			// create mod
			println(("#"*10)+" trying to create mod");
			int newModID = api.createMod("KS API Test Mod", "Just a mod to test the KerbalStuff API", "1.0", "0.24.2", "MIT", new File("test.zip"));
			println(("#"*10)+" created new mod ${resp}");
			// def newModID =  Integer.parseInt(resp.split("/")[2]); // response is url in the format /mod/<id>/<name> --> splitting the id out
			println(("#"*10)+" trying to update mod: ${newModID}");
			def updateResp = api.addModVersion(newModID, "Changing stuff!", "1.1", "0.24.2", false, new File("test_update.zip"))
			println(("#"*10)+" update response: ${updateResp}")
			
			
		}
		catch(KerbalStuffAPIException ex){
			println("Error!!!")
			if(ex.getReason()){
				println("Reason: ${ex.getReason()}")
			}
			if(ex.getRequest()){
				println("Request: ${ex.getRequest()}")
				println("Data: ${new String(ex.getRequest().data)}")
			}
			if(ex.getResponse()){
				println("Response: ${ex.getResponse()}")
				println("Data: ${new String(ex.getResponse().data)}")
			}
		}
	}

}
