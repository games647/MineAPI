package com.w67clement.mineapi.nms.reflection.play_out.tab;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.tab.PacketPlayerInfo;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class CraftPacketPlayerInfo extends PacketPlayerInfo
{

	private static Class<?> packetClass;
	private static Class<?> playerDataClass;
	private static Constructor<?> playerDataConstructor;
	private static Class<?> enum_player_info_actionClass;
	private static Object enum_add_player;
	private static Object enum_remove_player;
	private static Object enum_update_display_name;
	private static Object enum_update_game_mode;
	private static Object enum_update_latency;
	private static Class<?> enum_game_modeClass;
	private static Object enum_adventure;
	private static Object enum_creative;
	private static Object enum_spectator;
	private static Object enum_survival;

	static
	{
		if (MineAPI.isGlowstone())
		{

		}
		else
		{
			packetClass = ReflectionAPI.getNmsClass("PacketPlayOutPlayerInfo");
			if (MineAPI.getServerVersion().equals("v1_8_R1"))
				playerDataClass = ReflectionAPI.getNmsClass("PlayerInfoData");
			else
				playerDataClass = ReflectionAPI
						.getNmsClass("PacketPlayOutPlayerInfo$PlayerInfoData");
			if (MineAPI.getServerVersion().equals("v1_8_R1"))
				enum_player_info_actionClass = ReflectionAPI
						.getNmsClass("EnumPlayerInfoAction");
			else
				enum_player_info_actionClass = ReflectionAPI.getNmsClass(
						"PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
			for (Object obj : enum_player_info_actionClass.getEnumConstants())
			{
				if (obj.toString().equals("ADD_PLAYER"))
				{
					enum_add_player = obj;
				}
				else if (obj.toString().equals("REMOVE_PLAYER"))
				{
					enum_remove_player = obj;
				}
				else if (obj.toString().equals("UPDATE_DISPLAY_NAME"))
				{
					enum_update_display_name = obj;
				}
				else if (obj.toString().equals("UPDATE_GAME_MODE"))
				{
					enum_update_game_mode = obj;
				}
				else if (obj.toString().equals("UPDATE_LATENCY"))
				{
					enum_update_latency = obj;
				}
			}
			if (MineAPI.getServerVersion().equals("v1_8_R1"))
				enum_game_modeClass = ReflectionAPI.getNmsClass("EnumGamemode");
			else
				enum_game_modeClass = ReflectionAPI
						.getNmsClass("WorldSettings$EnumGamemode");
			for (Object obj : enum_game_modeClass.getEnumConstants())
			{
				if (obj.toString().equals("ADVENTURE"))
				{
					enum_adventure = obj;
				}
				else if (obj.toString().equals("CREATIVE"))
				{
					enum_creative = obj;
				}
				else if (obj.toString().equals("SPECTATOR"))
				{
					enum_spectator = obj;
				}
				else if (obj.toString().equals("SURVIVAL"))
				{
					enum_survival = obj;
				}
			}
			playerDataConstructor = playerDataClass.getConstructors()[0];
		}
	}

	public CraftPacketPlayerInfo(MC_EnumPlayerInfoAction action,
			List<PacketPlayerInfoData> data) {
		super(action, data);
	}

	@Override
	public void send(Player player)
	{
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
	}

	@Override
	public Object constructPacket()
	{
		if (MineAPI.isSpigot())
		{
			this.constructPacket_Bukkit();
		}
		else if (MineAPI
				.isGlowstone()) { throw new UnsupportedOperationException(
						"MineAPI cannot construct packet '"
								+ this.getClass().getSimpleName()
								+ "' for Glowstone."); }
		return this.constructPacket_Bukkit();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object constructPacket_Bukkit()
	{
		Object packet = ReflectionAPI
				.newInstance(ReflectionAPI.getConstructor(packetClass));
		Object action = null;
		switch (this.action)
		{
			case ADD_PLAYER:
				action = enum_add_player;
				break;
			case REMOVE_PLAYER:
				action = enum_remove_player;
				break;
			case UPDATE_DISPLAY_NAME:
				action = enum_update_display_name;
				break;
			case UPDATE_GAME_MODE:
				action = enum_update_game_mode;
				break;
			case UPDATE_LATENCY:
				action = enum_update_latency;
				break;
			default:
				break;
		}

		ReflectionAPI.setValue(packet,
				ReflectionAPI.getField(packetClass, "a", true), action);

		List data = new ArrayList();
		this.data.forEach(playerinfodata -> {
			Object gamemode = null;
			switch (playerinfodata.getGamemode())
			{
				case ADVENTURE:
					gamemode = enum_adventure;
					break;
				case CREATIVE:
					gamemode = enum_creative;
					break;
				case SPECTATOR:
					gamemode = enum_spectator;
					break;
				case SURVIVAL:
					gamemode = enum_survival;
					break;
				default:
					break;
			}
			data.add(ReflectionAPI.newInstance(playerDataConstructor, packet,
					playerinfodata.getProfile().toNms(),
					playerinfodata.getPing(), gamemode,
					ChatComponentWrapper.makeChatComponentByText(
							playerinfodata.getPlayerListName())));
		});

		ReflectionAPI.setValue(packet,
				ReflectionAPI.getField(packetClass, "b", true), data);
		return packet;
	}
}
