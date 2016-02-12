package com.w67clement.mineapi.block;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import org.bukkit.Location;

/**
 * PacketBlockAction<br>
 * <b>Nms PacketPlayOutBlockAction</b><br>
 * Send a block action to the player! <br>
 * For more informations for this packet, view that: <a href="http://wiki.vg/Protocol#Block_Action">wiki.vg/Protocol#Block_Action</a>
 *
 * @author w67clement
 */
public abstract class PacketBlockAction extends PacketSender
{

    protected Location location;
    protected BlockAction action;
    protected int data;

    public PacketBlockAction(Location location, BlockAction action)
    {
        this(location, action, action.getData());
    }

    public PacketBlockAction(Location location, BlockAction action, int data)
    {
        this.location = location;
        this.action = action;
        this.data = data;
    }

    public PacketBlockAction(int x, int y, int z, BlockAction action)
    {
        this(new Location(null, x, y, z), action, action.getData());
    }

    public PacketBlockAction(int x, int y, int z, BlockAction action, int data)
    {
        this(new Location(null, x, y, z), action, data);
    }

    /**
     * Sets the location of the block who have the action.
     *
     * @param x X axis of the location.
     * @param y Y axis of the location.
     * @param z Z axis of the location.
     *
     * @return Instance.
     */
    public PacketBlockAction setLocation(int x, int y, int z)
    {
        this.location = new Location(null, x, y, z);
        return this;
    }

    /**
     * Gets the location of the block who have the action. <br>
     * <b>Warning:</b> <i>The world can is null!</i>
     *
     * @return Location of the block.
     */
    public Location getLocation()
    {
        return this.location;
    }

    /**
     * Sets the location of the block who have the action.
     *
     * @param location Location of the block.
     *
     * @return Instance.
     */
    public PacketBlockAction setLocation(Location location)
    {
        this.location = location;
        return this;
    }

    /**
     * Gets the action of the block.
     *
     * @return BlockAction enum constant.
     */
    public BlockAction getAction()
    {
        return this.action;
    }

    /**
     * Sets the action of the block.<br>
     * It's redefine the data if the action isn't for NoteBlocks.
     *
     * @param action Action for the block.
     *
     * @return Instance.
     */
    public PacketBlockAction setAction(BlockAction action)
    {
        this.action = action;
        if (!(action.getType() == BlockAction.BlockActionType.NOTE_BLOCK))
        {
            this.data = action.getData();
        }
        return this;
    }

    /**
     * Gets the data of the action for the block.
     *
     * @return Data of the action.
     */
    public int getData()
    {
        return this.data;
    }

    /**
     * Sets the data of the action for the block.
     *
     * @param data Data of the action.
     *
     * @return Instance.
     */
    public PacketBlockAction setData(int data)
    {
        this.data = data;
        return this;
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }
}
