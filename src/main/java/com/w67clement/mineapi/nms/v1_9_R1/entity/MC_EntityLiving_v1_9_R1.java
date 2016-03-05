package com.w67clement.mineapi.nms.v1_9_R1.entity;

import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.MC_EntityLiving;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.server.v1_9_R1.AxisAlignedBB;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

/**
 * Created by w67clement on 01/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class MC_EntityLiving_v1_9_R1 extends MC_Entity_v1_9_R1 implements MC_EntityLiving
{
    private net.minecraft.server.v1_9_R1.EntityLiving entityLiving;

    public MC_EntityLiving_v1_9_R1(LivingEntity entity)
    {
        super(entity);
        this.entityLiving = ((CraftLivingEntity) entity).getHandle();
    }

    @Override
    public boolean isAIDisabled()
    {
        return false;
    }

    @Override
    public void setAIDisabled(boolean ai)
    {
    }

    @Override
    public double getHealth()
    {
        return this.entityLiving.getHealth();
    }

    @Override
    public void setHealth(double health)
    {
        this.entityLiving.setHealth((float) health);
    }

    @Override
    public double getMaxHealth()
    {
        return this.entityLiving.getMaxHealth();
    }

    @Override
    public void setMaxHealth(double health)
    {
        Validate.isTrue(health > 0.0D, "Max health must be greater than 0");

        this.entityLiving.getAttributeInstance(GenericAttributes.maxHealth).setValue(health);

        if (getHealth() > health)
        {
            this.setHealth(health);
        }
    }

    @Override
    public List<MC_Entity> getTargetEntities()
    {
        LivingEntity bukkitEntity = (LivingEntity) this.getEntityHandle();
        Location loc = bukkitEntity.getTargetBlock((HashSet<Byte>) null, 100).getLocation();
        AxisAlignedBB aabb = new AxisAlignedBB(loc.getX(), loc.getY(), loc.getZ(), loc.getX(), loc.getY(), loc.getZ()).grow(1, 1, 1);
        List<Entity> nmsEntities = ((CraftWorld) loc.getWorld()).getHandle().getEntities(this.entityLiving, aabb);
        return nmsEntities.stream().map(entity -> new MC_Entity_v1_9_R1(entity.getBukkitEntity())).collect(Collectors.toList());
    }
}
