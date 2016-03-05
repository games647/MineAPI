package com.w67clement.mineapi.nms.v1_9_R1.entity;

import com.w67clement.mineapi.entity.animals.MC_Pig;
import net.minecraft.server.v1_9_R1.EntityPig;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPig;
import org.bukkit.entity.Pig;

/**
 * Created by w67clement on 01/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class MC_Pig_v1_9_R1 extends MC_EntityAgeable_v1_9_R1 implements MC_Pig
{
    private EntityPig pig;

    public MC_Pig_v1_9_R1(Pig pig)
    {
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
