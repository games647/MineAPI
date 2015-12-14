package com.w67clement.mineapi.system;

public enum ServerType
{
	
	CRAFTBUKKIT("CraftBukkit"),
	GLOWSTONE("Glowstone"),
	PAPERSPIGOT("PaperSpigot"),
	RAINBOW_PROJECT("Rainbow_Project"),
	SPIGOT("Spigot");
	
	private String serverName;
	
	private ServerType(String name) {
		this.serverName = name;
	}
	
	public String getServerName() {
		return this.serverName;
	}

}
