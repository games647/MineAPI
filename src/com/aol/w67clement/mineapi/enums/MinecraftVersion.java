package com.aol.w67clement.mineapi.enums;

import java.util.Map;

import org.apache.commons.lang.Validate;

import com.aol.w67clement.mineapi.MineAPI;
import com.google.common.collect.Maps;

public enum MinecraftVersion {

	UNKNOW("Unknow", -1),
	OLD("1.6.x and old", -1),
	v1_7_R1("1.7.2", 4),
	v1_7_4("1.7.4", 4),
	v1_7_5("1.7.5", 4),
	v1_7_6("1.7.6", 5),
	v1_7_7("1.7.7", 5),
	v1_7_8("1.7.8", 5),
	v1_7_9("1.7.9", 5),
	v1_7_R4("1.7.10", 5),
	v1_8_R1("1.8", 47),
	v1_8_1("1.8.1", 47),
	v1_8_2("1.8.2", 47),
	v1_8_R2("1.8.3", 47);
	
	private String version = "1.0.0";
	private int protocol = 0;
	private final static Map<String, MinecraftVersion> BY_Version = Maps.newHashMap();
	
    MinecraftVersion(String version, int protocol) {
    	this.version = version;
    	this.protocol = protocol;
    }
	
    public String getVersion() {
    	return this.version;
    }
    
    public int getProtocolVersion() {
    	return this.protocol;
    }
    
    public String toString() {
    	return this.version;
    }
   
    public static MinecraftVersion getServerVersion() {
    	if (MineAPI.getServerVersion().equals("v1_8_R1")) {
    		return v1_8_R1;
    	} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
    		return v1_8_R2;
    	} else {
    		return UNKNOW;
    	}
    }
    
    public static MinecraftVersion getByVersion(String version) {
    	Validate.notNull(version, "Version cannot be null!");
    	return BY_Version.get(version);
    }
    
    static {
        for (MinecraftVersion mcVersion : values()) {
            BY_Version.put(mcVersion.version, mcVersion);
        }
    }
}
