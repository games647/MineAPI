package com.w67clement.mineapi.nms.glowstone.entity;

import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.MC_EntityLiving;
import java.util.List;
import net.glowstone.entity.GlowLivingEntity;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.LivingEntity;

public class GlowMC_EntityLiving extends GlowMC_Entity
		implements MC_EntityLiving
{

	private GlowLivingEntity livingEntity;

	public GlowMC_EntityLiving(LivingEntity entity) {
		super(entity);
		this.livingEntity = (GlowLivingEntity) entity;
	}

	@Override
	public void setAIDisabled(boolean ai)
	{}

	@Override
	public boolean isAIDisabled()
	{
		return false;
	}

	@Override
	public void setHealth(double health)
	{
		this.livingEntity.setHealth(health);
	}

	@Override
	public double getHealth()
	{
		return this.livingEntity.getHealth();
	}

	@Override
	public void setMaxHealth(double health)
	{
		Validate.isTrue(health > 0.0D, "Max health must be greater than 0");

		this.livingEntity.setMaxHealth(health);

		if (getHealth() > health)
		{
			this.setHealth(health);
		}
	}

	@Override
	public double getMaxHealth()
	{
		return this.livingEntity.getMaxHealth();
	}

	@Override
	public List<MC_Entity> getTargetEntities()
	{
		return null;
	}

}
