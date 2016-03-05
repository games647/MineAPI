package com.w67clement.mineapi.nms.v1_9_R1.entity;

import com.w67clement.mineapi.entity.MC_EntityAgeable;
import net.minecraft.server.v1_9_R1.EntityAgeable;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftAgeable;
import org.bukkit.entity.Ageable;

/**
 * Created by w67clement on 01/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class MC_EntityAgeable_v1_9_R1 extends MC_EntityCreature_v1_9_R1 implements MC_EntityAgeable
{
    private EntityAgeable entityAgeable;

    public MC_EntityAgeable_v1_9_R1(Ageable entity)
    {
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
        this.entityAgeable.setSize(f, f1);
    }
}
