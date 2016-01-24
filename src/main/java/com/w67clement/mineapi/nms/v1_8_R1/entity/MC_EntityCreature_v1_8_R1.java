package com.w67clement.mineapi.nms.v1_8_R1.entity;

import com.w67clement.mineapi.entity.MC_EntityCreature;
import net.minecraft.server.v1_8_R1.EntityCreature;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftCreature;
import org.bukkit.entity.Creature;

public class MC_EntityCreature_v1_8_R1 extends MC_EntityLiving_v1_8_R1 implements MC_EntityCreature
{

	private EntityCreature creature;

	public MC_EntityCreature_v1_8_R1(Creature entity) {
		super(entity);
		this.creature = ((CraftCreature) entity).getHandle();
	}

	@Override
	public void setAIDisabled(boolean ai)
	{
		this.creature.getDataWatcher().watch(15, Byte.valueOf((byte) (ai ? 1 : 0)));
	}

	@Override
	public boolean isAIDisabled()
	{
		return this.creature.cd();
	}

}
