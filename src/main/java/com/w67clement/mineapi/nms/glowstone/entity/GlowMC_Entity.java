package com.w67clement.mineapi.nms.glowstone.entity;

import com.w67clement.mineapi.entity.MC_Entity;
import net.glowstone.entity.GlowEntity;
import org.bukkit.entity.Entity;

public class GlowMC_Entity implements MC_Entity
{
	private GlowEntity entity;

	public GlowMC_Entity(Entity entity) {
		this.entity = (GlowEntity) entity;
	}

	@Override
	public int getEntityId()
	{
		return this.entity.getEntityId();
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
		return this.getCustomName() != null || this.getCustomName().isEmpty();
	}

	@Override
	public void setCustomNameVisible(boolean visible)
	{
		this.entity.setCustomNameVisible(visible);
	}

	@Override
	public boolean getCustomNameVisible()
	{
		return this.entity.isCustomNameVisible();
	}

	@Override
	public void setInvisible(boolean invisible)
	{}

	@Override
	public boolean isInvisible()
	{
		return false;
	}

	@Override
	public void setNoClip(boolean noClip)
	{}

	@Override
	public boolean hasNoClip()
	{
		return false;
	}

	@Override
	public void setSize(float width, float length)
	{}

	@Override
	public Entity getEntityHandle()
	{
		return entity;
	}

	@Override
	public Object getMC_Handle()
	{
		return entity;
	}

}
