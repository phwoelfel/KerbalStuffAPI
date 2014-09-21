package com.kerbalstuff.api.test

import wslite.rest.Response

import com.kerbalstuff.api.KerbalStuffAPI
import com.kerbalstuff.api.KerbalStuffAPIException

class KerbalStuffAPITest {

	
	static main(args) {
		
		// update README.md --> page for search, notify-followers for update = true (not yes)
		// update api for notify followers to allow yes
		
		//KerbalStuffAPITestGui gui = new KerbalStuffAPITestGui();
		
		
		String search = "Resource Overview";
		int id = 8;
		
		KerbalStuffAPI api = new KerbalStuffAPI();
		/*
		List<User> userList = api.searchUser("");
		userList.each { u ->
			println(u);
			// println(u.getForumUsername());
		}
		*/
		
		if(args.size()>1){
			println("authenticating ${args[0]}")
			def authret = api.authenticate(args[0], args[1]);
			if(authret){
				println("authenticated sucessfull!")
				println("auth cookie: ${api.authCookie}")
			}
		}
		
		try{
			println("trying to create mod")
			Response resp = api.createMod("KS API Test mod", "Just a mod to test the KerbalStuff API", "1.0", "0.24.2", "MIT", new File("test.zip"));
			println(new String(resp.getData()));
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
		
		
		/*
		println("searching for ${search}");
		List<Mod> modList = api.search(search);
		modList.each{ m ->
			println(m);
			println("Versions:")
			m.modVersions.each{ mv ->
				println(mv)
			}
		}
		
		println("\n------------------------------------\n");
		println("getting mod for id: ${id}")
		Mod ir = api.getMod(id);
		println(ir);
		println("Versions:")
		ir.modVersions.each{ mv ->
			println(mv)
		}
		
		
		println("\n------------------------------------\n");
		println("getting latest version for id: ${id}")
		ModVersion irLatest = api.getLatestVersion(id);
		println(irLatest);
		println(irLatest.changelog);
		*/
		
	}

}
