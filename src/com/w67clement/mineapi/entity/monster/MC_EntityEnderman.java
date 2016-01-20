package com.w67clement.mineapi.entity.monster;

import com.w67clement.mineapi.entity.MC_EntityMonster;
import org.bukkit.entity.Enderman;
import org.bukkit.inventory.ItemStack;

public interface MC_EntityEnderman extends MC_EntityMonster
{

	public void setScreaming(boolean screaming);

	public boolean isScreaming();

	public void setCarriedBlock(ItemStack block);

	public ItemStack getCarriedBlock();

	public Enderman getHandle();

	public Object getMC_Handle();

}
