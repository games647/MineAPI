package com.w67clement.mineapi.nms.v1_8_R1.entity;

import com.w67clement.mineapi.entity.MC_Entity;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class MC_Entity_v1_8_R1 implements MC_Entity
{

	private net.minecraft.server.v1_8_R1.Entity entity;

	public MC_Entity_v1_8_R1(Entity entity) {
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
	public void setNoClip(boolean noClip)
	{
		this.entity.T = noClip;
	}

	@Override
	public boolean hasNoClip()
	{
		return this.entity.T;
	}

	@Override
	public void setSize(float width, float length)
	{
		this.entity.a(width, length);
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
