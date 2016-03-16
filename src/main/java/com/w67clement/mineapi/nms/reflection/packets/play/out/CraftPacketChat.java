package com.w67clement.mineapi.nms.reflection.packets.play.out;

import com.w67clement.mineapi.api.ReflectionAPI.*;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.message.PacketChat;
import java.lang.reflect.Field;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 13/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketChat extends PacketChat<Object>
{
    private static final Class<?> packetClass;
    private static final Field contentField;
    private static final Field dataField;

    static
    {
        packetClass = getNmsClass("PacketPlayOutChat");
        contentField = getField(packetClass, "a", true);
        dataField = getField(packetClass, "b", true);
    }

    public CraftPacketChat(Object packet)
    {
        super(packet);
    }

    public CraftPacketChat(String json)
    {
        this(json, (byte) 1);
    }

    public CraftPacketChat(String json, byte data)
    {
        super(SunUnsafe.newInstance(packetClass));
        if (!this.setContent(json))
            this.setContent("[{\"text\":\"\"}]");
        this.setData(data);
    }

    @Override
    public String getContent()
    {
        return ChatComponentWrapper.makeJsonByChatComponent(getValue(packet, contentField));
    }

    @Override
    public byte getData()
    {
        return getValueWithType(packet, dataField, byte.class);
    }

    @Override
    public PacketChat setData(byte data)
    {
        if (data < 1)
            data = 1;
        else if (data > 2)
            data = 2;
        setValue(this.packet, dataField, data);
        return this;
    }

    @Override
    public boolean setContent(String json)
    {
        try
        {
            parser.parse(json);
        }
        catch (Exception e)
        {
            return false;
        }
        setValue(packet, contentField, ChatComponentWrapper.makeChatComponentByJson(json));
        return true;
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.getHandle());
    }
}
