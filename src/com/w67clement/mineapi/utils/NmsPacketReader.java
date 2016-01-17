package com.w67clement.mineapi.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.entity.player.ClientCommand.ClientCommandType;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import com.w67clement.mineapi.message.PacketChat;
import com.w67clement.mineapi.nms.NmsPacket;
import com.w67clement.mineapi.packets.ProtocolState;
import com.w67clement.mineapi.packets.handshake.PacketHandshake;
import com.w67clement.mineapi.packets.play.in.PacketPlayInChat;
import com.w67clement.mineapi.packets.status.PacketStatusOutPong;
import com.w67clement.mineapi.packets.status.PacketStatusOutServerInfo;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.PacketExplosion;

public class NmsPacketReader
{
	private static NmsPacketReader injectReader = null;
	private static JsonParser json_parser = new JsonParser();

	public <U extends NmsPacket> U readPacket(Object packet,
			Class<? extends U> mineapi_packet)
	{
		if (injectReader == null)
		{
			boolean hasReader = false;
			for (Method method : this.getClass().getDeclaredMethods())
			{
				if (method.getName()
						.equals("readPacket_" + mineapi_packet.getSimpleName()))
				{
					hasReader = true;
					break;
				}
			}
			if (hasReader)
				return ReflectionAPI
						.invokeMethodWithType(this,
								ReflectionAPI.getMethod(this.getClass(),
										"readPacket_" + mineapi_packet
												.getSimpleName(),
										Object.class),
								mineapi_packet, packet);
			throw new RuntimeException("Cannot found reader for read '"
					+ packet.getClass().getSimpleName()
					+ "' for create instance of '"
					+ mineapi_packet.getSimpleName() + "'.");
		}
		else
			return injectReader.readPacket(packet, mineapi_packet);
	}

	/**
	 * Inject a new NmsPacketReader, use it only if you work for a new MC
	 * version or a module for support new server type or versions.
	 */
	public static void injectReader(NmsPacketReader newReader)
	{
		injectReader = newReader;
	}

	/* HANDSHAKE */

