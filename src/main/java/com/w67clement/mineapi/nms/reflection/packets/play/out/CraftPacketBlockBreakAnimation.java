package com.w67clement.mineapi.nms.reflection.packets.play.out;

import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.entity.player.MC_Player;
import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

public class CraftPacketBlockBreakAnimation extends PacketBlockBreakAnimation<Object>
{
    private static final Class<?> packetClass;
    private static final Field entityIdField;
    private static final Field blockPosField;
    private static final Field destroyStageField;

    static
    {
        packetClass = getNmsClass("PacketPlayOutBlockBreakAnimation");
        entityIdField = getField(packetClass, "a", true);
        blockPosField = getField(packetClass, "b", true);
        destroyStageField = getField(packetClass, "c", true);
    }

    public CraftPacketBlockBreakAnimation(Object packet)
    {
        super(packet);
    }

    public CraftPacketBlockBreakAnimation(MC_Player player, Location blockLocation, byte destroyStage)
    {
        this(player.getEntityId(), blockLocation, destroyStage);
    }

    public CraftPacketBlockBreakAnimation(MC_Player player, int x, int y, int z, byte destroyStage)
    {
        this(player.getEntityId(), new Location(Bukkit.getWorlds().get(0), x, y, z), destroyStage);
    }

    public CraftPacketBlockBreakAnimation(Player player, Location blockLocation, byte destroyStage)
    {
        this(player.getEntityId(), blockLocation, destroyStage);
    }

    public CraftPacketBlockBreakAnimation(Player player, int x, int y, int z, byte destroyStage)
    {
        this(player.getEntityId(), new Location(Bukkit.getWorlds().get(0), x, y, z), destroyStage);
    }

    public CraftPacketBlockBreakAnimation(int entityId, Location blockLocation, byte destroyStage)
    {
        super(SunUnsafe.newInstance(packetClass));
        setEntityId(entityId);
        setBlockLocation(blockLocation);
        setDestroyStage(destroyStage);
    }

    @Override
    public Location getBlockLocation()
    {
        return new BlockPositionWrapper(getValue(packet, blockPosField)).toLocation(null);
    }

    @Override
    public PacketBlockBreakAnimation setBlockLocation(Location loc)
    {
        setValue(packet, blockPosField, BlockPositionWrapper.fromLocation(loc).toBlockPosition());
        return this;
    }

    @Override
    public int getEntityId()
    {
        return getIntValue(packet, entityIdField);
    }

    @Override
    public PacketBlockBreakAnimation setEntityId(int id)
    {
        setValue(packet, entityIdField, id);
        return this;
    }

    @Override
    public byte getDestroyStage()
    {
        return (byte) getIntValue(packet, destroyStageField);
    }

    @Override
    public PacketBlockBreakAnimation setDestroyStage(byte destroyStage)
    {
        setValue(packet, destroyStageField, (int) destroyStage);
        return this;
    }

    @Override
    public void send(Player player)
    {
        // Send the packet.
        NmsClass.sendPacket(player, this.getHandle());
    }

}
