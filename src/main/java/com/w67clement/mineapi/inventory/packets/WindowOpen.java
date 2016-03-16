package com.w67clement.mineapi.inventory.packets;

import com.google.gson.JsonObject;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;

public abstract class WindowOpen<T> extends PacketSender<T>
{
    public WindowOpen(T packet) {
        super(packet);
    }

    public abstract int getId();

    public abstract WindowOpen setId(int id);

    public abstract WindowType getWindowType();

    public abstract WindowOpen setWindowType(WindowType type);

    /**
     * Gets the title of the opened Window.
     *
     * @return Title in Json Format.
     */
    public abstract String getTitle();

    /**
     * Sets the title of the opened Window.
     *
     * @param title Title in Json Format
     *
     * @return Instance of WindowOpen.
     */
    public abstract WindowOpen setTitle(String title);

    /**
     * Sets the title of the opened Window.
     *
     * @param title Title in Text format.
     *
     * @return Instance of WindowOpen.
     */
    public WindowOpen setTitleText(String title)
    {
        JsonObject json = new JsonObject();
        json.addProperty("text", title);
        setTitle(json.toString());
        return this;
    }

    public abstract int getSize();

    public abstract WindowOpen setSize(int size);

    public abstract int getHorseId();

    public abstract WindowOpen setHorseId(int horseId);

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

}
