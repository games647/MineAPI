package com.aol.w67clement.mineapi.nms.v1_8_R3.entity;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.EntityEnderman;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderman;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Enderman;
import org.bukkit.inventory.ItemStack;

import com.aol.w67clement.mineapi.entity.monster.MC_EntityEnderman;

public class MC_EntityEnderman_v1_8_R3 extends MC_EntityCreature_v1_8_R3
		implements MC_EntityEnderman {

	private EntityEnderman enderman;

	public MC_EntityEnderman_v1_8_R3(Enderman entity) {
		super(entity);
		this.enderman = ((CraftEnderman) entity).getHandle();
	}

	@Override
	public void setScreaming(boolean screaming) {
		this.enderman.a(screaming);
	}

	@Override
	public boolean isScreaming() {
		return this.enderman.co();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setCarriedBlock(ItemStack block) {
		this.enderman.setCarried(Block.getById(block.getType().getId())
				.getBlockData());
	}

	@Override
	public ItemStack getCarriedBlock() {
		return CraftItemStack
				.asBukkitCopy(new net.minecraft.server.v1_8_R3.ItemStack(
						this.enderman.getCarried().getBlock()));
	}

	@Override
	public Enderman getHandle() {
		return (Enderman) this.enderman.getBukkitEntity();
	}

	@Override
	public Object getMC_Handle() {
		return this.enderman;
	}

}
