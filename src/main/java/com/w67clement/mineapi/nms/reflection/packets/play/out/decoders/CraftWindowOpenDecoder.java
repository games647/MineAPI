package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.inventory.packets.WindowOpen;
import com.w67clement.mineapi.inventory.packets.WindowType;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftWindowOpenDecoder implements IndividualPacketDecoder<WindowOpen>
{
    @Override
    public WindowOpen decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutOpenWindow.getPacketName()) || PacketList.PacketPlayOutOpenWindow.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            WindowOpen minePacket;
            if (MineAPI.isGlowstone())
            {
                int windowId = getIntValue(packet, getField(packet.getClass(), "id", true));
                String type = getStringValue(packet, getField(packet.getClass(), "type", true));
                Object title = getValue(packet, getField(packet.getClass(), "title", true));
                int size = getIntValue(packet, getField(packet.getClass(), "slots", true));
                int horseId = getIntValue(packet, getField(packet.getClass(), "entityId", true));
                minePacket = MineAPI.getNmsManager().getWindowOpenPacket(windowId, WindowType.getByMCValue(type), invokeMethodWithType(title, getMethod(title, "encode"), String.class), size, horseId);
            }
            else
            {
                int windowId = getIntValue(packet, getField(packet.getClass(), "a", true));
                String type = getStringValue(packet, getField(packet.getClass(), "b", true));
                String jsonTitle = ChatComponentWrapper.makeJsonByChatComponent(getValue(packet, getField(packet.getClass(), "c", true)));
                int size = getIntValue(packet, getField(packet.getClass(), "d", true));
                int horseId = getIntValue(packet, getField(packet.getClass(), "e", true));
                minePacket = MineAPI.getNmsManager().getWindowOpenPacket(windowId, WindowType.getByMCValue(type), jsonTitle, size, horseId);
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
