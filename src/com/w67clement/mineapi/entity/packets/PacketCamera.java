package com.w67clement.mineapi.entity.packets;

import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import org.bukkit.entity.Entity;

public abstract class PacketCamera extends PacketSender
{

    protected int entityId;

    public PacketCamera(int entityId)
    {
        this.entityId = entityId;
    }

    public PacketCamera(Entity entity)
    {
        this.entityId = entity.getEntityId();
    }

    public PacketCamera(MC_Entity entity)
    {
        this.entityId = entity.getEntityId();
    }

    /**
     * Gets the entity's Id.
     *
     * @return Entity's Id.
     */
    public int getEntityId()
    {
        return this.entityId;
    }

    /**
     * Sets the entity's Id.
     *
     * @param id Entity's Id.
     *
     * @return Instance.
     */
    public PacketCamera setEntityId(int id)
    {
        this.entityId = id;
        return this;
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

}
