package com.w67clement.mineapi.nms.none.play_out.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.enums.PacketType;

public class CraftPacketBlockBreakAnimation implements PacketBlockBreakAnimation
{

	private int entityId;
	private Location blockLocation;
	private byte destroyStage;

	public CraftPacketBlockBreakAnimation(MC_Player player, Location blockLocation, byte destroyStage) {
		this.entityId = player.getEntityId();
		this.blockLocation = blockLocation;
		this.destroyStage = destroyStage;
	}

	public CraftPacketBlockBreakAnimation(MC_Player player, int x, int y, int z, byte destroyStage) {
		this(player, new Location(Bukkit.getWorlds().get(0), x, y, z), destroyStage);
	}

	public CraftPacketBlockBreakAnimation(Player player, Location blockLocation, byte destroyStage) {
		Object nmsPlayer = ReflectionAPI.invokeMethod(player, ReflectionAPI.getMethod(player, "getHandle"));
		this.entityId = (int) ReflectionAPI.invokeMethod(nmsPlayer, ReflectionAPI.getMethod(nmsPlayer, "getId"));
		this.blockLocation = blockLocation;
		this.destroyStage = destroyStage;
	}

	public CraftPacketBlockBreakAnimation(Player player, int x, int y, int z, byte destroyStage) {
		this(player, new Location(Bukkit.getWorlds().get(0), x, y, z), destroyStage);
	}

	@Override
	public void send(Player player)
	{
		// Get packet class
		Class<?> packetClass = ReflectionAPI.getNmsClass("PacketPlayOutBlockBreakAnimation");
		// Create packet
		Object packet = ReflectionAPI.newInstance(
				ReflectionAPI.getConstructor(packetClass, Integer.class, ReflectionAPI.getNmsClass("BlockPosition"),
						Integer.class),
				this.entityId, BlockPositionWrapper.fromLocation(this.blockLocation), this.destroyStage);
		// Send the packet.
		ReflectionAPI.NmsClass.sendPacket(player, packet);
	}

	@Override
	public void sendAll()
	{
		for (Player player : Bukkit.getOnlinePlayers())
		{
			this.send(player);
		}
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public Location getBlockLocation()
	{
		return this.blockLocation;
	}

	@Override
	public PacketBlockBreakAnimation setBlockLocation(Location loc)
	{
		this.blockLocation = loc;
		return this;
	}

	@Override
	public PacketBlockBreakAnimation setBlockLocation(int x, int y, int z)
	{
		this.blockLocation = new Location(null, x, y, z);
		return this;
	}

	@Override
	public byte getDestroyStage()
	{
		return this.destroyStage;
	}

	@Override
	public PacketBlockBreakAnimation setDestroyStage(byte destroyStage)
	{
		this.destroyStage = destroyStage;
		return this;
	}

	@Override
	public int getEntityId()
	{
		return this.entityId;
	}

	@Deprecated
	@Override
	public PacketBlockBreakAnimation setEntityId(int id)
	{
		this.entityId = id;
		return this;
	}

	@Override
	public PacketBlockBreakAnimation setEntityId(Player player)
	{
		Object nmsPlayer = ReflectionAPI.invokeMethod(player, ReflectionAPI.getMethod(player, "getHandle"));
		return this
				.setEntityId((int) ReflectionAPI.invokeMethod(nmsPlayer, ReflectionAPI.getMethod(nmsPlayer, "getId")));
	}

	@Override
	public PacketBlockBreakAnimation setEntityId(MC_Player player)
	{
		return this.setEntityId(player.getEntityId());
	}

}
