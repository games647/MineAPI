package com.w67clement.mineapi.nms.v1_8_R3.wrappers;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public class ChatComponentWrapper_v1_8_R3
{

	public static IChatBaseComponent makeChatComponentByJson(String json)
	{
		return net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer.a(json);
	}

	public static String makeJsonByChatComponent(Object chatComponent)
	{
		return net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer.a((IChatBaseComponent) chatComponent);
	}
}
