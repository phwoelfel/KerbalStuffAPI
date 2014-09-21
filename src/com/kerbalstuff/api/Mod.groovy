package com.kerbalstuff.api

class Mod {

	protected int id;
	protected String name;
	protected String author;
	protected int downloads;
	protected int followers;
	protected String shortDescription;
	
	protected int defaultVersionID;
	
	protected List<ModVersion> modVersions;
	
	public Mod(def data){
		id = data['id'];
		name = data['name'];
		author = data['author'];
		downloads = data['downloads'];
		followers = data['followers'];
		shortDescription = data['short_description'];
		defaultVersionID = data['default_version_id'];
		modVersions = new ArrayList<ModVersion>();
		
		data['versions'].each{ mv ->
			modVersions.add(new ModVersion(mv));
		}
	}
	
	public String getInfo(){
		StringBuilder sb = new StringBuilder();
		sb.append("${name} by ${author} (ID: ${id})\n\tDownloads: ${downloads}\n\tFollowers: ${followers}\n\t${shortDescription}\n");
		sb.append("Versions:\n");
		modVersions.each{ mv ->
			sb.append("\t${mv}\n");
		}
		return sb.toString();
	}
	
	public String toString(){
		return "${name} by ${author}";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj && obj instanceof Mod){
			if( ((Mod)obj)?.getId() ==  id){
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public int getDefaultVersion(){
		return modVersions.find{it.id == defaultVersionID};
	}
	
	public int getDefaultVersionID() {
		return defaultVersionID;
	}

	public void setDefaultVersionID(int defaultVersion) {
		this.defaultVersionID = defaultVersion;
	}

	public int getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public int getDownloads() {
		return downloads;
	}

	public int getFollowers() {
		return followers;
	}

	public List<ModVersion> getModVersions() {
		return modVersions;
	}
	
	
}
