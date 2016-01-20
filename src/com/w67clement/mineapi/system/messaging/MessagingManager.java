package com.w67clement.mineapi.system.messaging;

import com.w67clement.mineapi.system.messaging.pipeline.MineEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by w67clement on 20/01/2016.
 */
public class MessagingManager
{

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Bootstrap bootstrap;
    private MineConnection connection = null;

    public void init()
    {
        this.bootstrap = new Bootstrap();
        this.bootstrap.group(this.bossGroup).group(this.workerGroup);
        this.bootstrap.handler(new MineChannelInitializer());
    }

    public MineConnection getConnection()
    {
        return this.connection;
    }

    public class MineChannelInitializer extends ChannelInitializer<SocketChannel>
    {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception
        {

            socketChannel.pipeline().addLast("timeout", new ReadTimeoutHandler(30));
            socketChannel.pipeline().addLast("encoder", new MineEncoder());

        }
    }
}