	public PacketHandshake readPacket_PacketHandshake(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.Handshake.getPacketName())
				|| PacketList.Handshake.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			PacketHandshake minePacket = null;
			if (MineAPI.isGlowstone())
			{
				int protocol = ReflectionAPI.getIntValue(packet, ReflectionAPI
						.getField(packet.getClass(), "version", true));
				String address = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "address",
								true),
						String.class);
				int port = ReflectionAPI.getIntValue(packet, ReflectionAPI
						.getField(packet.getClass(), "port", true));
				int nextProtocolState = ReflectionAPI.getIntValue(packet,
						ReflectionAPI.getField(packet.getClass(), "state",
								true));
				minePacket = MineAPI.getNmsManager().getPacketHandshake(
						protocol, address, port,
						ProtocolState.getById(nextProtocolState));
			}
			else
			{
				int protocol = ReflectionAPI.getIntValue(packet,
						ReflectionAPI.getField(packet.getClass(), "a", true));
				String hostname = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "hostname",
								true),
						String.class);
				int port = ReflectionAPI.getIntValue(packet, ReflectionAPI
						.getField(packet.getClass(), "port", true));
				Object protocolState = ReflectionAPI.getValue(packet,
						ReflectionAPI.getField(packet.getClass(), "d", true));
				int nextProtocolState = ReflectionAPI.invokeMethodWithType(
						protocolState,
						ReflectionAPI.getMethod(protocolState, "a"), int.class);
				minePacket = MineAPI.getNmsManager().getPacketHandshake(
						protocol, hostname, port,
						ProtocolState.getById(nextProtocolState));
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	/* PLAY */

	/* In */

	public PacketPlayInChat readPacket_PacketPlayInChat(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketPlayInChat.getPacketName())
				|| PacketList.PacketPlayInChat.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			PacketPlayInChat minePacket = MineAPI.getNmsManager()
					.getPacketPlayInChat("");
			if (MineAPI.isGlowstone())
			{
				minePacket.setMessage(ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "text", true),
						String.class));
			}
			else
			{
				minePacket.setMessage(ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "a", true),
						String.class));
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	public ClientCommand readPacket_ClientCommand(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketPlayInClientCommand.getPacketName())
				|| PacketList.PacketPlayInClientCommand.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			ClientCommand minePacket = null;
			if (MineAPI.isGlowstone())
			{
				int id = ReflectionAPI.getIntValue(packet, ReflectionAPI
						.getField(packet.getClass(), "action", true));
				for (ClientCommandType type : ClientCommandType.values())
				{
					if (id == type.getId()) minePacket = MineAPI.getNmsManager()
							.getPacketPlayInClientCommand(type);
				}
			}
			else
			{
				Object enum_constant = ReflectionAPI.getValue(packet,
						ReflectionAPI.getField(packet.getClass(), "a", true));
				int id = ReflectionAPI.invokeMethodWithType(enum_constant,
						ReflectionAPI.getMethod(enum_constant, "ordinal"),
						int.class);
				for (ClientCommandType type : ClientCommandType.values())
				{
					if (id == type.getId()) minePacket = MineAPI.getNmsManager()
							.getPacketPlayInClientCommand(type);
				}
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	/* Out */

	public PacketChat readPacket_PacketChat(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketPlayOutChat.getPacketName())
				|| PacketList.PacketPlayOutChat.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			PacketChat minePacket = MineAPI.getNmsManager().getPacketChat("");
			if (MineAPI.isGlowstone())
			{
				Object txtMsg = ReflectionAPI.getValue(packet, ReflectionAPI
						.getField(packet.getClass(), "text", true));
				minePacket.setContent(ReflectionAPI.invokeMethodWithType(txtMsg,
						ReflectionAPI.getMethod(txtMsg, "encode"),
						String.class));
				minePacket
						.setData(Byte.valueOf(String
								.valueOf(ReflectionAPI.getValueWithType(packet,
										ReflectionAPI.getField(
												packet.getClass(), "mode",
												true),
										int.class))));
			}
			else
			{
				String json = ChatComponentWrapper.makeJsonByChatComponent(
						ReflectionAPI.getValue(packet, ReflectionAPI
								.getField(packet.getClass(), "a", true)));
				minePacket.setContent(json);
				minePacket.setData(ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "b", true),
						byte.class));
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	public PacketExplosion readPacket_PacketExplosion(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketPlayOutExplosion.getPacketName())
				|| PacketList.PacketPlayOutExplosion.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			PacketExplosion minePacket = null;
			if (MineAPI.isGlowstone())
			{
				double x = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "x", true),
						double.class);
				double y = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "y", true),
						double.class);
				double z = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "z", true),
						double.class);
				float radius = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "radius",
								true),
						float.class);
				minePacket = MineAPI.getNmsManager().getExplosionPacket(null, x,
						y, z, radius, true);
			}
			else
			{
				double x = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "a", true),
						double.class);
				double y = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "b", true),
						double.class);
				double z = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "c", true),
						double.class);
				float radius = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "d", true),
						float.class);
				minePacket = MineAPI.getNmsManager().getExplosionPacket(null, x,
						y, z, radius, true);
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	public TabTitle readPacket_TabTitle(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketPlayOutPlayerListHeaderFooter
						.getPacketName())
				|| PacketList.PacketPlayOutPlayerListHeaderFooter
						.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			TabTitle minePacket = MineAPI.getNmsManager().getTabTitle("", "");
			if (MineAPI.isGlowstone())
			{
				Object msg_header = ReflectionAPI.getValue(packet, ReflectionAPI
						.getField(packet.getClass(), "header", true));
				Object msg_footer = ReflectionAPI.getValue(packet, ReflectionAPI
						.getField(packet.getClass(), "footer", true));
				String header = ReflectionAPI.invokeMethodWithType(packet,
						ReflectionAPI.getMethod(msg_header, "asPlaintext"),
						String.class);
				String footer = ReflectionAPI.invokeMethodWithType(packet,
						ReflectionAPI.getMethod(msg_footer, "asPlaintext"),
						String.class);
				minePacket.setHeader(header);
				minePacket.setFooter(footer);
			}
			else
			{
				JsonObject json_header = (JsonObject) json_parser
						.parse(ChatComponentWrapper.makeJsonByChatComponent(
								ReflectionAPI.getValue(packet,
										ReflectionAPI.getField(
												packet.getClass(), "a",
												true))));
				JsonObject json_footer = (JsonObject) json_parser
						.parse(ChatComponentWrapper.makeJsonByChatComponent(
								ReflectionAPI.getValue(packet,
										ReflectionAPI.getField(
												packet.getClass(), "b",
												true))));
				minePacket.setHeader(json_header.get("text").getAsString());
				minePacket.setFooter(json_footer.get("text").getAsString());
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	/*
	 * Inventories
	 */

	public WindowItems readPacket_WindowItems(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketPlayOutWindowItems.getPacketName())
				|| PacketList.PacketPlayOutWindowItems.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			WindowItems minePacket = null;
			if (MineAPI.isGlowstone())
			{
				int windowId = ReflectionAPI.getIntValue(packet,
						ReflectionAPI.getField(packet.getClass(), "id", true));
				ItemStack[] contents = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "items",
								true),
						ItemStack[].class);
				minePacket = MineAPI.getNmsManager().getWindowItemsPacket(
						windowId, Arrays.asList(contents));
			}
			else
			{
				int windowId = ReflectionAPI.getIntValue(packet,
						ReflectionAPI.getField(packet.getClass(), "a", true));
				Object items = ReflectionAPI.getValue(packet,
						ReflectionAPI.getField(packet.getClass(), "b", true));
				int itemsLength = Array.getLength(items);
				List<ItemStack> contents = new ArrayList<ItemStack>();
				for (int i = 0; i < itemsLength; i++)
				{
					Object nms_Item = Array.get(items, i);
					contents.add(
							ReflectionAPI.ItemStackConverter.fromNms(nms_Item));
				}
				minePacket = MineAPI.getNmsManager()
						.getWindowItemsPacket(windowId, contents);
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	/* STATUS */

	public PacketStatusOutServerInfo readPacket_PacketStatusOutServerInfo(
			Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketStatusOutServerInfo.getPacketName())
				|| PacketList.PacketStatusOutServerInfo.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			PacketStatusOutServerInfo minePacket = MineAPI.getNmsManager()
					.getPacketStatusOutServerInfo(
							MineAPI.getNmsManager().getServerPingWrapper());
			if (MineAPI.isGlowstone())
			{
				minePacket.setServerPing(
						ServerPingWrapper.Serializer.jsonToServerPing(
								ReflectionAPI.getValueWithType(packet,
										ReflectionAPI.getField(
												packet.getClass(), "json",
												true),
										String.class)));
			}
			else
			{
				minePacket.setServerPing(
						MineAPI.getNmsManager().getServerPingWrapper(
								ReflectionAPI.getValue(packet,
										ReflectionAPI.getField(
												packet.getClass(), "b",
												true))));
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

	public PacketStatusOutPong readPacket_PacketStatusOutPong(Object packet)
	{
		if (packet.getClass().getSimpleName()
				.equals(PacketList.PacketStatusOutPong.getPacketName())
				|| PacketList.PacketStatusOutPong.getPacketAliases()
						.contains(packet.getClass().getSimpleName()))
		{
			PacketStatusOutPong minePacket = null;
			if (MineAPI.isGlowstone())
			{
				long pong = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "time", true),
						long.class);
				minePacket = MineAPI.getNmsManager()
						.getPacketStatusOutPong(pong);
			}
			else
			{
				long pong = ReflectionAPI.getValueWithType(packet,
						ReflectionAPI.getField(packet.getClass(), "a", true),
						long.class);
				minePacket = MineAPI.getNmsManager()
						.getPacketStatusOutPong(pong);
			}
			return minePacket;
		}
		else
			throw new RuntimeException("Invalid packet given.");
	}

}
