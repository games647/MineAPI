package com.w67clement.mineapi;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.w67clement.mineapi.api.PacketHandler;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.event.PacketListener;
import com.w67clement.mineapi.api.event.RecievePacketEvent;
import com.w67clement.mineapi.api.event.SendPacketEvent;
import com.w67clement.mineapi.api.event.ping.PacketPingRecieveEvent;
import com.w67clement.mineapi.api.event.ping.PacketPingSendEvent;
import com.w67clement.mineapi.api.wrappers.PacketWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.nms.ProtocolManager;
import com.w67clement.mineapi.nms.glowstone.GlowNmsManager;
import com.w67clement.mineapi.nms.none.NmsManager_vNone;
import com.w67clement.mineapi.nms.v1_8_R1.NmsManager_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R2.NmsManager_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R3.NmsManager_v1_8_R3;
import com.w67clement.mineapi.system.ConfigManager;
import com.w67clement.mineapi.system.ModuleManager;
import com.w67clement.mineapi.system.ProtocolInjector;
import com.w67clement.mineapi.system.ServerType;
import com.w67clement.mineapi.system.modules.ModuleLoader;
import com.w67clement.mineapi.utils.MineAPIUtils;
import com.w67clement.mineapi.utils.PluginManagerUtils;

public class MineAPI extends JavaPlugin
{

	public static final String PREFIX = ChatColor.GRAY + "["
			+ ChatColor.DARK_AQUA + "MineAPI" + ChatColor.GRAY + "]"
			+ ChatColor.RESET + " ";
	public static final String SIMPLE_PREFIX = "[MineAPI] ";
	public static ConsoleCommandSender console = Bukkit.getServer()
			.getConsoleSender();
	private static Map<PacketListener, List<Method>> packetListeners = new HashMap<PacketListener, List<Method>>();
	private static ModuleManager moduleManager;
	private static MineAPI_AutoUpdater autoUpdater;
	private static boolean isSpigot;
	private static boolean isGlowstone;
	private static boolean isRainbow;
	private static String serverVersion;
	private static ServerType serverType;
	private static NmsManager nms;
	private static ProtocolManager protocolManager;
	private static ConfigManager configManager;
	private static MineAPIConfig config;
	private static boolean useColor = true;

	@Override
	public void onLoad()
	{
		sendMessageToConsole(PREFIX + "§aLoading §3MineAPI");
		isSpigot = this.getServer().getVersion().contains("Spigot");
		try
		{
			Class.forName("net.glowstone.GlowServer");
			isGlowstone = true;
		}
		catch (Throwable e)
		{
			isGlowstone = false;
		}
		isRainbow = this.getServer().getName().equals("Rainbow");
		if (isSpigot) serverType = ServerType.SPIGOT;
		else if (isGlowstone)
		{
			serverType = ServerType.GLOWSTONE;
			sendMessageToConsole(PREFIX + ChatColor.GREEN
					+ "Glowstone detected, starting integration for Glowstone...");
			Class<?> glowServer = ReflectionAPI
					.getClass("net.glowstone.GlowServer");
			String game_version = (String) ReflectionAPI.getValue(null,
					ReflectionAPI.getField(glowServer, "GAME_VERSION", true));
			int protocol_version = (int) ReflectionAPI.getValue(null,
					ReflectionAPI.getField(glowServer, "PROTOCOL_VERSION",
							true));
			serverVersion = "Glowstone " + game_version + " ("
					+ protocol_version + ")";
		}
		else if (isRainbow)
		{
			serverType = ServerType.RAINBOW_PROJECT;
			serverVersion = "Rainbow-Project "
					+ Bukkit.getServer().getVersion();
			useColor = false;
		}
		else
		{
			serverType = ServerType.CRAFTBUKKIT;
		}
	}

