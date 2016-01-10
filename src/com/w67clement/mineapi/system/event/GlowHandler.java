package com.w67clement.mineapi.system.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.flowpowered.networking.ConnectionManager;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.wrappers.MC_PacketWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.NmsPacket;
import com.w67clement.mineapi.utils.NmsPacketReader;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.SocketChannel;
import net.glowstone.GlowServer;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.GlowNetworkServer;
import net.glowstone.net.pipeline.CodecsHandler;
import net.glowstone.net.pipeline.FramingHandler;
import net.glowstone.net.pipeline.MessageHandler;
import net.glowstone.net.pipeline.NoopHandler;
import net.glowstone.net.protocol.ProtocolType;
import net.md_5.bungee.api.ChatColor;

public class GlowHandler implements IHandler
{
	private static MineAPI mineapi;
	private static NmsPacketReader reader;

	public GlowHandler(MineAPI mineapi) {
		GlowHandler.mineapi = mineapi;
		reader = new NmsPacketReader();
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
					ChannelHandler handler = new ChannelHandler(player);
					channel.pipeline().addBefore("handler", "MineAPI", handler);
					MineAPI.sendMessageToConsole(
							MineAPI.DEBUG_PREFIX + handler.getClass().getName()
									+ " added to " + player.getName() + ".",
							true);
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
					io.netty.channel.ChannelHandler handler = channel.pipeline()
							.get("MineAPI");
					channel.pipeline().remove("MineAPI");
					MineAPI.sendMessageToConsole(
							MineAPI.DEBUG_PREFIX + handler.getClass().getName()
									+ " removed to " + player.getName() + ".",
							true);
				}
				catch (Exception e)
				{}
			}
		}, "MineAPI channel remover (" + player.getName() + ")").start();
	}

	@Override
	public void addServerConnectionChannel()
	{
		GlowServer server = (GlowServer) Bukkit.getServer();
		GlowNetworkServer network = (GlowNetworkServer) ReflectionAPI
				.getValue(server, ReflectionAPI.getField(server.getClass(),
						"networkServer", true));
		ServerBootstrap bootstrap = (ServerBootstrap) ReflectionAPI.getValue(
				network,
				ReflectionAPI.getField(network.getClass(), "bootstrap", true));
		bootstrap.childHandler(new GlowChannelInitializer(network));
	}

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
			NmsPacket mineapi_packet = null;
			try
			{
				mineapi_packet = reader.readPacket(packet,
						PacketList
								.getPacketByAliase(
										packet.getClass().getSimpleName())
								.getMineAPIPacket());
			}
			catch (NullPointerException e)
			{}
			catch (RuntimeException e)
			{
				MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX
						+ ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED
						+ " Error detected in the INCHandler: "
						+ e.getClass().getSimpleName(), true);
				MineAPI.sendMessageToConsole(
						MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]"
								+ ChatColor.RED + " Message: " + e.getMessage(),
						true);
				if (MineAPI.debug) e.printStackTrace();
			}
			catch (Exception e)
			{}
			MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(
					mineapi_packet, packet);
			mineapi.packetSend(packetWrapper, cancel, player);
			if (cancel.isCancelled()) { return; }
			if (mineapi_packet == null)
			{
				if (!cancel.hasForceChanges())
				{
					super.write(ctx, msg, promise);
					return;
				}
			}
			super.write(ctx, packetWrapper.getNmsPacket(), promise);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception
		{
			Object packet = msg;
			PacketCancellable cancel = new PacketCancellable();
			NmsPacket mineapi_packet = null;
			try
			{
				mineapi_packet = reader.readPacket(packet,
						PacketList
								.getPacketByAliase(
										packet.getClass().getSimpleName())
								.getMineAPIPacket());
			}
			catch (NullPointerException e)
			{}
			catch (RuntimeException e)
			{
				MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX
						+ ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED
						+ " Error detected in the INCHandler: "
						+ e.getClass().getSimpleName(), true);
				MineAPI.sendMessageToConsole(
						MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]"
								+ ChatColor.RED + " Message: " + e.getMessage(),
						true);
				if (MineAPI.debug) e.printStackTrace();
			}
			catch (Exception e)
			{}
			MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(
					mineapi_packet, packet);
			mineapi.packetRecieve(packetWrapper, cancel, player);
			if (cancel.isCancelled()) { return; }
			if (mineapi_packet == null)
			{
				if (!cancel.hasForceChanges())
				{
					super.channelRead(ctx, msg);
					return;
				}
			}
			super.channelRead(ctx, packetWrapper.getNmsPacket());
		}

		@Override
		public String toString()
		{
			return "ChannelHandler (" + this.player + ")";
		}
	}

	public class GlowChannelInitializer
			extends ChannelInitializer<SocketChannel>
	{

		private ConnectionManager connectionManager;

		public GlowChannelInitializer(ConnectionManager connectionManager) {
			this.connectionManager = connectionManager;
		}

		@Override
		protected void initChannel(SocketChannel channel) throws Exception
		{
			MessageHandler handler = new MessageHandler(this.connectionManager);
			CodecsHandler codecs = new CodecsHandler(
					ProtocolType.HANDSHAKE.getProtocol());
			FramingHandler framing = new FramingHandler();

			channel.pipeline().addLast("encryption", NoopHandler.INSTANCE)
					.addLast("framing", framing)
					.addLast("compression", NoopHandler.INSTANCE)
					.addLast("codecs", codecs)
					.addLast("packet-interception", new GlowChannelHandler())
					.addLast("handler", handler);
		}

	}

	public class GlowChannelHandler extends ChannelDuplexHandler
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
			Object packet = msg;
			NmsPacket mineapi_packet = null;
			try
			{
				mineapi_packet = reader.readPacket(packet,
						PacketList
								.getPacketByAliase(
										packet.getClass().getSimpleName())
								.getMineAPIPacket());
			}

			catch (NullPointerException e)
			{}
			catch (RuntimeException e)
			{
				MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX
						+ ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED
						+ " Error detected in the INCHandler: "
						+ e.getClass().getSimpleName(), true);
				MineAPI.sendMessageToConsole(
						MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]"
								+ ChatColor.RED + " Message: " + e.getMessage(),
						true);
				if (MineAPI.debug) e.printStackTrace();
			}
			catch (Exception e)
			{}
			MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(
					mineapi_packet, packet);
			mineapi.pingPacketSend(packetWrapper, cancel,
					ctx.channel().remoteAddress().toString());
			if (cancel.isCancelled()) { return; }
			if (mineapi_packet == null)
			{
				if (!cancel.hasForceChanges())
				{
					super.write(ctx, msg, promise);
					return;
				}
			}
			super.write(ctx, packetWrapper.getNmsPacket(), promise);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception
		{
			if (msg.getClass().getSimpleName().equals("HandshakeMessage")
					&& MineAPI.hasEnableDebugConnection())
			{
				MineAPI.sendMessageToConsole(MineAPI.CONNECTION_PREFIX + "["
						+ ctx.channel().remoteAddress().toString() + "] "
						+ this.getClass().getSimpleName() + " has connected.");
			}
			if ((!msg.getClass().getSimpleName().startsWith("Status")) || (!msg
					.getClass().getSimpleName().equals("HandshakeMessage")))
			{
				super.channelRead(ctx, msg);
				return;
			}
			PacketCancellable cancel = new PacketCancellable();
			Object packet = msg;
			NmsPacket mineapi_packet = null;
			try
			{
				mineapi_packet = reader.readPacket(packet,
						PacketList
								.getPacketByAliase(
										packet.getClass().getSimpleName())
								.getMineAPIPacket());
			}
			catch (NullPointerException e)
			{}
			catch (RuntimeException e)
			{
				MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX
						+ ChatColor.DARK_RED + "[ERROR]" + ChatColor.RED
						+ " Error detected in the INCHandler: "
						+ e.getClass().getSimpleName(), true);
				MineAPI.sendMessageToConsole(
						MineAPI.DEBUG_PREFIX + ChatColor.DARK_RED + "[ERROR]"
								+ ChatColor.RED + " Message: " + e.getMessage(),
						true);
				if (MineAPI.debug) e.printStackTrace();
			}
			catch (Exception e)
			{}
			MC_PacketWrapper<?> packetWrapper = new MC_PacketWrapper<>(
					mineapi_packet, packet);
			mineapi.pingPacketRecieve(packetWrapper, cancel,
					ctx.channel().remoteAddress().toString());
			if (cancel.isCancelled()) { return; }
			if (mineapi_packet == null)
			{
				if (!cancel.hasForceChanges())
				{
					super.channelRead(ctx, msg);
					return;
				}
			}
			super.channelRead(ctx, packetWrapper.getNmsPacket());
		}
	}

}
