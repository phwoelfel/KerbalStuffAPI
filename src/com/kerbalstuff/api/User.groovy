package com.kerbalstuff.api

class User {

	String username;
	String twitterUsername;
	String redditUsername;
	String ircNick;
	String description;
	String forumUsername;
	
	List<Mod> mods;
	
	public User(def data){
		username = data['username'];
		twitterUsername = data['twitterUsername'];
		redditUsername = data['redditUsername'];
		ircNick = data['ircNick'];
		description = data['description'];
		forumUsername = data['forumUsername'];
		
		mods = new ArrayList<Mod>();
		data['mods'].each{ m ->
			mods.add(new Mod(m));
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj && obj instanceof User){
			if( ((User)obj)?.getUsername() ==  username){
				return true;
			}
		}
		return false;
	}
	
	public String getInfo(){
		StringBuilder sb = new StringBuilder();
		sb.append("User: ${username}\n\t${description}\n\tReddit: ${redditUsername}\n\tIRC: ${ircNick}\n\tForum: ${forumUsername}\n\tTwitter: ${twitterUsername}\n");
		sb.append("Mods: \n");
		mods.each{ mod ->
			sb.append("\t${mod}\n");
		}
		return sb.toString();
	}
	
	public String toString(){
		return username;
	}

	public String getTwitterUsername() {
		return twitterUsername;
	}

	public void setTwitterUsername(String twitterUsername) {
		this.twitterUsername = twitterUsername;
	}

	public String getRedditUsername() {
		return redditUsername;
	}

	public void setRedditUsername(String redditUsername) {
		this.redditUsername = redditUsername;
	}

	public String getIrcNick() {
		return ircNick;
	}

	public void setIrcNick(String ircNick) {
		this.ircNick = ircNick;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getForumUsername() {
		return forumUsername;
	}

	public void setForumUsername(String forumUsername) {
		this.forumUsername = forumUsername;
	}

	public String getUsername() {
		return username;
	}

	public List<Mod> getMods() {
		return mods;
	}
	
	
	
}
