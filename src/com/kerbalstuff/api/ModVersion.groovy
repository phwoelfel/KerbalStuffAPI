package com.kerbalstuff.api

class ModVersion {

	protected int id;
	protected String friendlyVersion;
	protected String kspVersion;
	protected String downloadPath;
	protected String changelog;
	
	public ModVersion(def data){
		id = data['id'];
		friendlyVersion = data['friendly_version'];
		kspVersion = data['ksp_version'];
		downloadPath = data['download_path'];
		changelog = data['changelog'];
	}
	
	public String getInfo(){
		return "Version: ${friendlyVersion} for KSP ${kspVersion} (ID: ${id})\n\t${downloadPath}\n\t${changelog}"
	}
	
	public String toString(){
		return "Version: ${friendlyVersion} for KSP ${kspVersion} (ID: ${id})";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj && obj instanceof ModVersion){
			if( ((ModVersion)obj)?.getId() ==  id){
				return true;
			}
		}
		return false;
	}

	public String getFriendlyVersion() {
		return friendlyVersion;
	}

	public void setFriendlyVersion(String friendlyVersion) {
		this.friendlyVersion = friendlyVersion;
	}

	public String getKspVersion() {
		return kspVersion;
	}

	public void setKspVersion(String kspVersion) {
		this.kspVersion = kspVersion;
	}

	public String getChangelog() {
		return changelog;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public int getId() {
		return id;
	}

	public String getDownloadPath() {
		return downloadPath;
	}
	
	
}
