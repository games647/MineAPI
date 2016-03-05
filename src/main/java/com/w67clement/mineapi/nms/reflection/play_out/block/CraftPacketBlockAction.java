package com.w67clement.mineapi.nms.reflection.play_out.block;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.BlockAction;
import com.w67clement.mineapi.block.PacketBlockAction;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * CraftPacketBlockAction is implements the methods for send the packet.
 * 
 * @author w67clement
 *
 */
public class CraftPacketBlockAction extends PacketBlockAction
{
	private static Class<?> blockClass;
	private static Class<?> blocksClass;
	private static Class<?> blockPositionClass;
	private static Class<?> packetClass;
	private static Object noteblock;
	private static Object piston;
	private static Object chest;

	static
	{
		if (MineAPI.isGlowstone())
		{
			initGlowstone();
		}
		else
		{
			blockClass = ReflectionAPI.getNmsClass("Block");
			blocksClass = ReflectionAPI.getNmsClass("Blocks");
			blockPositionClass = ReflectionAPI.getNmsClass("BlockPosition");
			packetClass = ReflectionAPI.getNmsClass("PacketPlayOutBlockAction");

			noteblock = ReflectionAPI.getValue(null,
					ReflectionAPI.getField(blocksClass, "NOTEBLOCK", true));
			piston = ReflectionAPI.getValue(null,
					ReflectionAPI.getField(blocksClass, "PISTON", true));
			chest = ReflectionAPI.getValue(null,
					ReflectionAPI.getField(blocksClass, "CHEST", true));
		}
	}

	public CraftPacketBlockAction(Location location, BlockAction action) {
		super(location, action);
	}

	public CraftPacketBlockAction(Location location, BlockAction action,
			int data) {
		super(location, action, data);
	}

	public CraftPacketBlockAction(int x, int y, int z, BlockAction action) {
		super(x, y, z, action);
	}

	public CraftPacketBlockAction(int x, int y, int z, BlockAction action,
			int data) {
		super(x, y, z, action, data);
	}

	private static void initGlowstone()
	{
		blockClass = ReflectionAPI
				.getClass("net.glowstone.block.blocktype.BlockType");
		blocksClass = ReflectionAPI.getClass("net.glowstone.block.ItemTable");
		packetClass = ReflectionAPI.getClass(
				"net.glowstone.net.message.play.game.BlockActionMessage");

		Object itemTableInstance = ReflectionAPI.invokeMethod(null,
				ReflectionAPI.getMethod(blocksClass, "instance"));
		Method getBlock = ReflectionAPI.getMethod(blocksClass, "getBlock",
				Material.class);

		noteblock = ReflectionAPI.invokeMethod(itemTableInstance, getBlock,
				BlockAction.BlockActionType.NOTE_BLOCK.getMaterial());
		piston = ReflectionAPI.invokeMethod(itemTableInstance, getBlock,
				BlockAction.BlockActionType.PISTON.getMaterial());
		chest = ReflectionAPI.invokeMethod(itemTableInstance, getBlock,
				BlockAction.BlockActionType.CHEST.getMaterial());
	}

	@Override
	public void send(Player player)
	{
		// Send the packet
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
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
		// Constructing the packet
		Constructor<?> constructor = ReflectionAPI.getConstructor(packetClass,
				blockPositionClass, blockClass, int.class, int.class);
		Object block = null;

		switch (action.getType())
		{
			case CHEST:
				block = chest;
				break;
			case PISTON:
				block = piston;
				break;
			case NOTE_BLOCK:
				block = noteblock;
				break;
			default:
				break;
		}

		return ReflectionAPI.newInstance(constructor,
				BlockPositionWrapper.fromLocation(location).toBlockPosition(),
				block, this.action.getAction(), this.data);
	}

	private Object constructPacket_Glowstone()
	{
		Constructor<?> constructor = ReflectionAPI.getConstructor(packetClass,
				int.class, int.class, int.class, int.class, int.class,
				int.class);
		@SuppressWarnings("deprecation")
		int block = Material.CHEST.getId();

		switch (action.getType())
		{
			case CHEST:
				block = ReflectionAPI.invokeMethodWithType(chest,
						ReflectionAPI.getMethod(chest, "getId"), int.class);
				break;
			case PISTON:
				block = ReflectionAPI.invokeMethodWithType(piston,
						ReflectionAPI.getMethod(piston, "getId"), int.class);;
				break;
			case NOTE_BLOCK:
				block = ReflectionAPI.invokeMethodWithType(noteblock,
						ReflectionAPI.getMethod(noteblock, "getId"),
						int.class);;
				break;
			default:
				break;
		}

		return ReflectionAPI.newInstance(constructor, this.location.getBlockX(),
				this.location.getBlockY(), this.location.getBlockZ(),
				this.action.getAction(), this.data, block);
	}

}
