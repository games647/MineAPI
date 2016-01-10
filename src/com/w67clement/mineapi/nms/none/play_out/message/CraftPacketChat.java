package com.w67clement.mineapi.nms.none.play_out.message;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.bukkit.entity.Player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.message.PacketChat;

public class CraftPacketChat extends PacketChat
{
	private static Class<?> packet;
	private static Constructor<?> packet_constructor;
	private static Field contentField;
	private static Field dataField;

	public CraftPacketChat(String json) {
		super(json);
	}

	public CraftPacketChat(String json, byte data) {
		super(json, data);
	}

	@Override
	public void send(Player player)
	{
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
	}

	static
	{
		if (MineAPI.isSpigot())
		{
			initForBukkit();
		}
		else if (MineAPI.isGlowstone())
		{
			initForGlowstone();
		}
		else
			initForBukkit();
	}

	private static void initForBukkit()
	{
		packet = ReflectionAPI.getNmsClass("PacketPlayOutChat");
		packet_constructor = ReflectionAPI.getConstructor(packet);
		contentField = ReflectionAPI.getField(packet, "a", true);
		dataField = ReflectionAPI.getField(packet, "b", true);
	}

	private static void initForGlowstone()
	{
		packet = ReflectionAPI
				.getClass("net.glowstone.net.message.play.game.ChatMessage");
		packet_constructor = ReflectionAPI.getConstructor(packet, String.class);
		contentField = ReflectionAPI.getField(packet, "text", true);
		dataField = ReflectionAPI.getField(packet, "mode", true);
	}

	public Object constructPacket()
	{
		if (MineAPI.isSpigot())
		{
			return this.constructPacketForBukkit();
		}
		else if (MineAPI.isGlowstone())
			return this.constructPacketForGlowstone();
		return this.constructPacketForBukkit();
	}

	private Object constructPacketForBukkit()
	{
		Object obj = ReflectionAPI.newInstance(packet_constructor);
		ReflectionAPI.setValue(obj, contentField, ChatComponentWrapper
				.makeChatComponentByJson(this.getContent()));
		ReflectionAPI.setValue(obj, dataField, this.getData());
		return obj;
	}

	private Object constructPacketForGlowstone()
	{
		Object obj = ReflectionAPI.newInstance(packet_constructor,
				this.getContent());
		Class<?> jsonObject = ReflectionAPI
				.getClass("org.json.simple.JSONObject");
		Class<?> jsonParserClass = ReflectionAPI
				.getClass("org.json.simple.parser.JSONParser");
		Object jsonParser = ReflectionAPI
				.newInstance(ReflectionAPI.getConstructor(jsonParserClass));
		Class<?> textMsgClass = ReflectionAPI
				.getClass("net.glowstone.util.TextMessage");
		Constructor<?> textMsgConstructor = ReflectionAPI
				.getConstructor(textMsgClass, jsonObject);

		JsonObject json = (JsonObject) new JsonParser().parse(this.json);

		Object content = ReflectionAPI
				.newInstance(textMsgConstructor,
						ReflectionAPI.invokeMethod(jsonParser,
								ReflectionAPI.getMethod(jsonParserClass,
										"parse", String.class),
						json.toString()));
		ReflectionAPI.setValue(obj, contentField, content);
		ReflectionAPI.setValue(obj, dataField, (int) this.getData());
		return obj;
	}
}
