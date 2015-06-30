package com.aol.w67clement.mineapi.nms.v1_8_R3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.ServerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.api.event.PacketCancellable;
import com.aol.w67clement.mineapi.api.wrappers.PacketWrapper;
import com.aol.w67clement.mineapi.nms.ProtocolManager;

public class ProtocolManager_v1_8_R3 implements ProtocolManager {

	private MineAPI mineapi;

	private Field mChannel;
	private Field getConsole;
	private Field getServerConnection;
	private Field getH;

	private HashMap<String, Channel> channels = new HashMap<String, Channel>();
	private List<Channel> injectedChannels = new ArrayList<Channel>();

	@SuppressWarnings("deprecation")
	public ProtocolManager_v1_8_R3(MineAPI mineapi) {
		this.mineapi = mineapi;

		try {
			mChannel = NetworkManager.class.getDeclaredField("channel");
			mChannel.setAccessible(true);
			getConsole = CraftServer.class.getDeclaredField("console");
			getConsole.setAccessible(true);
			getServerConnection = MinecraftServer.class.getDeclaredField("q");
			getServerConnection.setAccessible(true);
			getH = ServerConnection.class.getDeclaredField("h");
			getH.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Player player : Bukkit.getOnlinePlayers()) {
			this.injectPlayer(player);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void disable() {
		HandlerList.unregisterAll(this);
		for (Entry<String, Channel> channel : this.channels.entrySet()) {
			if (Bukkit.getServer().getPlayerExact(channel.getKey()) != null) {
				channel.getValue().pipeline().remove("MineAPI");
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		this.injectPlayer(e.getPlayer());
	}

	@EventHandler
	public void onPing(ServerListPingEvent event)
			throws IllegalArgumentException, IllegalAccessException {

		MinecraftServer server = (MinecraftServer) getConsole.get(this.mineapi
				.getServer());
		ServerConnection connection = (ServerConnection) this.getServerConnection
				.get(server);
		@SuppressWarnings("unchecked")
		List<NetworkManager> list = (List<NetworkManager>) getH.get(connection);

		for (NetworkManager manager : list) {
			injectConnection(manager);
		}
	}

	private void injectPlayer(final Player p) {
		try {
			EntityPlayer ep = ((CraftPlayer) p).getHandle();
			Channel channel = (Channel) mChannel
					.get(ep.playerConnection.networkManager);
			channels.put(p.getName(), channel);

			channel.pipeline().addBefore("packet_handler", "MineAPI",
					new ChannelDuplexHandler() {

						@SuppressWarnings("rawtypes")
						@Override
						public void write(ChannelHandlerContext ctx,
								Object msg, ChannelPromise promise)
								throws Exception {
							PacketCancellable cancel = new PacketCancellable();
							mineapi.packetSend(new PacketWrapper((Packet) msg),
									cancel, p);
							if (cancel.isCancelled()) {
								return;
							}
							super.write(ctx, msg, promise);
						}

						@SuppressWarnings("rawtypes")
						@Override
						public void channelRead(ChannelHandlerContext ctx,
								Object msg) throws Exception {
							PacketCancellable cancel = new PacketCancellable();
							mineapi.packetRecieve(new PacketWrapper(
									(Packet) msg), cancel, p);
							if (cancel.isCancelled()) {
								return;
							}
							super.channelRead(ctx, msg);
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void injectConnection(NetworkManager manager)
			throws IllegalArgumentException, IllegalAccessException {
		final Channel channel = (Channel) mChannel.get(manager);

		if (this.injectedChannels.contains(channel)) {
			return;
		}
		this.injectedChannels.add(channel);

		channel.pipeline().addBefore("packet_handler", "MineAPI Ping",
				new ChannelDuplexHandler() {

					@SuppressWarnings("rawtypes")
					@Override
					public void write(ChannelHandlerContext ctx, Object msg,
							ChannelPromise promise) throws Exception {
						if (!msg.getClass().getSimpleName()
								.startsWith("PacketStatus")) {
							super.write(ctx, msg, promise);
							return;
						}
						PacketCancellable cancel = new PacketCancellable();
						mineapi.pingPacketSend(new PacketWrapper((Packet) msg),
								cancel, channel.remoteAddress().toString());
						if (cancel.isCancelled()) {
							return;
						}
						super.write(ctx, msg, promise);
					}

					@SuppressWarnings("rawtypes")
					@Override
					public void channelRead(ChannelHandlerContext ctx,
							Object msg) throws Exception {
						if (!msg.getClass().getSimpleName()
								.startsWith("PacketStatus")) {
							super.channelRead(ctx, msg);
							return;
						}
						PacketCancellable cancel = new PacketCancellable();
						mineapi.pingPacketRecieve(new PacketWrapper(
								(Packet) msg), cancel, channel.remoteAddress()
								.toString());
						if (cancel.isCancelled()) {
							return;
						}
						super.channelRead(ctx, msg);
					}
				});
	}
}
