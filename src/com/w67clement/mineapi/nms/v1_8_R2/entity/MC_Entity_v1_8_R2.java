package com.w67clement.mineapi.nms.v1_8_R2.entity;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;

import com.w67clement.mineapi.entity.MC_Entity;

public class MC_Entity_v1_8_R2 implements MC_Entity
{

	private net.minecraft.server.v1_8_R2.Entity entity;

	public MC_Entity_v1_8_R2(Entity entity) {
		this.entity = ((CraftEntity) entity).getHandle();
	}

	@Override
	public int getEntityId()
	{
		return this.entity.getId();
	}

	@Override
	public void setCustomName(String customName)
	{
		this.entity.setCustomName(customName);
	}

	@Override
	public String getCustomName()
	{
		return this.entity.getCustomName();
	}

	@Override
	public boolean hasCustomName()
	{
		return this.entity.hasCustomName();
	}

	@Override
	public void setCustomNameVisible(boolean visible)
	{
		this.entity.setCustomNameVisible(visible);
	}

	@Override
	public boolean getCustomNameVisible()
	{
		return this.entity.getCustomNameVisible();
	}

	@Override
	public void setInvisible(boolean invisible)
	{
		this.entity.setInvisible(invisible);
	}

	@Override
	public boolean isInvisible()
	{
		return this.entity.isInvisible();
	}

	@Override
	public Entity getEntityHandle()
	{
		return this.entity.getBukkitEntity();
	}

	@Override
	public Object getMC_Handle()
	{
		return this.entity;
	}

}
