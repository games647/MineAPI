package com.w67clement.mineapi.enums;

import java.util.Map;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Maps;

public enum Variable {
	
	DISPLAYNAME("%displayname%"),
	MAX_PLAYERS("%max_players%"),
	MOTD("%motd%"),
	ONLINE_PLAYERS("%online_players%"),
	PLAYER("%player%"),
	SERVER_IP("%server_ip%"),
	SERVER_NAME("%server_name%"),
	VERSION("%version%"),
	WORLD("%world%");
	
	private String variableName;
	private final static Map<String, Variable> BY_Name = Maps.newHashMap();
	
	private Variable(String variableName) {
		this.variableName = variableName;
	}
	
	public String getVariableName() {
		return this.variableName;
	}
	
	public Variable getByName(String name) {
		Validate.notNull(name, "Variable name cannot be null!");
    	return BY_Name.get(name);
	}
	
	static {
        for (Variable variable : values()) {
            BY_Name.put(variable.variableName, variable);
        }
    }
}
