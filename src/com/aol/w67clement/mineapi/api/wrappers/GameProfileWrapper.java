package com.aol.w67clement.mineapi.api.wrappers;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

public class GameProfileWrapper {
	
	private UUID id;
	private String name;

	public GameProfileWrapper(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return "GameProfileWrapper[ID:" + id.toString() + ",Name:" + this.getName() + "]";
	}
	
	public String getName() {
		return this.name;
	}
	
	public UUID getUUID() {
		return this.id;
	}
	
	public Object convertToNmsGameProfile() {
		return new GameProfile(this.id, this.name);
	}
}
