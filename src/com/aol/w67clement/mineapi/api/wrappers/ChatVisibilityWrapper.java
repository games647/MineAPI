package com.aol.w67clement.mineapi.api.wrappers;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.enums.mc.MC_ChatVisibility;

public class ChatVisibilityWrapper {

	public static Object chatVisibilityToEnumChatVisibility(
			MC_ChatVisibility chatVisibility) {
		if (MineAPI.getServerVersion().equals("v1_8_R3")) {
			return net.minecraft.server.v1_8_R3.EntityHuman.EnumChatVisibility
					.valueOf(chatVisibility.toString());
		} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
			return net.minecraft.server.v1_8_R2.EntityHuman.EnumChatVisibility
					.valueOf(chatVisibility.toString());
		} else if (MineAPI.getServerVersion().equals("v1_8_R1")) {
			return net.minecraft.server.v1_8_R1.EnumChatVisibility
					.valueOf(chatVisibility.toString());
		} else {
			return null;
		}
	}

	public static MC_ChatVisibility makeMCChatVisibilityByEnumChatVisibility(
			Object chatVisibility) {
		if (MineAPI.getServerVersion().equals("v1_8_R3")) {
			return MC_ChatVisibility
					.valueOf(((net.minecraft.server.v1_8_R3.EntityHuman.EnumChatVisibility) chatVisibility)
							.toString());
		} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
			return MC_ChatVisibility
					.valueOf(((net.minecraft.server.v1_8_R2.EntityHuman.EnumChatVisibility) chatVisibility)
							.toString());
		} else if (MineAPI.getServerVersion().equals("v1_8_R1")) {
			return MC_ChatVisibility
					.valueOf(((net.minecraft.server.v1_8_R1.EnumChatVisibility) chatVisibility)
							.toString());
		} else {
			return null;
		}
	}
}
