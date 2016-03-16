package com.w67clement.mineapi.nms.reflection.play_out.block;

import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.block.BlockAction;
import com.w67clement.mineapi.block.PacketBlockAction;
import java.lang.reflect.Constructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * CraftPacketBlockAction is implements the methods for send the packet.
 *
 * @author w67clement
 */
public class CraftPacketBlockAction extends PacketBlockAction
{
    private static final Class<?> blockClass;
    private static final Class<?> blocksClass;
    private static final Class<?> blockPositionClass;
    private static final Class<?> packetClass;
    private static final Object noteblock;
    private static final Object piston;
    private static final Object chest;

    static
    {
            blockClass = getNmsClass("Block");
            blocksClass = getNmsClass("Blocks");
            blockPositionClass = getNmsClass("BlockPosition");
            packetClass = getNmsClass("PacketPlayOutBlockAction");

            noteblock = getValue(null, getField(blocksClass, "NOTEBLOCK", true));
            piston = getValue(null, getField(blocksClass, "PISTON", true));
            chest = getValue(null, getField(blocksClass, "CHEST", true));
    }

    public CraftPacketBlockAction(Location location, BlockAction action)
    {
        this(location, action, action.getData());
    }

    public CraftPacketBlockAction(Location location, BlockAction action, int data)
    {
        super(null);
        setLocation(location).setAction(action).setData(data);
    }

    public CraftPacketBlockAction(int x, int y, int z, BlockAction action)
    {
        this(new Location(null, x, y, z), action, action.getData());
    }

    public CraftPacketBlockAction(int x, int y, int z, BlockAction action, int data)
    {
        this(new Location(null, x, y, z), action, data);
    }

    @Override
    public void send(Player player)
    {
        // Send the packet
        NmsClass.sendPacket(player, this.getHandle());
    }

    private Object constructPacket_Bukkit()
    {
        // Constructing the packet
        Constructor<?> constructor = getConstructor(packetClass, blockPositionClass, blockClass, int.class, int.class);
        Object block = null;

        switch (action.getType())
        {
            case CHEST:
                block = chest;
                break;
            case PISTON:
                block = piston;
                break;
            case NOTE_BLOCK:
                block = noteblock;
                break;
            default:
                break;
        }

        return newInstance(constructor, BlockPositionWrapper.fromLocation(location).toBlockPosition(), block, this.action.getAction(), this.data);
    }

}
