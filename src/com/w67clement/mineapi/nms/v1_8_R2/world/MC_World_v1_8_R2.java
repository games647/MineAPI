package com.w67clement.mineapi.nms.v1_8_R2.world;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;

import com.w67clement.mineapi.world.MC_World;

import net.minecraft.server.v1_8_R2.Block;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.Chunk;
import net.minecraft.server.v1_8_R2.IBlockData;
import net.minecraft.server.v1_8_R2.WorldServer;

public class MC_World_v1_8_R2 extends MC_World
{

	private WorldServer mc_world;

	public MC_World_v1_8_R2(World world) {
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
