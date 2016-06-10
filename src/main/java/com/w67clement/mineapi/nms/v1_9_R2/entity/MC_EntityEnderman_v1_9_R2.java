package com.w67clement.mineapi.nms.v1_9_R2.entity;

import com.w67clement.mineapi.entity.monster.MC_EntityEnderman;
import net.minecraft.server.v1_9_R2.Block;
import net.minecraft.server.v1_9_R2.EntityEnderman;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEnderman;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Enderman;
import org.bukkit.inventory.ItemStack;

/**
 * Created by w67clement on 01/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class MC_EntityEnderman_v1_9_R2 extends MC_EntityCreature_v1_9_R2 implements MC_EntityEnderman
{
    private EntityEnderman enderman;

    public MC_EntityEnderman_v1_9_R2(Enderman entity) {
        super(entity);
        this.enderman = ((CraftEnderman) entity).getHandle();
    }

    @Override
    public boolean isScreaming()
    {
        throw new UnsupportedOperationException("Method removed.");
    }

    @Override
    public void setScreaming(boolean screaming)
    {
        throw new UnsupportedOperationException("Method removed.");
    }

    @Override
    public ItemStack getCarriedBlock()
    {
        return CraftItemStack
                .asBukkitCopy(new net.minecraft.server.v1_9_R2.ItemStack(this.enderman.getCarried().getBlock()));
    }

    @Override
    public void setCarriedBlock(ItemStack block)
    {
        this.enderman.setCarried(Block.getById(block.getType().getId()).getBlockData());
    }

    @Override
    public Enderman getHandle()
    {
        return (Enderman) this.enderman.getBukkitEntity();
    }

    @Override
    public Object getMC_Handle()
    {
        return this.enderman;
    }
}
