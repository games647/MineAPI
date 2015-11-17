package com.w67clement.mineapi.nms.v1_8_R1.entity;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftAgeable;
import org.bukkit.entity.Ageable;

import com.w67clement.mineapi.entity.MC_EntityAgeable;

import net.minecraft.server.v1_8_R1.EntityAgeable;

public class MC_EntityAgeable_v1_8_R1 extends MC_EntityCreature_v1_8_R1 implements MC_EntityAgeable
{

	private EntityAgeable entityAgeable;

	public MC_EntityAgeable_v1_8_R1(Ageable entity) {
		super(entity);
		this.entityAgeable = ((CraftAgeable) entity).getHandle();
	}

	@Override
	public int getAge()
	{
		return this.entityAgeable.getAge();
	}

	@Override
	public void setAge(int age)
	{
		this.entityAgeable.setAge(age);
	}

	@Override
	public boolean isBaby()
	{
		return this.entityAgeable.isBaby();
	}

	@Override
	public boolean isAdult()
	{
		return !this.entityAgeable.isBaby();
	}

	@Override
	public void setAgeLocked(boolean lock)
	{
		this.entityAgeable.ageLocked = lock;
	}

	@Override
	public boolean ageIsLocked()
	{
		return this.entityAgeable.ageLocked;
	}

	@Override
	public void setSize(float f, float f1)
	{
		this.entityAgeable.a(f, f1);
	}

}
