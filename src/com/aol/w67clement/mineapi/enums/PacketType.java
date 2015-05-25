package com.aol.w67clement.mineapi.enums;

public enum PacketType {
	
	PACKETPLAYOUT("PacketPlayOut"),
	PACKETPLAYIN("PacketPlayIn"),
	PACKETSTATUS("PacketStatus");
	
	private String a;

	private PacketType(String param_String) {
		this.a = param_String;
	}
	
	public String getPacketTypeName() {
		return this.a;
	}
	
	public String toString() {
		return this.a;
	}
}