	@Override
	public void onEnable()
	{
		sendMessageToConsole(PREFIX + ChatColor.GREEN + "Enabling MineAPI");
		sendMessageToConsole(
				PREFIX + ChatColor.GREEN + "Loading nms manager...");
		sendMessageToConsole(PREFIX + ChatColor.GREEN + "Version: "
				+ ChatColor.DARK_GREEN + getServerVersion());
		sendMessageToConsole(PREFIX + ChatColor.GREEN + "Server type: "
				+ ChatColor.DARK_GREEN + serverType.getServerName());
		sendMessageToConsole(PREFIX + ChatColor.GREEN + "OS used: "
				+ ChatColor.DARK_GREEN + System.getProperty("os.name"));
		sendMessageToConsole(PREFIX + ChatColor.GREEN + "OS version: "
				+ ChatColor.DARK_GREEN + System.getProperty("os.version"));
		sendMessageToConsole(PREFIX + ChatColor.GREEN + "Java version: "
				+ ChatColor.DARK_GREEN + System.getProperty("java.version"));

		// Version 1.8.R3
		if (getServerVersion().equals("v1_8_R3"))
		{
			nms = new NmsManager_v1_8_R3();
			// Version 1.8.R2
		}
		else if (getServerVersion().equals("v1_8_R2"))
		{
			nms = new NmsManager_v1_8_R2();
			// Version 1.8.R1
		}
		else if (getServerVersion().equals("v1_8_R1"))
		{
			nms = new NmsManager_v1_8_R1();
			// No valid version detected
		}
		else if (isGlowstone())
		{
			nms = new GlowNmsManager();
		}
		else if (isRainbow())
		{
			sendMessageToConsole(
					PREFIX + ChatColor.DARK_RED + "[Error] " + ChatColor.RED
							+ "You use Rainbow, isn't support by MineAPI!");
			sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] "
					+ ChatColor.RED
					+ "Please install the MineAPI's module: 'RainbowMineAPI'!");
		}
		else
		{
			sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] "
					+ ChatColor.RED + "Your server is outdated!");
			sendMessageToConsole(PREFIX + ChatColor.RED
					+ "Attempting use the NmsManager for none version...");
			sendMessageToConsole(
					PREFIX + ChatColor.RED + "Analysing the nms' version...");
			if (getServerVersion().contains("v1_6"))
			{
				sendMessageToConsole(PREFIX + ChatColor.RED
						+ "PreNetty version detected, wasn't supported...");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
			else if (getServerVersion().contains("v1_7"))
			{
				sendMessageToConsole(PREFIX + ChatColor.RED
						+ "1.7 version don't support many functions, disabling MineAPI...");
				Bukkit.getPluginManager().disablePlugin(this);
				return;
			}
			else
			{
				sendMessageToConsole(
						PREFIX + ChatColor.RED + "Using None NmsManager...");
				nms = new NmsManager_vNone();
			}
		}

		// Simple injector
		final ProtocolInjector injector = new ProtocolInjector();

		sendMessageToConsole(
				PREFIX + ChatColor.GREEN + "Starting protocol injector...");
		if (injector.createInjector(this))
		{
			injector.addServerConnectionChannel();
			Bukkit.getOnlinePlayers().forEach(injector::addChannel);
			protocolManager = new ProtocolManager(this, injector);
			this.getServer().getPluginManager().registerEvents(protocolManager,
					this);
		}

		sendMessageToConsole(PREFIX);
		sendMessageToConsole(PREFIX);
		sendMessageToConsole(PREFIX + ChatColor.GREEN
				+ "Starting load general configurations...");
		configManager = new ConfigManager(this);
		configManager.init();

		sendMessageToConsole(PREFIX);
		sendMessageToConsole(PREFIX);
		sendMessageToConsole(
				PREFIX + ChatColor.GREEN + "Starting load commands...");
		try
		{
			this.getCommand("AdvancedVersion")
					.setExecutor(new VersionCommand(this));
			sendMessageToConsole(PREFIX + ChatColor.GREEN
					+ "The commands was load successful!");
			MineAPICommand mineAPICmd = new MineAPICommand(this);
			this.getCommand("MineAPI").setExecutor(mineAPICmd);
			this.getCommand("MineAPI").setTabCompleter(mineAPICmd);
		}
		catch (Throwable ex)
		{
			sendMessageToConsole(
					PREFIX + ChatColor.RED + "Failed to load the commands!");
		}

		sendMessageToConsole(PREFIX);
		sendMessageToConsole(PREFIX);

		// Module system
		sendMessageToConsole(
				PREFIX + ChatColor.GREEN + "Starting load modules...");
		moduleManager = new ModuleManager(
				new ModuleLoader(this.getClassLoader(), this));

		File folder = new File(this.getDataFolder(), "modules/");
		if (folder.exists())
		{
			for (File file : folder.listFiles())
			{
				if (file.getName().endsWith(".jar") && file.isFile())
				{
					moduleManager.loadModule(file);
				}
			}

			moduleManager.enableModules();
		}
		else
		{
			folder.mkdirs();
		}

		sendMessageToConsole(PREFIX);
		sendMessageToConsole(PREFIX);

		sendMessageToConsole(
				PREFIX + ChatColor.GREEN + "Loading configuration...");
		config = new MineAPIConfig(this);
		sendMessageToConsole(PREFIX + ChatColor.GREEN
				+ "Configuration has loaded successfully!");

		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
		}
		catch (IOException e)
		{
			// Failed to submit the stats :-(
		}

		sendMessageToConsole(
				PREFIX + ChatColor.GREEN + "Starting Auto-Updater (v1.0.3)...");
		autoUpdater = new MineAPI_AutoUpdater(true, this);
		if (autoUpdater.haveNewUpdate())
		{
			sendMessageToConsole(
					PREFIX + ChatColor.GREEN + "Update found: MineAPI v"
							+ autoUpdater.getLatestVersion());
			if (config.allowAutoUpdate())
			{
				sendMessageToConsole(
						PREFIX + ChatColor.GREEN + "Starting auto-updating...");
				if (autoUpdater.getLatestLink().equals(""))
				{
					sendMessageToConsole(PREFIX + ChatColor.RED
							+ "No latest link specified, MineAPI can't update it.");
				}
				else
				{
					try
					{
						if (MineAPIUtils.isOnline(autoUpdater.getLatestLink()))
						{
							final File mineAPIFile = new File("plugins/MineAPI-"
									+ autoUpdater.getLatestVersion() + ".jar");
							if (MineAPIUtils.download(
									autoUpdater.getLatestLink(), mineAPIFile))
							{
								new Thread() {

									@Override
									public void run()
									{
										PluginManagerUtils
												.uninstallPlugin(MineAPI.this);
										Plugin mineapi = null;
										try
										{
											mineapi = Bukkit.getPluginManager()
													.loadPlugin(mineAPIFile);
										}
										catch (UnknownDependencyException
												| InvalidPluginException
												| InvalidDescriptionException e)
										{
											System.out.println(
													"Error during updating MineAPI, please redownload it!");
											return;
										}
										Bukkit.getPluginManager()
												.enablePlugin(mineapi);
									}

								}.start();
							}
							else
							{
								sendMessageToConsole(PREFIX + ChatColor.DARK_RED
										+ "[Error] " + ChatColor.RED
										+ "Latest MineAPI can't be downloaded.");
							}
						}
						else
						{
							sendMessageToConsole(PREFIX + ChatColor.DARK_RED
									+ "Error: " + ChatColor.RED
									+ "The latest link is invalid or the website is down.");
						}
					}
					catch (MalformedURLException e1)
					{
						sendMessageToConsole(PREFIX + ChatColor.DARK_RED
								+ "Error: " + ChatColor.RED
								+ "The latest link is invalid.");
						e1.printStackTrace();
					}
				}
			}
			if (config.allowUpdateNotifications()) this.getServer()
					.getPluginManager().registerEvents(new Listener() {

						@EventHandler
						public void onPlayerJoin(PlayerJoinEvent e)
						{
							Player player = e.getPlayer();
							if (player.hasPermission(
									"mineapi.update_notifications")
									|| player.isOp())
							{
								getNmsManager()
										.getFancyMessage(PREFIX
												+ ChatColor.DARK_AQUA
												+ "Update found: "
												+ ChatColor.AQUA + "MineAPI v"
												+ MineAPI.autoUpdater
														.getLatestVersion())
										.send(player);
								getNmsManager()
										.getFancyMessage(
												PREFIX + ChatColor.DARK_AQUA
														+ "Download: ")
										.then("On Spigot")
										.addHoverMessage(ChatColor.GREEN
												+ "Click to open url to download the latest MineAPI! \n"
												+ ChatColor.RED
												+ "Recommended!")
										.addLink(
												"https://www.spigotmc.org/resources/mineapi.8614/")
										.then(" / ").then("On Bukkit")
										.addHoverMessage(ChatColor.GREEN
												+ "Click to open url to download the latest MineAPI!")
										.addLink(
												"http://dev.bukkit.org/bukkit-plugins/mineapi/")
										.send(player);
							}
						}

					}, this);
		}
	}

	@Override
	public void onDisable()
	{
		// Disabling protocol manager
		if (protocolManager != null)
		{
			sendMessageToConsole(
					PREFIX + ChatColor.RED + "Disabling protocol injector...");
			protocolManager.disable();
		}
		sendMessageToConsole(PREFIX + ChatColor.RED + "Disabling MineAPI v"
				+ this.getDescription().getVersion());

		// Disabling modules
		if (!moduleManager.getModules().isEmpty())
		{
			moduleManager.disableModules();
		}
	}

	public static void registerPacketListener(PacketListener listener,
			Plugin pl)
	{
		List<Method> methods = new ArrayList<Method>();
		for (Method method : listener.getClass().getMethods())
		{
			PacketHandler annotation = method
					.getAnnotation(PacketHandler.class);
			if (annotation != null)
			{
				methods.add(method);
			}
		}
		packetListeners.put(listener, methods);
		sendMessageToConsole(
				PREFIX + ChatColor.GREEN + "The events of the plugin \""
						+ ChatColor.DARK_GREEN + pl.getName() + ChatColor.GREEN
						+ "\" were loaded successfully!");
	}

	public void packetSend(PacketWrapper packetWrapper,
			PacketCancellable cancellable, Player player)
	{
		try
		{
			for (Entry<PacketListener, List<Method>> listener : packetListeners
					.entrySet())
			{
				for (Method method : listener.getValue())
				{
					if (!method.getParameterTypes()[0]
							.equals(SendPacketEvent.class))
					{
						continue;
					}

					PacketHandler ann = method
							.getAnnotation(PacketHandler.class);

					if (ann.listenType() == PacketList.ALL
							|| ann.listenType().getPacketName()
									.equals(packetWrapper.getPacketName()))
					{

						method.setAccessible(true);
						try
						{
							method.invoke(listener.getKey(),
									new SendPacketEvent(packetWrapper,
											cancellable, player));
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void pingPacketSend(PacketWrapper packetWrapper,
			PacketCancellable cancellable, String ip)
	{
		try
		{
			for (Entry<PacketListener, List<Method>> listener : packetListeners
					.entrySet())
			{
				for (Method method : listener.getValue())
				{
					if (!method.getParameterTypes()[0]
							.equals(PacketPingSendEvent.class))
					{
						continue;
					}

					PacketHandler ann = method
							.getAnnotation(PacketHandler.class);

					if (ann.listenType() == PacketList.ALL
							|| ann.listenType().getPacketName()
									.equals(packetWrapper.getPacketName()))
					{

						method.setAccessible(true);
						try
						{
							method.invoke(listener.getKey(),
									new PacketPingSendEvent(packetWrapper,
											cancellable, ip));
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void packetRecieve(PacketWrapper packetWrapper,
			PacketCancellable cancellable, Player player)
	{
		for (Entry<PacketListener, List<Method>> listener : packetListeners
				.entrySet())
		{
			for (Method method : listener.getValue())
			{
				if (!method.getParameterTypes()[0]
						.equals(RecievePacketEvent.class))
				{
					continue;
				}

				PacketHandler ann = method.getAnnotation(PacketHandler.class);

				if (ann.listenType() == PacketList.ALL || ann.listenType()
						.getPacketName().equals(packetWrapper.getPacketName()))
				{

					method.setAccessible(true);
					try
					{
						method.invoke(listener.getKey(), new RecievePacketEvent(
								packetWrapper, cancellable, player));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void pingPacketRecieve(PacketWrapper packetWrapper,
			PacketCancellable cancellable, String ip)
	{
		for (Entry<PacketListener, List<Method>> listener : packetListeners
				.entrySet())
		{
			for (Method method : listener.getValue())
			{
				if (!method.getParameterTypes()[0]
						.equals(PacketPingRecieveEvent.class))
				{
					continue;
				}

				PacketHandler ann = method.getAnnotation(PacketHandler.class);

				if (ann.listenType() == PacketList.ALL || ann.listenType()
						.getPacketName().equals(packetWrapper.getPacketName()))
				{

					method.setAccessible(true);
					try
					{
						method.invoke(listener.getKey(),
								new PacketPingRecieveEvent(packetWrapper,
										cancellable, ip));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Check if the server running is Spigot.
	 */
	public static boolean isSpigot()
	{
		return isSpigot;
	}

	/**
	 * Check if the server running is Glowstone.
	 */
	public static boolean isGlowstone()
	{
		return isGlowstone;
	}

	/**
	 * Check if the server running is Rainbow.
	 */
	public static boolean isRainbow()
	{
		return isRainbow;
	}

	/**
	 * Send a message to the console.
	 * 
	 * @param msg
	 *            Message for the console.
	 */
	public static void sendMessageToConsole(String msg)
	{
		if (!useColor)
		{
			msg = ChatColor.stripColor(msg);
		}
		console.sendMessage(msg);
	}

	/**
	 * Gets the version of your server by the packages
	 * 
	 * @return The server version
	 */
	public static String getServerVersion()
	{
		if (isGlowstone() || isRainbow())
		{
			return serverVersion;

		}
		else
		{
			return Bukkit.getServer().getClass().getPackage().getName()
					.substring(23);
		}
	}

	/**
	 * Gets the API type of the server.
	 */
	public static ServerType getServerType()
	{
		return serverType;
	}

	/**
	 * Gets the nms Manager of MineAPI
	 * 
	 * @return A new nms Manager object
	 */
	public static NmsManager getNmsManager()
	{
		return nms;
	}

	/**
	 * Sets the nms Manager of MineAPI.
	 */
	public void setNmsManager(NmsManager nms)
	{
		MineAPI.nms = nms;
	}

	/**
	 * Gets the configurations Manager of MineAPI.<br />
	 * With you can use a configuration for many plugins!
	 * 
	 * @return ConfigManager!
	 */
	public static ConfigManager getConfigManager()
	{
		return configManager;
	}

	/**
	 * Gets the module Manager of MineAPI
	 * 
	 * @return A ModuleManager object.
	 */
	public static ModuleManager getModuleManager()
	{
		return moduleManager;
	}

	private static class MineAPIConfig
	{

		private MineAPI mineapi;
		private File file;

		public MineAPIConfig(MineAPI mineapi) {
			this.mineapi = mineapi;
			this.file = new File(mineapi.getDataFolder(), "config.yml");
			if (!file.exists())
			{
				this.mineapi.saveResource("config.yml", true);
			}
		}

		public FileConfiguration getConfig()
		{
			return YamlConfiguration.loadConfiguration(this.file);
		}

		public boolean allowUpdateNotifications()
		{
			return getConfig().getBoolean("AllowUpdateNotifications", true);
		}

		public boolean allowAutoUpdate()
		{
			return getConfig().getBoolean("AllowAutoUpdate", false);
		}
	}
}

// End of MineAPI class