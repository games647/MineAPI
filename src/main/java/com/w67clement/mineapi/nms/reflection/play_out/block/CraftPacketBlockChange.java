package com.w67clement.mineapi.nms.reflection.play_out.block;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.ReflectionAPI.CraftPackage;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.PacketBlockChange;
import java.lang.reflect.Constructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CraftPacketBlockChange extends PacketBlockChange
{
	private static Class<?> packetClass;
	private static Constructor<?> packetConstructor;

	static {
		if (MineAPI.isGlowstone()) {
			packetClass = ReflectionAPI.getClass("net.glowstone.net.message.play.game.BlockChangeMessage");
			packetConstructor = ReflectionAPI.getConstructor(packetClass, int.class, int.class, int.class, int.class, int.class);
		} else {
			packetClass = ReflectionAPI.getNmsClass("PacketPlayOutBlockChange");
			packetConstructor = ReflectionAPI.getConstructor(packetClass);
		}
	}

	public CraftPacketBlockChange(Material material, Location loc) {
		super(material, 0, loc);
	}

	public CraftPacketBlockChange(Material material, int data, Location loc) {
		super(material, data, loc);
	}

	public CraftPacketBlockChange(Material material, int x, int y, int z) {
		super(material, x, y, z);
	}

	public CraftPacketBlockChange(Material material, int data, int x, int y,
			int z) {
		super(material, data, x, y, z);
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
			return this.constructPacket_Bukkit();
		}
		else if (MineAPI
				.isGlowstone()) { return this.constructPacket_Glowstone(); }
		return this.constructPacket_Bukkit();
	}

	private Object constructPacket_Bukkit()
	{
		Object block = ReflectionAPI.invokeMethod(null,
				ReflectionAPI.getMethod(
						ReflectionAPI.getCraftClass("CraftMagicNumbers",
								CraftPackage.ORG_BUKKIT_CRAFTBUKKIT_UTIL),
				"getBlock", Material.class), this.material);
		Object blockData = ReflectionAPI.invokeMethod(block, ReflectionAPI.getMethod(block, "fromLegacyData", int.class), this.data);
		Object packet = ReflectionAPI.newInstance(packetConstructor);
		ReflectionAPI.setValue(packet, ReflectionAPI.getField(packet.getClass(), "a", true),
				BlockPositionWrapper.fromLocation(this.location).toBlockPosition());
		ReflectionAPI.setValue(packet, ReflectionAPI.getField(packet.getClass(), "block", true), blockData);
		// Inset the code for construct the packet for bukkit.
		return packet;
	}
	
	@SuppressWarnings("deprecation")
	private Object constructPacket_Glowstone()
	{
		return ReflectionAPI.newInstance(packetConstructor,
				this.location.getBlockX(), this.location.getBlockY(),
				this.location.getBlockZ(), this.material.getId(), this.data);
	}

}
