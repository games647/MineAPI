package com.aol.w67clement.mineapi.nms.v1_8_R2.entity;

import net.minecraft.server.v1_8_R2.EntityCreature;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftCreature;
import org.bukkit.entity.Creature;

import com.aol.w67clement.mineapi.entity.MC_EntityCreature;

public class MC_EntityCreature_v1_8_R2 extends MC_Entity_v1_8_R2 implements
		MC_EntityCreature {

	private EntityCreature creature;
	
	public MC_EntityCreature_v1_8_R2(Creature entity) {
		super(entity);
		this.creature = ((CraftCreature) entity).getHandle();
	}

	@Override
	public void setAIDisabled(boolean ai) {
		this.creature.getDataWatcher().watch(15, Byte.valueOf((byte)(ai ? 1 : 0)));
	}

	@Override
	public boolean isAIDisabled() {
		return this.creature.ce();
	}

}
