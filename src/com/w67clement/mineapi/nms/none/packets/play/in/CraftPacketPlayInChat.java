package com.w67clement.mineapi.nms.none.packets.play.in;

import java.lang.reflect.Constructor;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.packets.play.in.PacketPlayInChat;

public class CraftPacketPlayInChat extends PacketPlayInChat
{
	private static Class<?> packetClass;
	private static Constructor<?> packetConstructor;

	public CraftPacketPlayInChat(String msg) {
		super(msg);
	}

	@Override
	public Object constructPacket()
	{
		return ReflectionAPI.newInstance(packetConstructor, this.msg);
	}

	static
	{
		if (MineAPI.isGlowstone())
		{
			packetClass = ReflectionAPI.getClass(
					"net.glowstone.net.message.play.game.IncomingChatMessage");
		}
		else
		{
			packetClass = ReflectionAPI.getNmsClass("PacketPlayInChat");
		}
		packetConstructor = ReflectionAPI.getConstructor(packetClass,
				String.class);
	}

}
