package com.aol.w67clement.mineapi.nms.v1_8_R1.wrappers;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;

public class ChatComponentWrapper_v1_8_R1 {
	
	public static IChatBaseComponent makeChatComponentByJson(String json) {
		return ChatSerializer.a(json);
	}
	
	public static String makeJsonByChatComponent(Object chatComponent) {
		return ChatSerializer.a((IChatBaseComponent) chatComponent);
	}
	
}
