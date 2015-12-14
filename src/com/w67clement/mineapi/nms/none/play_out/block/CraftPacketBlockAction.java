package com.w67clement.mineapi.nms.none.play_out.block;

import java.lang.reflect.Constructor;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.BlockAction;
import com.w67clement.mineapi.block.PacketBlockAction;

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

	static
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

	@Override
	public void send(Player player)
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

		Object packet = ReflectionAPI.newInstance(constructor,
				BlockPositionWrapper.fromLocation(location).toBlockPosition(),
				block, this.action.getAction(), this.data);
		
		// Send the packet
		ReflectionAPI.NmsClass.sendPacket(player, packet);
	}

}
