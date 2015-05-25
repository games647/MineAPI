package com.aol.w67clement.mineapi;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.aol.w67clement.mineapi.api.PacketHandler;
import com.aol.w67clement.mineapi.api.event.PacketCancellable;
import com.aol.w67clement.mineapi.api.event.PacketListener;
import com.aol.w67clement.mineapi.api.event.RecievePacketEvent;
import com.aol.w67clement.mineapi.api.event.SendPacketEvent;
import com.aol.w67clement.mineapi.api.wrappers.PacketWrapper;
import com.aol.w67clement.mineapi.enums.PacketList;
import com.aol.w67clement.mineapi.nms.NmsManager;
import com.aol.w67clement.mineapi.nms.ProtocolManager;
import com.aol.w67clement.mineapi.nms.v1_8_R1.NmsManager_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.ProtocolManager_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R2.NmsManager_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.ProtocolManager_v1_8_R2;

public class MineAPI extends JavaPlugin {

	public static final String PREFIX = ChatColor.GRAY + "["
			+ ChatColor.DARK_AQUA + "MineAPI" + ChatColor.GRAY + "]"
			+ ChatColor.RESET + " ";
	public static ConsoleCommandSender console = Bukkit.getServer()
			.getConsoleSender();
	private static Map<PacketListener, List<Method>> packetListeners = new HashMap<PacketListener, List<Method>>();
	private static boolean isSpigot;
	private static NmsManager nms;
	private static ProtocolManager protocolManager;

	@Override
	public void onLoad() {
		console.sendMessage(PREFIX + "§aLoading §3MineAPI");
		File spigotConfig = new File("spigot.yml");
		if (spigotConfig.exists()) {
			isSpigot = true;
		} else {
			isSpigot = false;
		}
	}

	@Override
	public void onEnable() {
		console.sendMessage(PREFIX + ChatColor.GREEN + "Enabling "
				+ ChatColor.DARK_AQUA + "MineAPI");
		console.sendMessage(PREFIX + ChatColor.GREEN + "Loading nms manager...");
		console.sendMessage(PREFIX + ChatColor.GREEN + "Version: "
				+ ChatColor.RED + getServerVersion());
		console.sendMessage(PREFIX + ChatColor.GREEN + "Server is Spigot: "
				+ ChatColor.RED + isSpigot);
		if (getServerVersion().equals("v1_8_R2")) {
			nms = new NmsManager_v1_8_R2();
			protocolManager = new ProtocolManager_v1_8_R2(this);
			this.getServer().getPluginManager()
					.registerEvents(protocolManager, this);
		} else if (getServerVersion().equals("v1_8_R1")) {
			nms = new NmsManager_v1_8_R1();
			protocolManager = new ProtocolManager_v1_8_R1(this);
			this.getServer().getPluginManager()
					.registerEvents(protocolManager, this);
		} else {
			console.sendMessage(PREFIX
					+ "§4[Error] §3MineAPI §cis disabled: Your server was outdated!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		console.sendMessage(PREFIX + ChatColor.GREEN
				+ "Starting load commands...");
		try {
			this.getCommand("AdvancedVersion").setExecutor(
					new VersionCommand(this));
			console.sendMessage(PREFIX + ChatColor.GREEN
					+ "The commands was load successful!");
		} catch (Throwable ex) {
			console.sendMessage(PREFIX + ChatColor.RED
					+ "Failed to load the commands!");
		}

	}

	@Override
	public void onDisable() {
		if (protocolManager != null) {
			protocolManager.disable();
		}
		console.sendMessage(PREFIX + ChatColor.GREEN + "Disabling "
				+ ChatColor.DARK_AQUA + "MineAPI" + ChatColor.GREEN + " v"
				+ this.getDescription().getVersion());
	}

	public static void registerPacketListener(PacketListener listener, Plugin pl) {
		List<Method> methods = new ArrayList<Method>();
		for (Method method : listener.getClass().getMethods()) {
			PacketHandler annotation = method
					.getAnnotation(PacketHandler.class);
			if (annotation != null) {
				methods.add(method);
			}
		}
		packetListeners.put(listener, methods);
		console.sendMessage(PREFIX + ChatColor.GREEN
				+ "The events of the plugin \"" + ChatColor.DARK_GREEN
				+ pl.getName() + ChatColor.GREEN
				+ "\" were loaded successfully!");
	}

	public void packetSend(PacketWrapper packetWrapper,
			PacketCancellable cancellable, String receiverName) {
		try {
			for (Entry<PacketListener, List<Method>> listener : packetListeners
					.entrySet()) {
				for (Method method : listener.getValue()) {
					if (!method.getParameterTypes()[0]
							.equals(SendPacketEvent.class)) {
						continue;
					}

					PacketHandler ann = method
							.getAnnotation(PacketHandler.class);

					if (ann.listenType() == PacketList.ALL
							|| ann.listenType().getPacketName()
									.equals(packetWrapper.getPacketName())) {

						method.setAccessible(true);
						try {
							method.invoke(listener.getKey(),
									new SendPacketEvent(packetWrapper,
											cancellable, receiverName));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void packetRecieve(PacketWrapper packetWrapper,
			PacketCancellable cancellable, String receiverName) {
		for (Entry<PacketListener, List<Method>> listener : packetListeners
				.entrySet()) {
			for (Method method : listener.getValue()) {
				if (!method.getParameterTypes()[0]
						.equals(RecievePacketEvent.class)) {
					continue;
				}

				PacketHandler ann = method.getAnnotation(PacketHandler.class);

				if (ann.listenType() == PacketList.ALL
						|| ann.listenType().getPacketName()
								.equals(packetWrapper.getPacketName())) {

					method.setAccessible(true);
					try {
						method.invoke(listener.getKey(),
								new RecievePacketEvent(packetWrapper,
										cancellable, receiverName));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static boolean isSpigot() {
		return isSpigot;
	}

	/**
	 * Gets the version of your server by the packages
	 * 
	 * @return The server version
	 */
	public static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName()
				.substring(23);
	}

	/**
	 * Gets the nms Manager of MineAPI
	 * 
	 * @return A new nms Manager object
	 */
	public static NmsManager getNmsManager() {
		return nms;
	}
}

// End of MineAPI class