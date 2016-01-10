package com.w67clement.mineapi.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public abstract class MC_World
{

	protected World world;

	public MC_World(World world) {
		this.world = world;
	}

	/**
	 * Sets a block (faster) in the world.
	 * 
	 * @param location
	 *            Position of the block.
	 * @param material
	 *            Material of the block.
	 */
	public void setBlock(Location location, Material material)
	{
		this.setBlock(location, material, (byte) 0);
	}

	/**
	 * Sets a block (faster) in the world.
	 * 
	 * @param location
	 *            Position of the block.
	 * @param material
	 *            Material of the block.
	 * @param data
	 *            Data of the block.
	 */
	public void setBlock(Location location, Material material, byte data)
	{
		this.setBlock(location.getBlockX(), location.getBlockY(),
				location.getBlockZ(), material, data);
	}

	/**
	 * Sets a block (faster) in the world.
	 * 
	 * @param x
	 *            X position.
	 * @param y
	 *            Y position.
	 * @param z
	 *            Z position.
	 * @param material
	 *            Material of the block.
	 */
	public void setBlock(int x, int y, int z, Material material)
	{
		this.setBlock(x, y, z, material, (byte) 0);
	}

	/**
	 * Sets a block (faster) in the world.
	 * 
	 * @param x
	 *            X position.
	 * @param y
	 *            Y position.
	 * @param z
	 *            Z position.
	 * @param material
	 *            Material of the block.
	 * @param data
	 *            Data of the block.
	 */
	public abstract void setBlock(int x, int y, int z, Material material,
			byte data);

}
