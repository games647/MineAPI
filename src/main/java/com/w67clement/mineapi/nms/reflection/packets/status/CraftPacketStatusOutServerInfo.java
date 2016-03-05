package com.w67clement.mineapi.nms.reflection.packets.status;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.packets.status.PacketStatusOutServerInfo;
import java.lang.reflect.Constructor;

public class CraftPacketStatusOutServerInfo extends PacketStatusOutServerInfo
{
	private static Class<?> packetClass;
	private static Constructor<?> packetConstructor;
	// Bukkit
	private static Class<?> serverpingClass;
	// Glowstone
	private static Object jsonParser;

	static
	{
		if (MineAPI.isGlowstone())
		{
			packetClass = ReflectionAPI.getClass(
					"net.glowstone.net.message.status.StatusResponseMessage");
			packetConstructor = ReflectionAPI.getConstructor(packetClass,
					ReflectionAPI.getClass("org.json.simple.JSONObject"));
			jsonParser = ReflectionAPI
					.newInstance(ReflectionAPI.getConstructor(ReflectionAPI
							.getClass("org.json.simple.parser.JSONParser")));
		}
		else
		{
			packetClass = ReflectionAPI
					.getNmsClass("PacketStatusOutServerInfo");
			serverpingClass = ReflectionAPI.getNmsClass("ServerPing");
			packetConstructor = ReflectionAPI.getConstructor(packetClass,
					serverpingClass);
		}
	}

	public CraftPacketStatusOutServerInfo(ServerPingWrapper ping) {
		super(ping);
	}

	@Override
	public Object constructPacket()
	{
		if (MineAPI.isSpigot())
		{
			return this.constructPacket_Bukkit();
		}
		else if (MineAPI
				.isGlowstone()) { return this.constructPacket_Glowstone(); }
		return this.constructPacket_Bukkit();
	}

	private Object constructPacket_Bukkit()
	{
		Object serverping = this.ping.toServerPing();
		return ReflectionAPI.newInstance(packetConstructor, serverping);
	}

	private Object constructPacket_Glowstone()
	{
		Object json = ReflectionAPI.invokeMethod(jsonParser,
				ReflectionAPI.getMethod(jsonParser, "parse", String.class),
				this.ping.toJson());
		return ReflectionAPI.newInstance(packetConstructor, json);
	}
}
