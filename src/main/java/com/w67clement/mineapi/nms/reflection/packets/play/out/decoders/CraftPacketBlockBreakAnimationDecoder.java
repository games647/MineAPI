package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.nms.reflection.packets.play.out.CraftPacketBlockBreakAnimation;

/**
 * Created by w67clement on 16/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketBlockBreakAnimationDecoder implements IndividualPacketDecoder<PacketBlockBreakAnimation>
{
    @Override
    public PacketBlockBreakAnimation decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutBlockBreakAnimation.getPacketName()) || PacketList.PacketPlayOutBlockBreakAnimation.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            return new CraftPacketBlockBreakAnimation(packet);
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
