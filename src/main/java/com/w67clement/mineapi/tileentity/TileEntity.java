package com.w67clement.mineapi.tileentity;

import org.bukkit.Location;
import org.bukkit.World;

public interface TileEntity {
	
	public World getWorld();

	public Location getLocation();
	
	public int getX();
	
	public int getY();
	
	public int getZ();
}
