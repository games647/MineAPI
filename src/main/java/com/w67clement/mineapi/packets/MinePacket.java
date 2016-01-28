package com.w67clement.mineapi.packets;

import com.w67clement.mineapi.system.messaging.PacketBuffer;

/**
 * Created by w67clement on 24/01/2016.
 */
public abstract class MinePacket
{

    public abstract void write(PacketBuffer buffer);

    public abstract void read(PacketBuffer buffer);

    public abstract void handle();

}
