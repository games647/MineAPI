package com.w67clement.mineapi.block;

import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class PacketBlockBreakAnimation<T> extends PacketSender<T>
{
    public PacketBlockBreakAnimation(T packet)
    {
        super(packet);
    }

    /**
     * Gets the block location
     *
     * @return An bukkit's location object
     */
    public abstract Location getBlockLocation();

    /**
     * Set the block location
     *
     * @param loc The bukkit location object.
     *
     * @return Instance.
     */
    public abstract PacketBlockBreakAnimation setBlockLocation(Location loc);

    public abstract int getEntityId();

    public PacketBlockBreakAnimation setEntityId(Player player)
    {
        return this.setEntityId(player.getEntityId());
    }

    public PacketBlockBreakAnimation setEntityId(MC_Player player)
    {
        return this.setEntityId(player.getEntityId());
    }

    public abstract PacketBlockBreakAnimation setEntityId(int id);

    /**
     * Set the block location
     *
     * @param x X-Location
     * @param y Y-Location
     * @param z Z-Location
     *
     * @return Instance.
     */
    public PacketBlockBreakAnimation setBlockLocation(int x, int y, int z)
    {
        return this.setBlockLocation(new Location(null, x, y, z));
    }

    /**
     * Gets the destroy stage byte
     *
     * @return Destroy stage.
     */
    public abstract byte getDestroyStage();

    /**
     * Set the destroy stage byte
     *
     * @param destroyStage 0–9 to set it, any other value to remove it.
     *
     * @return Instance.
     */
    public abstract PacketBlockBreakAnimation setDestroyStage(byte destroyStage);

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

}
