package com.w67clement.mineapi.nms.v1_8_R2.entity;

import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.MC_EntityLiving;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.server.v1_8_R2.AxisAlignedBB;
import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

public class MC_EntityLiving_v1_8_R2 extends MC_Entity_v1_8_R2 implements MC_EntityLiving
{

	private EntityLiving livingEntity;

	public MC_EntityLiving_v1_8_R2(LivingEntity entity) {
		super(entity);
		this.livingEntity = ((CraftLivingEntity) entity).getHandle();
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
		this.livingEntity.setHealth((float) health);
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

		this.livingEntity.getAttributeInstance(GenericAttributes.maxHealth).setValue(health);

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
		LivingEntity bukkitEntity = (LivingEntity) this.getEntityHandle();
		@SuppressWarnings("deprecation")
		Location loc = bukkitEntity.getTargetBlock((HashSet<Byte>) null, 100).getLocation();
		AxisAlignedBB aabb = new AxisAlignedBB(loc.getX(), loc.getY(), loc.getZ(), loc.getX(), loc.getY(), loc.getZ())
				.grow(1, 1, 1);
		List<Entity> nmsEntities = ((CraftWorld) loc.getWorld()).getHandle().getEntities(this.livingEntity, aabb);
		List<MC_Entity> entities = new ArrayList<MC_Entity>();
		for (Entity entity : nmsEntities)
		{
			entities.add(new MC_Entity_v1_8_R2(entity.getBukkitEntity()));
		}
		return entities;
	}

}
