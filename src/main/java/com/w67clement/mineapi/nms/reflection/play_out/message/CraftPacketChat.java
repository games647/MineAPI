package com.w67clement.mineapi.nms.reflection.play_out.message;

import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.message.PacketChat;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

public class CraftPacketChat extends PacketChat
{
	private static Class<?> packet;
	private static Constructor<?> packet_constructor;
	private static Field contentField;
	private static Field dataField;

	static
	{
		packet = getNmsClass("PacketPlayOutChat");
		packet_constructor = getConstructor(packet);
		contentField = getFirstFieldOfType(packet, getNmsClass("IChatBaseComponent"));
		dataField = getFirstFieldOfType(packet, byte.class);
	}

	public CraftPacketChat(String json) {
		super(json);
	}

	public CraftPacketChat(String json, byte data) {
		super(json, data);
	}

	@Override
	public void send(Player player)
	{
		NmsClass.sendPacket(player, this.constructPacket());
	}

	public Object constructPacket()
	{
		Object obj = newInstance(packet_constructor);
		setValue(obj, contentField, ChatComponentWrapper
				.makeChatComponentByJson(this.getContent()));
		setValue(obj, dataField, this.getData());
		return obj;
	}
}
