package com.aol.w67clement.mineapi.nms.v1_8_R2.wrappers;

import net.minecraft.server.v1_8_R2.IChatBaseComponent;

public class ChatComponentWrapper_v1_8_R2 {
	
	public static IChatBaseComponent makeChatComponentByJson(String json) {
		return net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer.a(json);
	}
	
	public static String makeJsonByChatComponent(Object chatComponent) {
		return net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) chatComponent);
	}
	
}
