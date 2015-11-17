package com.w67clement.mineapi.nms.v1_8_R1.entity;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEnderman;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.entity.Enderman;
import org.bukkit.inventory.ItemStack;

import com.w67clement.mineapi.entity.monster.MC_EntityEnderman;

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.EntityEnderman;

public class MC_EntityEnderman_v1_8_R1 extends MC_EntityCreature_v1_8_R1 implements MC_EntityEnderman
{

	private EntityEnderman enderman;

	public MC_EntityEnderman_v1_8_R1(Enderman entity) {
		super(entity);
		this.enderman = ((CraftEnderman) entity).getHandle();
	}

	@Override
	public void setScreaming(boolean screaming)
	{
		this.enderman.a(screaming);
	}

	@Override
	public boolean isScreaming()
	{
		return this.enderman.cm();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setCarriedBlock(ItemStack block)
	{
		this.enderman.setCarried(Block.getById(block.getType().getId()).getBlockData());
	}

	@Override
	public ItemStack getCarriedBlock()
	{
		return CraftItemStack
				.asBukkitCopy(new net.minecraft.server.v1_8_R1.ItemStack(this.enderman.getCarried().getBlock()));
	}

	@Override
	public Enderman getHandle()
	{
		return (Enderman) this.enderman.getBukkitEntity();
	}

	@Override
	public Object getMC_Handle()
	{
		return this.enderman;
	}

}
