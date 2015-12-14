package com.w67clement.mineapi.nms.v1_8_R1.play_out.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.PacketBlockChange;

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.PacketPlayOutBlockChange;

public class PacketBlockChange_v1_8_R1 extends PacketBlockChange
{

	public PacketBlockChange_v1_8_R1(Material material, Location loc) {
		super(material, 0, loc);
	}

	public PacketBlockChange_v1_8_R1(Material material, int data, Location loc) {
		super(material, data, loc);
	}

	public PacketBlockChange_v1_8_R1(Material material, int x, int y, int z) {
		super(material, x, y, z);
	}

	public PacketBlockChange_v1_8_R1(Material material, int data, int x, int y, int z) {
		super(material, data, x, y, z);
	}

	@Override
	public void send(Player player)
	{
		Block block = CraftMagicNumbers.getBlock(this.material);
		IBlockData blockData = block.fromLegacyData(data);
		// Packet
		PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange();
		// Setup
		ReflectionAPI.setValue(packet, ReflectionAPI.getField(packet.getClass(), "a", true),
				BlockPositionWrapper.fromLocation(this.location).toBlockPosition());
		ReflectionAPI.setValue(packet, ReflectionAPI.getField(packet.getClass(), "block", true), blockData);
		// Send packet to player
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
