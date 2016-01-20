package com.w67clement.mineapi.world;

import com.w67clement.mineapi.nms.PacketSender;
import org.bukkit.World;

/**
 * The main interface for the world's interface packet!
 * 
 * @author w67clement
 * @version 1.0
 */
public abstract class WorldPacket extends PacketSender
{

	protected World world;

	public World getWorld()
	{
		return this.world;
	}

	public String getWorldName()
	{
		return this.world.getName();
	}

}

// End of WorldPacket interface.