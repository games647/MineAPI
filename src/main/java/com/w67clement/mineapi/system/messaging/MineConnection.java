package com.w67clement.mineapi.system.messaging;

import io.netty.channel.Channel;

import java.util.Queue;
import java.util.LinkedList;

/**
 * Created by w67clement on 20/01/2016.
 */
public class MineConnection
{

    private final Queue<MessagingPacket> inPacketQueue = new LinkedList<>();
    private final Queue<MessagingPacket> outPacketQueue = new LinkedList<>();

    private Channel channel;

    public MineConnection(Channel channel)
    {
        this.channel = channel;
    }

    public Channel getChannel()
    {
        return this.channel;
    }



}
