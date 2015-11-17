package com.w67clement.mineapi.world;

import org.bukkit.World;

import com.w67clement.mineapi.nms.PacketSender;

/**
 * The main interface for the world's interface packet!
 * 
 * @author w67clement
 * @version 1.0
 */
public interface WorldPacket extends PacketSender
{

	public World getWorld();

	public String getWorldName();

}

// End of WorldPacket interface.