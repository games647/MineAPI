package com.aol.w67clement.mineapi.block;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.nms.PacketSender;

public interface PacketBlockBreakAnimation extends PacketSender {

	/**
	 * Gets the block location
	 * @return An bukkit's location object
	 */
	public Location getBlockLocation();
	
	/**
	 *  Set the block location
	 * @param loc The bukkit location object.
	 */
	public PacketBlockBreakAnimation setBlockLocation(Location loc);
	
	public int getEntityId();
	
	@Deprecated
	public PacketBlockBreakAnimation setEntityId(int id);
	
	public PacketBlockBreakAnimation setEntityId(Player player);
	
	public PacketBlockBreakAnimation setEntityId(MC_Player player);
	
	/**
	 *  Set the block location
	 * @param x X-Location
	 * @param y Y-Location
	 * @param z Z-Location
	 */
	public PacketBlockBreakAnimation setBlockLocation(int x, int y, int z);
	
	/**
	 *  Gets the destroy stage byte
	 */
	public byte getDestroyStage();
	
	/**
	 *  Set the destroy stage byte
	 * @param destroyStage 0–9 to set it, any other value to remove it
	 */
	public PacketBlockBreakAnimation setDestroyStage(byte destroyStage);
}
