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
	
	public String toString(){
		return "Version: ${friendlyVersion} (ID: ${id}) for KSP ${kspVersion}";
	}
}
