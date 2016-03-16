package com.w67clement.mineapi.message;

import com.google.gson.JsonParser;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;

/**
 * Packet for chat message, used by ActionBarMessage and FancyMessage.
 *
 * @author w67clement
 * @version MineAPI v2.2.0 (Event system v2)
 */
public abstract class PacketChat<T> extends PacketSender<T>
{
    protected static final JsonParser parser = new JsonParser();
    protected String json;
    protected byte data;

    public PacketChat(T packet)
    {
        super(packet);
    }

    /**
     * Gets the content text in Json.
     *
     * @return Text in Json.
     */
    public abstract String getContent();

    /**
     * Gets the data of the message, default is 1. <br>
     * The data have <b>2 possible</b> values: <br>
     * <ul>
     * <li>1: Display content in chat.</li>
     * <li>2: Display content in ActionBar.</li>
     * </ul>
     *
     * @return Data
     */
    public abstract byte getData();

    /**
     * Sets the data of the message, default is 1. <br>
     * The data have <b>2 possible</b> values: <br>
     * <ul>
     * <li>1: Display content in chat.</li>
     * <li>2: Display content in ActionBar.</li>
     * </ul>
     * <p>
     * <b>If you give too big value, it set to 2.</b> <br>
     * <b>If you give too small value, it set to 1.</b> <br>
     *
     * @param data Data of the message and it is 1 or 2.
     *
     * @return Instance of working object.
     */
    public abstract PacketChat setData(byte data);

    /**
     * Sets the content of the message.
     *
     * @param json Content as Json.
     *
     * @return Json is valid or not.
     */
    public abstract boolean setContent(String json);

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

}
