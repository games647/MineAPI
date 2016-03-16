package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.nms.reflection.play_out.tab.CraftTabTitle;
import com.w67clement.mineapi.tab.TabTitle;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftTabTitleDecoder implements IndividualPacketDecoder<TabTitle>
{
    @Override
    public TabTitle decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutPlayerListHeaderFooter.getPacketName()) || PacketList.PacketPlayOutPlayerListHeaderFooter.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            return new CraftTabTitle(packet);
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
