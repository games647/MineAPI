package com.w67clement.mineapi.nms.v1_9_R1.packets.play.out.decoders;

import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.nms.v1_9_R1.packets.play.out.PacketBossBar_v1_9_R1;
import com.w67clement.mineapi.packets.play.out.PacketBossBar;
import java.util.UUID;
import net.minecraft.server.v1_9_R1.PacketPlayOutBoss;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 08/03/2016. <br><br/>
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
            PacketBossBar mineapiPacket = new PacketBossBar_v1_9_R1();
            mineapiPacket.setUuid(getValueWithType(packetPlayOutBoss, getField(packetPlayOutBoss.getClass(), "a", true), UUID.class));

            return mineapiPacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
