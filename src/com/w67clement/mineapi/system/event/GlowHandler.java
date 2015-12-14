package com.w67clement.mineapi.system.event;

import org.bukkit.entity.Player;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.wrappers.PacketWrapper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.glowstone.entity.GlowPlayer;

public class GlowHandler implements IHandler
{
	private static MineAPI mineapi;

	public GlowHandler(MineAPI mineapi) {
		GlowHandler.mineapi = mineapi;
	}

	@Override
	public void addChannel(Player player)
	{
		final Channel channel = ((GlowPlayer) player).getSession().getChannel();
		new Thread(new Runnable() {

			@Override
			public void run()
			{
				try
				{
					channel.pipeline().addBefore("handler", "MineAPI",
							new ChannelHandler(player));
				}
				catch (Exception e)
				{}
			}
		}, "MineAPI channel adder (" + player.getName() + ")").start();
	}

	@Override
	public void removeChannel(Player player)
	{
		final Channel channel = ((GlowPlayer) player).getSession().getChannel();
		new Thread(new Runnable() {

			@Override
			public void run()
			{
				try
				{
					channel.pipeline().remove("MineAPI");
				}
				catch (Exception e)
				{}
			}
		}, "MineAPI channel remover (" + player.getName() + ")").start();
	}

	@Override
	public void addServerConnectionChannel()
	{}

	public class ChannelHandler extends ChannelDuplexHandler
	{
		private Player player;

		public ChannelHandler(Player p) {
			this.player = p;
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg,
				ChannelPromise promise) throws Exception
		{
			PacketCancellable cancel = new PacketCancellable();
			Object packet = msg;
			mineapi.packetSend(new PacketWrapper(packet), cancel, player);
			if (cancel.isCancelled()) { return; }
			super.write(ctx, msg, promise);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception
		{
			Object packet = msg;
			PacketCancellable cancel = new PacketCancellable();
			mineapi.packetRecieve(new PacketWrapper(packet), cancel, player);
			if (cancel.isCancelled()) { return; }
			super.channelRead(ctx, msg);
		}

		@Override
		public String toString()
		{
			return "ChannelHandler (" + this.player + ")";
		}
	}

	public class PingChannelHandler extends ChannelDuplexHandler
	{

		@Override
		public void write(ChannelHandlerContext ctx, Object msg,
				ChannelPromise promise) throws Exception
		{
			if (!msg.getClass().getSimpleName().startsWith("Status"))
			{
				super.write(ctx, msg, promise);
				return;
			}
			PacketCancellable cancel = new PacketCancellable();
			mineapi.pingPacketSend(new PacketWrapper(msg), cancel,
					ctx.channel().remoteAddress().toString());
			if (cancel.isCancelled()) { return; }
			super.write(ctx, msg, promise);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception
		{
			if (!msg.getClass().getSimpleName().startsWith("Status"))
			{
				super.channelRead(ctx, msg);
				return;
			}
			PacketCancellable cancel = new PacketCancellable();
			mineapi.pingPacketRecieve(new PacketWrapper(msg), cancel,
					ctx.channel().remoteAddress().toString());
			if (cancel.isCancelled()) { return; }
			super.channelRead(ctx, msg);
		}
	}

}
