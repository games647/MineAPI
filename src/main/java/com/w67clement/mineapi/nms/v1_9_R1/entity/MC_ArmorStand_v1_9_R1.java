package com.w67clement.mineapi.nms.v1_9_R1.entity;

import com.w67clement.mineapi.entity.others.MC_ArmorStand;
import net.minecraft.server.v1_9_R1.EntityArmorStand;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

/**
 * Created by w67clement on 01/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class MC_ArmorStand_v1_9_R1 extends MC_EntityLiving_v1_9_R1 implements MC_ArmorStand
{
    private EntityArmorStand armorStand;

    public MC_ArmorStand_v1_9_R1(ArmorStand entity)
    {
        super(entity);
        this.armorStand = ((CraftArmorStand) entity).getHandle();
    }

    @Override
    public boolean isSmall()
    {
        return this.armorStand.isSmall();
    }

    @Override
    public void setSmall(boolean small)
    {
        this.armorStand.setSmall(small);
    }

    @Override
    public boolean hasBasePlate()
    {
        return this.armorStand.hasBasePlate();
    }

    @Override
    public void setBasePlate(boolean basePlate)
    {
        this.armorStand.setBasePlate(basePlate);
    }

    @Override
    public EulerAngle getBodyPose()
    {
        return this.getArmorHandle().getBodyPose();
    }

    @Override
    public void setBodyPose(EulerAngle bodyPose)
    {
        this.getArmorHandle().setBodyPose(bodyPose);
    }

    @Override
    public EulerAngle getLeftArmPose()
    {
        return this.getArmorHandle().getLeftArmPose();
    }

    @Override
    public void setLeftArmPose(EulerAngle leftArmPose)
    {
        this.getArmorHandle().setLeftArmPose(leftArmPose);
    }

    @Override
    public EulerAngle getRightArmPose()
    {
        return this.getArmorHandle().getRightArmPose();
    }

    @Override
    public void setRightArmPose(EulerAngle rightArmPose)
    {
        this.getArmorHandle().setRightArmPose(rightArmPose);
    }

    @Override
    public EulerAngle getLeftLegPose()
    {
        return this.getArmorHandle().getLeftLegPose();
    }

    @Override
    public void setLeftLegPose(EulerAngle leftLegPose)
    {
        this.getArmorHandle().setLeftLegPose(leftLegPose);
    }

    @Override
    public EulerAngle getRightLegPose()
    {
        return this.getArmorHandle().getRightLegPose();
    }

    @Override
    public void setRightLegPose(EulerAngle rightLegPose)
    {
        this.getArmorHandle().setRightLegPose(rightLegPose);
    }

    @Override
    public EulerAngle getHeadPose()
    {
        return this.getArmorHandle().getHeadPose();
    }

    @Override
    public void setHeadPose(EulerAngle headPose)
    {
        this.getArmorHandle().setHeadPose(headPose);
    }

    @Override
    public boolean hasGravity()
    {
        return this.armorStand.hasGravity();
    }

    @Override
    public void setGravity(boolean gravity)
    {
        this.armorStand.setGravity(gravity);
    }

    @Override
    public boolean hasArms()
    {
        return this.armorStand.hasArms();
    }

    @Override
    public void setArms(boolean arms)
    {
        this.armorStand.setArms(arms);
    }

    @Override
    public boolean hasMarker()
    {
        return this.armorStand.isMarker();
    }

    @Override
    public void setMarker(boolean marker)
    {
        this.armorStand.setMarker(marker);
    }

    @Override
    public ArmorStand getArmorHandle()
    {
        return (ArmorStand) this.armorStand.getBukkitEntity();
    }

    @Override
    public Object getMC_Handle()
    {
        return this.armorStand;
    }
}
