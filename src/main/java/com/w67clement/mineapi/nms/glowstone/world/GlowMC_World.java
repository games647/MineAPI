package com.w67clement.mineapi.nms.glowstone.world;

import com.w67clement.mineapi.world.MC_World;
import net.glowstone.GlowWorld;
import org.bukkit.Material;
import org.bukkit.World;

public class GlowMC_World extends MC_World
{

	private GlowWorld glow_world;

	public GlowMC_World(World world) {
		super(world);
		this.glow_world = (GlowWorld) world;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBlock(int x, int y, int z, Material material, byte data)
	{
		this.glow_world.getBlockAt(x, y, z).setTypeIdAndData(material.getId(),
				data, true);
	}

}
