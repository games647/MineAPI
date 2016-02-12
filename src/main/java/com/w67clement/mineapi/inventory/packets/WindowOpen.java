package com.w67clement.mineapi.inventory.packets;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import org.bukkit.inventory.Inventory;

public abstract class WindowOpen extends PacketSender
{
    protected int id;
    protected WindowType type;
    protected String title;
    protected int size;
    protected int horseId = 0;

    public WindowOpen(int id, WindowType type, String title, int size)
    {
        this.id = id;
        this.type = type;
        this.title = title;
        this.size = size;
    }

    public WindowOpen(int id, WindowType type, String title, int size, int horseId)
    {
        this(id, type, title, size);
        this.horseId = horseId;
    }

    public WindowOpen(int id, Inventory inventory)
    {
        this.id = id;
        this.type = WindowType.getByInventory(inventory.getType());
        if (type == null)
        {
            throw new IllegalArgumentException("Type of the inventory was not recognized!");
        }
        this.title = inventory.getTitle();
        this.size = inventory.getSize();
    }

    public int getId()
    {
        return this.id;
    }

    public WindowOpen setId(int id)
    {
        this.id = id;
        return this;
    }

    public WindowType getWindowType()
    {
        return this.type;
    }

    public WindowOpen setWindowType(WindowType type)
    {
        this.type = type;
        return this;
    }

    /**
     * Gets the title of the opened Window.
     * @return Title in Json Format.
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * Sets the title of the opened Window.
     * @param title Title in Json Format
     * @return Instance of WindowOpen.
     */
    public WindowOpen setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public int getSize()
    {
        return this.size;
    }

    public WindowOpen setSize(int size)
    {
        this.size = size;
        return this;
    }

    public int getHorseId()
    {
        return this.horseId;
    }

    public WindowOpen setHorseId(int horse_id)
    {
        this.horseId = horse_id;
        return this;
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

}
