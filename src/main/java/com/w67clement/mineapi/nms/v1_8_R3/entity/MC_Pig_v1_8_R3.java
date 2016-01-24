package com.w67clement.mineapi.nms.v1_8_R3.entity;

import com.w67clement.mineapi.entity.animals.MC_Pig;
import net.minecraft.server.v1_8_R3.EntityPig;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPig;
import org.bukkit.entity.Pig;

public class MC_Pig_v1_8_R3 extends MC_EntityAgeable_v1_8_R3 implements MC_Pig
{

	private EntityPig pig;

	public MC_Pig_v1_8_R3(Pig pig) {
		super(pig);
		this.pig = ((CraftPig) pig).getHandle();
	}

	@Override
	public boolean hasSaddle()
	{
		return this.pig.hasSaddle();
	}

	@Override
	public void setSaddle(boolean flag)
	{
		this.pig.setSaddle(flag);
	}

	@Override
	public Object getMC_Handle()
	{
		return this.pig;
	}

	@Override
	public Pig getHandle()
	{
		return (Pig) this.pig.getBukkitEntity();
	}

}
