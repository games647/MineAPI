package com.w67clement.mineapi.system.event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.wrappers.PacketWrapper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.md_5.bungee.api.ChatColor;

public class INCHandler implements IHandler
{

	private static MineAPI mineapi;

	private static Class<?> entityPlayer = ReflectionAPI
			.getNmsClass("EntityPlayer");
	private static Class<?> playerConnection = ReflectionAPI
			.getNmsClass("PlayerConnection");

	private static Field connection = ReflectionAPI.getField(entityPlayer,
			"playerConnection", true);
	private static Field network = ReflectionAPI.getField(playerConnection,
			"networkManager", true);

	public INCHandler(MineAPI mineapi) {
		INCHandler.mineapi = mineapi;
	}

	private static Channel getChannel(Object networkManager)
	{
		Channel channel = null;
		if (MineAPI.getServerVersion().equals("v1_8_R1"))
		{
			channel = (Channel) ReflectionAPI.getValue(networkManager,
					ReflectionAPI.getField(networkManager.getClass(), "i",
							true));
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R2"))
		{
			channel = (Channel) ReflectionAPI.getValue(networkManager,
					ReflectionAPI.getField(networkManager.getClass(), "k",
							true));
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R3"))
		{
			channel = (Channel) ReflectionAPI.getValue(networkManager,
					ReflectionAPI.getField(networkManager.getClass(), "channel",
							true));
		}
		else
		{
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.RED
					+ "A unknown version used! Attempting use INCHandler...");
			channel = (Channel) ReflectionAPI.getValue(networkManager,
					ReflectionAPI.getFirstFieldOfType(networkManager.getClass(),
							Channel.class));
		}
		return channel;
	}

	@Override
	public void addChannel(final Player player)
	{
		final Object handle = ReflectionAPI.NmsClass
				.getEntityPlayerByPlayer(player);
		try
		{
			final Object connection = INCHandler.connection.get(handle);
			final Channel channel = getChannel(network.get(connection));
			new Thread(new Runnable() {

				@Override
				public void run()
				{
					try
					{
						channel.pipeline().addBefore("packet_handler",
								"MineAPI", new ChannelHandler(player));
					}
					catch (Exception e)
					{}
				}
			}, "MineAPI channel adder (" + player.getName() + ")").start();
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void removeChannel(Player player)
	{
		final Object handle = ReflectionAPI.NmsClass
				.getEntityPlayerByPlayer(player);
		try
		{
			final Object connection = INCHandler.connection.get(handle);
			final Channel channel = getChannel(network.get(connection));
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
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addServerConnectionChannel()
	{
		Server server = Bukkit.getServer();
		Object mc_Server = ReflectionAPI.invokeMethod(server,
				ReflectionAPI.getMethod(server, "getServer", new Class<?>[] {}),
				new Object[] {});
		Class<?> serverConnectionClass = ReflectionAPI
				.getNmsClass("ServerConnection");
		Object serverConnection = ReflectionAPI.getValue(mc_Server,
				ReflectionAPI.getFirstFieldOfType(
						ReflectionAPI.getNmsClass("MinecraftServer"),
						serverConnectionClass));
		Field field = ReflectionAPI.getLastFieldOfType(serverConnectionClass,
				List.class);
		List<?> currentList = (List<?>) ReflectionAPI.getValue(serverConnection,
				field);
		Field field1 = ReflectionAPI
				.getField(currentList.getClass().getSuperclass(), "list", true);
		Object obj = ReflectionAPI.getValue(currentList, field1);
		if (obj.getClass().equals(PacketPingListenerList.class)) return;
		@SuppressWarnings({ "rawtypes" })
		List newList = Collections
				.synchronizedList(new PacketPingListenerList());
		for (Object o : currentList)
		{
			newList.add(o);
		}
		ReflectionAPI.setValue(serverConnection, field, newList);
	}

	@SuppressWarnings("serial")
	private class PacketPingListenerList<E> extends ArrayList<E>
	{

		@Override
		public boolean add(E paramE)
		{
			final E a = paramE;
			new Thread(new Runnable() {

				@Override
				public void run()
				{
					try
					{
						Channel channel = null;
						while (channel == null)
						{
							channel = getChannel(a);
						}
						if (channel.pipeline().get("MineAPI_Ping") == null)
							channel.pipeline().addBefore("packet_handler",
									"MineAPI_Ping", new PingChannelHandler());
					}
					catch (Exception e)
					{}
				}
			}, "MineAPI channel adder (server)").start();
			return super.add(paramE);
		}

		@Override
		public boolean remove(Object paramE)
		{
			final Object a = paramE;
			new Thread(new Runnable() {

				@Override
				public void run()
				{
					try
					{
						Channel channel = null;
						while (channel == null)
						{
							channel = getChannel(a);
						}
						channel.pipeline().remove("MineAPI_Ping");
					}
					catch (Exception e)
					{}
				}
			}, "MineAPI channel remover (server)").start();
			return super.remove(paramE);
		}

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
			if (!msg.getClass().getSimpleName().startsWith("PacketStatus"))
			{
				super.write(ctx, msg, promise);
				return;
			}
			PacketCancellable cancel = new PacketCancellable();
			mineapi.pingPacketSend(new PacketWrapper(msg), cancel,
					"Unknown IP");
			if (cancel.isCancelled()) { return; }
			super.write(ctx, msg, promise);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception
		{
			if (!msg.getClass().getSimpleName().startsWith("PacketStatus"))
			{
				super.channelRead(ctx, msg);
				return;
			}
			PacketCancellable cancel = new PacketCancellable();
			mineapi.pingPacketRecieve(new PacketWrapper(msg), cancel,
					"Unknown IP");
			if (cancel.isCancelled()) { return; }
			super.channelRead(ctx, msg);
		}
	}
}
