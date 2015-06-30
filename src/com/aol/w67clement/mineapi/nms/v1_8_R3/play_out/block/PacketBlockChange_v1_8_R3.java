package com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.block;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.api.ReflectionAPI;
import com.aol.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.aol.w67clement.mineapi.block.PacketBlockChange;
import com.aol.w67clement.mineapi.enums.PacketType;

public class PacketBlockChange_v1_8_R3 implements PacketBlockChange {

	private Location location;
	private Material material;
	private int data;

	public PacketBlockChange_v1_8_R3(Material material, Location loc) {
		this(material, 0, loc);
	}

	public PacketBlockChange_v1_8_R3(Material material, int data, Location loc) {
		this.material = material;
		this.data = data;
		this.location = loc;
	}

	public PacketBlockChange_v1_8_R3(Material material, int x, int y, int z) {
		this(material, 0, x, y, z);
	}

	public PacketBlockChange_v1_8_R3(Material material, int data, int x, int y,
			int z) {
		this(material, data, new Location(null, x, y, z));
	}

	@Override
	public void send(Player player) {
		Block block = CraftMagicNumbers.getBlock(this.material);
		IBlockData blockData = block.fromLegacyData(data);
		// Packet
		PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange();
		// Setup
		ReflectionAPI.setValue(packet, ReflectionAPI.getField(
				packet.getClass(), "a", true), BlockPositionWrapper
				.fromLocation(this.location).toBlockPosition());
		ReflectionAPI.setValue(packet,
				ReflectionAPI.getField(packet.getClass(), "block", true),
				blockData);
		// Send packet to player
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendAll() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.send(player);
		}
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public PacketBlockChange setLocation(Location loc) {
		this.location = loc;
		return this;
	}

	@Override
	public PacketBlockChange setLocation(int x, int y, int z) {
		return this.setX(x).setY(y).setZ(z);
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	@Override
	public int getX() {
		return this.location.getBlockX();
	}

	@Override
	public int getY() {
		return this.location.getBlockY();
	}

	@Override
	public int getZ() {
		return this.location.getBlockZ();
	}

	@Override
	public PacketBlockChange setX(int x) {
		this.location.setX(x);
		return this;
	}

	@Override
	public PacketBlockChange setY(int y) {
		this.location.setY(y);
		return this;
	}

	@Override
	public PacketBlockChange setZ(int z) {
		this.location.setZ(z);
		return this;
	}

	@Override
	public Material getMaterial() {
		return this.material;
	}

	@Override
	public PacketBlockChange setMaterial(Material material) {
		this.material = material;
		return this;
	}

	@Override
	public int getData() {
		return this.data;
	}

	@Override
	public PacketBlockChange setData(int data) {
		this.data = data;
		return this;
	}

}
