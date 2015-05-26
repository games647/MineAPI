package com.aol.w67clement.mineapi.api.wrappers;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.message.FancyMessage;
import com.aol.w67clement.mineapi.nms.v1_8_R1.wrappers.ChatComponentWrapper_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R2.wrappers.ChatComponentWrapper_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R3.wrappers.ChatComponentWrapper_v1_8_R3;

public class ChatComponentWrapper {

	public static Object makeChatComponentByText(String text) {
		return makeChatComponentByJson("[{text:\"" + text + "\"}]");
	}

	public static Object makeChatComponentByJson(String json) {
		if (MineAPI.getServerVersion().equals("v1_8_R3")) {
			return ChatComponentWrapper_v1_8_R3.makeChatComponentByJson(json);
		} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
			return ChatComponentWrapper_v1_8_R2.makeChatComponentByJson(json);
		} else if (MineAPI.getServerVersion().equals("v1_8_R1")) {
			return ChatComponentWrapper_v1_8_R1.makeChatComponentByJson(json);
		} else {
			return null;
		}
	}

	public static Object makeChatComponentByFancyMessage(FancyMessage msg) {
		return makeChatComponentByJson(msg.toJSONString());
	}

	public static String makeJsonByChatComponent(Object chatComponent) {
		if (MineAPI.getServerVersion().equals("v1_8_R3")) {
			return ChatComponentWrapper_v1_8_R3
					.makeJsonByChatComponent(chatComponent);
		} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
			return ChatComponentWrapper_v1_8_R2
					.makeJsonByChatComponent(chatComponent);
		} else if (MineAPI.getServerVersion().equals("v1_8_R1")) {
			return ChatComponentWrapper_v1_8_R1
					.makeJsonByChatComponent(chatComponent);
		} else {
			return null;
		}
	}
}
