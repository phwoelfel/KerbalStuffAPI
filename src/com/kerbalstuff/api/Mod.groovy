package com.kerbalstuff.api

class Mod {

	protected int id;
	protected String name;
	protected String author;
	protected int downloads;
	protected int followers;
	protected String shortDescription;
	
	protected int defaultVersion;
	
	protected List<ModVersion> modVersions;
	
	public Mod(def data){
		id = data['id'];
		name = data['name'];
		author = data['author'];
		downloads = data['downloads'];
		followers = data['followers'];
		shortDescription = data['short_description'];
		defaultVersion = data['default_version_id'];
		modVersions = new ArrayList<ModVersion>();
		
		data['versions'].each{ mv ->
			modVersions.add(new ModVersion(mv));
		}
	}
	
	public String toString(){
		return "${name} by ${author} (ID: ${id})\n\tDownloads: ${downloads}\n\tFollowers: ${followers}\n\t${shortDescription}";
	}
}
