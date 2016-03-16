package com.w67clement.mineapi.packets;

import com.w67clement.mineapi.packets.ProtocolState.PacketDirection;
import com.w67clement.mineapi.system.messaging.PacketBuffer;

/**
 * Created by w67clement on 24/01/2016.
 * <p>
 * Class of project: MineAPI
 */
public interface MinePacket
{

    void write(PacketBuffer buffer);

    void read(PacketBuffer buffer);

    void handle();

    ProtocolState getProtocolStateUsed();

    PacketDirection getPacketDirectionUsed();

}
