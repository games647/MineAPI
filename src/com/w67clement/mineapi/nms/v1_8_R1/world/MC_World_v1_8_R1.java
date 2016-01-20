package com.w67clement.mineapi.nms.v1_8_R1.world;

import com.w67clement.mineapi.world.MC_World;
import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Chunk;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.WorldServer;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;

public class MC_World_v1_8_R1 extends MC_World
{

	private WorldServer mc_world;
	
	public MC_World_v1_8_R1(World world) {
		super(world);
		this.mc_world = ((CraftWorld) world).getHandle();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBlock(int x, int y, int z, Material material, byte data)
	{
		Chunk mc_chunk = this.mc_world.getChunkAt(x >> 4, z >> 4);
		BlockPosition pos = new BlockPosition(x, y, z);
		int combined = material.getId() + (data << 12);
		IBlockData block = Block.getByCombinedId(combined);
		mc_chunk.a(pos, block);
	}

}
