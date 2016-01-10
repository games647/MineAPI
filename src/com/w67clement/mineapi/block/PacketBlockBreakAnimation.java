package com.w67clement.mineapi.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.nms.PacketSender;

public abstract class PacketBlockBreakAnimation extends PacketSender
{

	protected int entityId;
	protected Location blockLocation;
	protected byte destroyStage;

	public PacketBlockBreakAnimation(MC_Player player, Location blockLocation,
			byte destroyStage) {
		this.entityId = player.getEntityId();
		this.blockLocation = blockLocation;
		this.destroyStage = destroyStage;
	}

	public PacketBlockBreakAnimation(MC_Player player, int x, int y, int z,
			byte destroyStage) {
		this(player, new Location(Bukkit.getWorlds().get(0), x, y, z),
				destroyStage);
	}

	public PacketBlockBreakAnimation(Player player, Location blockLocation,
			byte destroyStage) {
		this.entityId = player.getEntityId();
		this.blockLocation = blockLocation;
		this.destroyStage = destroyStage;
	}

	public PacketBlockBreakAnimation(Player player, int x, int y, int z,
			byte destroyStage) {
		this(player, new Location(Bukkit.getWorlds().get(0), x, y, z),
				destroyStage);
	}

	/**
	 * Gets the block location
	 * 
	 * @return An bukkit's location object
	 */
	public Location getBlockLocation()
	{
		return this.blockLocation;
	}

	/**
	 * Set the block location
	 * 
	 * @param loc
	 *            The bukkit location object.
	 */
	public PacketBlockBreakAnimation setBlockLocation(Location loc)
	{
		this.blockLocation = loc;
		return this;
	}

	public int getEntityId()
	{
		return this.entityId;
	}

	@Deprecated
	public PacketBlockBreakAnimation setEntityId(int id)
	{
		this.entityId = id;
		return this;
	}

	public PacketBlockBreakAnimation setEntityId(Player player)
	{
		Object nmsPlayer = ReflectionAPI.invokeMethod(player,
				ReflectionAPI.getMethod(player, "getHandle"));
		return this.setEntityId((int) ReflectionAPI.invokeMethod(nmsPlayer,
				ReflectionAPI.getMethod(nmsPlayer, "getId")));
	}

	public PacketBlockBreakAnimation setEntityId(MC_Player player)
	{
		return this.setEntityId(player.getEntityId());
	}

	/**
	 * Set the block location
	 * 
	 * @param x
	 *            X-Location
	 * @param y
	 *            Y-Location
	 * @param z
	 *            Z-Location
	 */
	public PacketBlockBreakAnimation setBlockLocation(int x, int y, int z)
	{
		this.blockLocation = new Location(null, x, y, z);
		return this;
	}

	/**
	 * Gets the destroy stage byte
	 */
	public byte getDestroyStage()
	{
		return this.destroyStage;
	}

	/**
	 * Set the destroy stage byte
	 * 
	 * @param destroyStage
	 *            0–9 to set it, any other value to remove it
	 */
	public PacketBlockBreakAnimation setDestroyStage(byte destroyStage)
	{
		this.destroyStage = destroyStage;
		return this;
	}
}
