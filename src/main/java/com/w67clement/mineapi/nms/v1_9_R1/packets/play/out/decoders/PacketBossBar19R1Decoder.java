package com.w67clement.mineapi.nms.v1_9_R1.packets.play.out.decoders;

import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.nms.v1_9_R1.packets.play.out.PacketBossBar_v1_9_R1;
import com.w67clement.mineapi.packets.play.out.PacketBossBar;
import net.minecraft.server.v1_9_R1.PacketPlayOutBoss;

/**
 * Created by w67clement on 08/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class PacketBossBar19R1Decoder implements IndividualPacketDecoder<PacketBossBar>
{
    @Override
    public PacketBossBar decode(Object packet)
    {
        if (packet instanceof PacketPlayOutBoss)
        {
            PacketPlayOutBoss packetPlayOutBoss = (PacketPlayOutBoss) packet;
            return new PacketBossBar_v1_9_R1(packetPlayOutBoss);
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
