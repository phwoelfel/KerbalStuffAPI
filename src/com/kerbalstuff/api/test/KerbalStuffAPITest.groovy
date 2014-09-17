package com.kerbalstuff.api.test

import com.kerbalstuff.api.KerbalStuffAPI;
import com.kerbalstuff.api.Mod;
import com.kerbalstuff.api.ModVersion;

class KerbalStuffAPITest {

	
	static main(args) {
		KerbalStuffAPITestGui gui = new KerbalStuffAPITestGui();
		
		/*
		String search = "Resource Overview";
		int id = 8;
		
		KerbalStuffAPI api = new KerbalStuffAPI();
		
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
