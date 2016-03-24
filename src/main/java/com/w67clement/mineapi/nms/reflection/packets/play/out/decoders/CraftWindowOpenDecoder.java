package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.inventory.packets.WindowOpen;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.nms.reflection.packets.play.out.CraftWindowOpen;

/**
 * Created by w67clement on 06/03/2016.
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
            return new CraftWindowOpen(packet);
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
