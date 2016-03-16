package com.w67clement.mineapi.nms.v1_9_R1;

import com.w67clement.mineapi.packets.MinePacket;
import com.w67clement.mineapi.packets.NmsMinePacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;
import java.io.IOException;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketDataSerializer;
import net.minecraft.server.v1_9_R1.PacketListener;

/**
 * Created by w67clement on 12/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class NmsMinePacket_v1_9_R1<T extends MinePacket> extends NmsMinePacket<T> implements Packet
{
    public NmsMinePacket_v1_9_R1(T packet)
    {
        super(packet);
    }

    @Override
    public void a(PacketDataSerializer packetDataSerializer) throws IOException
    {
        PacketBuffer buffer = new PacketBuffer(packetDataSerializer);
        this.packet.write(buffer);
    }

    @Override
    public void b(PacketDataSerializer packetDataSerializer) throws IOException
    {
        PacketBuffer buffer = new PacketBuffer(packetDataSerializer);
        this.packet.read(buffer);
    }

    @Override
    public void a(PacketListener packetListener)
    {
        this.packet.handle();
    }
}
