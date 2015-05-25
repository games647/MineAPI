package com.aol.w67clement.mineapi.world;

import org.bukkit.World;

import com.aol.w67clement.mineapi.nms.PacketSender;

/**
 *  The main interface for the world's interface packet!
 * @author 67clement
 * @version 1.0
 */
public interface WorldPacket extends PacketSender {

	public World getWorld();
	
	public String getWorldName();
	
}

//End of WorldPacket interface.