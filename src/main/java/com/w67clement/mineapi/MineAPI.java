package com.w67clement.mineapi;

import com.w67clement.mineapi.api.PacketHandler;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.event.PacketListener;
import com.w67clement.mineapi.api.event.ReceivePacketEvent;
import com.w67clement.mineapi.api.event.SendPacketEvent;
import com.w67clement.mineapi.api.event.ping.PacketPingReceiveEvent;
import com.w67clement.mineapi.api.event.ping.PacketPingSendEvent;
import com.w67clement.mineapi.api.wrappers.MC_PacketWrapper;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.nms.ProtocolManager;
import com.w67clement.mineapi.nms.reflection.CraftNmsManager;
import com.w67clement.mineapi.nms.v1_9_R1.NmsManager_v1_9_R1;
import com.w67clement.mineapi.system.ConfigManager;
import com.w67clement.mineapi.system.ModuleManager;
import com.w67clement.mineapi.system.ProtocolInjector;
import com.w67clement.mineapi.system.ServerType;
import com.w67clement.mineapi.system.messaging.MessagingManager;
import com.w67clement.mineapi.system.messaging.defaults.DispatchCommandPacket;
import com.w67clement.mineapi.system.messaging.defaults.SendMessagePacket;
import com.w67clement.mineapi.system.modules.Module;
import com.w67clement.mineapi.system.modules.ModuleLoader;
import com.w67clement.mineapi.utils.MineAPIUtils;
import com.w67clement.mineapi.utils.PluginManagerUtils;
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
import org.mcstats.Metrics.Graph;

public class MineAPI extends JavaPlugin
{

    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MineAPI" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
    public static final String DEBUG_PREFIX = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MineAPI" + ChatColor.GRAY + "][" + ChatColor.DARK_GREEN + "Debug" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
    public static final String CONNECTION_PREFIX = ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "Connection" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
    public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    public static boolean debug = false;
    private static Map<PacketListener, List<Method>> packetListeners = new HashMap<>();
    private static ModuleManager moduleManager;
    private static MineAPIAutoUpdater autoUpdater;
    private static String serverVersion;
    private static ServerType serverType = null;
    private static NmsManager nms;
    private static ProtocolManager protocolManager;
    private static ConfigManager configManager;
    private static MessagingManager messagingManager;
    private static boolean useColor = true;
    private static boolean debugConnection = false;
    public boolean useModuleNmsManager;
    private ArrayList<MC_Player> playerCache = new ArrayList<>();

    public static void registerPacketListener(PacketListener listener, Plugin pl)
    {
        List<Method> methods = new ArrayList<>();
        for (Method method : listener.getClass().getMethods())
        {
            PacketHandler annotation = method.getAnnotation(PacketHandler.class);
            if (annotation != null)
            {
                methods.add(method);
            }
        }
        packetListeners.put(listener, methods);
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "The events of the plugin \"" + ChatColor.DARK_GREEN + pl.getName() + ChatColor.GREEN + "\" were loaded successfully!");
    }

    /**
     * Check if the server running is Spigot.
     *
     * @return Server is Spigot or not.
     */
    public static boolean isSpigot()
    {
        return serverType == ServerType.SPIGOT || isPaperSpigot();
    }

    /**
     * Check if the server running is Glowstone.
     *
     * @return Server is Glowstone or not.
     */
    public static boolean isGlowstone()
    {
        return serverType == ServerType.GLOWSTONE || isGlowstonePlusPlus();
    }

    /**
     * Check if the server running is Glowstone++.
     *
     * @return Server is Glowstone++ or not.
     */
    public static boolean isGlowstonePlusPlus()
    {
        return serverType == ServerType.GLOWSTONEPLUSPLUS;
    }

    /**
     * Check if the server running is PaperSpigot.
     *
     * @return Server is PaperSpigot or not.
     */
    public static boolean isPaperSpigot()
    {
        return serverType == ServerType.PAPERSPIGOT;
    }

    /**
     * Check if the server running is Rainbow.
     *
     * @return Server is Rainbow or not.
     */
    public static boolean isRainbow()
    {
        return serverType == ServerType.RAINBOW_PROJECT;
    }

    /**
     * Sends a message to the console.
     *
     * @param msg Message for the console.
     */
    public static void sendMessageToConsole(String msg)
    {
        sendMessageToConsole(msg, false);
    }

    /**
     * Sends a message to the console.
     *
     * @param msg   Message for the console.
     * @param debug Message is a debug message.
     */
    public static void sendMessageToConsole(String msg, boolean debug)
    {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        if (debug)
        {
            if (!MineAPI.debug)
                return;
        }
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
            return Bukkit.getServer().getClass().getPackage().getName().substring(23);
        }
    }

    /**
     * Gets the API type of the server.
     *
     * @return ServerType of the server.
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
     *
     * @param nms New NmsManager instance.
     */
    public void setNmsManager(NmsManager nms)
    {
        MineAPI.nms = nms;
    }

    public static MessagingManager getMessagingManager()
    {
        return messagingManager;
    }

    /**
     * Gets the configurations Manager of MineAPI. <br>
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

    /**
     * Gets whether debug connection was enabled.
     *
     * @return Debug Connection enabled or not.
     */
    public static boolean hasEnableDebugConnection()
    {
        return debugConnection;
    }

    @Override
    public void onLoad()
    {
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Loading " + ChatColor.DARK_AQUA + "MineAPI");
        if (this.getServer().getVersion().contains("Spigot"))
            serverType = ServerType.SPIGOT;
        else if (this.getServer().getVersion().contains("PaperSpigot"))
            serverType = ServerType.PAPERSPIGOT;
        else if (this.getServer().getName().equals("Rainbow"))
            serverType = ServerType.RAINBOW_PROJECT;

        if (serverType == null)
            try
            {
                Class.forName("net.glowstone.GlowServer");
                if (this.getServer().getName().equals("Glowstone++"))
                {
                    serverType = ServerType.GLOWSTONEPLUSPLUS;
                }
                else
                {
                    serverType = ServerType.GLOWSTONE;
                }
            }
            catch (Throwable ignored)
            {
            }
        if (serverType == null)
            serverType = ServerType.CRAFTBUKKIT;
        switch (serverType)
        {
            case CRAFTBUKKIT:
                break;
            case GLOWSTONE:
                sendMessageToConsole(PREFIX + ChatColor.GREEN + "Glowstone detected, checking type of Glowstone...");
                Class<?> glowServer = ReflectionAPI.getClass("net.glowstone.GlowServer");
                String game_version = (String) ReflectionAPI.getValue(null, ReflectionAPI.getField(glowServer, "GAME_VERSION", true));
                int protocol_version = (int) ReflectionAPI.getValue(null, ReflectionAPI.getField(glowServer, "PROTOCOL_VERSION", true));
                serverVersion = "Glowstone " + game_version + " (" + protocol_version + ")";
                sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting integration for Glowstone");
                break;
            case GLOWSTONEPLUSPLUS:
                sendMessageToConsole(PREFIX + ChatColor.GREEN + "Glowstone detected, checking type of Glowstone...");
                glowServer = ReflectionAPI.getClass("net.glowstone.GlowServer");
                game_version = (String) ReflectionAPI.getValue(null, ReflectionAPI.getField(glowServer, "GAME_VERSION", true));
                protocol_version = (int) ReflectionAPI.getValue(null, ReflectionAPI.getField(glowServer, "PROTOCOL_VERSION", true));
                serverVersion = "Glowstone " + game_version + " (" + protocol_version + ")";
                sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting integration for Glowstone++");
                break;
            case PAPERSPIGOT:
                break;
            case RAINBOW_PROJECT:
                serverVersion = "Rainbow-Project " + Bukkit.getServer().getVersion();
                useColor = false;
                break;
            case SPIGOT:
                break;
        }
    }

    @Override
    public void onEnable()
    {
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Enabling MineAPI");
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Version: " + ChatColor.DARK_GREEN + getServerVersion());
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Server type: " + ChatColor.DARK_GREEN + serverType.getServerName());
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "OS used: " + ChatColor.DARK_GREEN + System.getProperty("os.name"));
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "OS version: " + ChatColor.DARK_GREEN + System.getProperty("os.version"));
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Java version: " + ChatColor.DARK_GREEN + System.getProperty("java.version"));

        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Loading configuration...");
        MineAPIConfig config = new MineAPIConfig(this);
        debug = config.enableDebug();
        debugConnection = config.enableConnectionDebug();
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Configuration has loaded successfully!");

        // Module system
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting load modules...");
        moduleManager = new ModuleManager(new ModuleLoader(this.getClassLoader(), this));

        File folder = new File(this.getDataFolder(), "modules/");
        if (folder.exists())
        {
            assert folder.listFiles() != null;
            for (File file : folder.listFiles())
            {
                if (file.getName().endsWith(".jar") && file.isFile())
                {
                    Module module = moduleManager.loadModule(file);
                    if (module != null)
                    {
                        if (module.isEnableStartup())
                        {
                            moduleManager.enableModule(module);
                        }
                    }
                }
            }
        }
        else
        {
            folder.mkdirs();
        }

        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Loading NmsManager...");

        if (!this.useModuleNmsManager)
        {
            // YAY 1.9
            if (getServerVersion().equals("v1_9_R1"))
            {
                nms = new NmsManager_v1_9_R1();
            }
            else if (isGlowstone())
            {
                sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] " + ChatColor.RED + "You use Glowstone, isn't support by MineAPI!");
                sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] " + ChatColor.RED + "Please install the MineAPI's module: 'GlowMineAPI'!");
            }
            else if (isRainbow())
            {
                sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] " + ChatColor.RED + "You use Rainbow, isn't support by MineAPI!");
                sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] " + ChatColor.RED + "Please install the MineAPI's module: 'RainbowMineAPI'!");
            }
            else
            {
                sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] " + ChatColor.RED + "Your server is outdated!");
                sendMessageToConsole(PREFIX + ChatColor.RED + "Attempting use the NmsManager for none version...");
                sendMessageToConsole(PREFIX + ChatColor.RED + "Analysing the nms' version...");
                if (getServerVersion().contains("v1_6"))
                {
                    sendMessageToConsole(PREFIX + ChatColor.RED + "PreNetty version detected, wasn't supported...");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
                else if (getServerVersion().contains("v1_7"))
                {
                    sendMessageToConsole(PREFIX + ChatColor.RED + "1.7 version don't support many functions, disabling MineAPI...");
                    sendMessageToConsole(PREFIX + ChatColor.RED + "Please install the module OldMCSupport...");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
                else if (getServerVersion().contains("v1_8"))
                {
                    sendMessageToConsole(PREFIX + ChatColor.RED + "1.8 version don't support many functions, disabling MineAPI...");
                    sendMessageToConsole(PREFIX + ChatColor.RED + "Please install the module OldMCSupport...");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return;
                }
                else
                {
                    sendMessageToConsole(PREFIX + ChatColor.RED + "Using None NmsManager...");
                    nms = new CraftNmsManager();
                }
            }
            sendMessageToConsole(DEBUG_PREFIX + "Using " + nms.getClass().getSimpleName(), true);
            nms.init();

            // Simple injector
            final ProtocolInjector injector = new ProtocolInjector();

            sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting protocol injector...");
            if (injector.createInjector(this))
            {
                injector.addServerConnectionChannel();
                Bukkit.getOnlinePlayers().parallelStream().forEach(injector::addChannel);
                protocolManager = new ProtocolManager(injector);
                this.getServer().getPluginManager().registerEvents(protocolManager, this);
            }
        }

        sendMessageToConsole(PREFIX);
        sendMessageToConsole(PREFIX);
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting load general configurations...");
        configManager = new ConfigManager(this);
        configManager.init();

        sendMessageToConsole(PREFIX);
        sendMessageToConsole(PREFIX);
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting load commands...");
        try
        {
            this.getCommand("AdvancedVersion").setExecutor(new VersionCommand(this));
            sendMessageToConsole(PREFIX + ChatColor.GREEN + "The commands was load successful!");
            MineAPICommand mineAPICmd = new MineAPICommand(this);
            this.getCommand("MineAPI").setExecutor(mineAPICmd);
            this.getCommand("MineAPI").setTabCompleter(mineAPICmd);
        }
        catch (Throwable ex)
        {
            sendMessageToConsole(PREFIX + ChatColor.RED + "Failed to load the commands!");
        }

        sendMessageToConsole(PREFIX);
        sendMessageToConsole(PREFIX);

        // Module system
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting enable modules...");
        moduleManager.enableModules();

        sendMessageToConsole(PREFIX);
        sendMessageToConsole(PREFIX);

        try
        {
            Metrics metrics = new Metrics(this);

            Graph graph_server_type = metrics.createGraph("Server Type");

            graph_server_type.addPlotter(new Metrics.Plotter(serverType.getServerName())
            {

                @Override
                public int getValue()
                {
                    return 1;
                }
            });

            Graph graph_nms_used = metrics.createGraph("NmsManager Used");

            graph_nms_used.addPlotter(new Metrics.Plotter(nms.getClass().getSimpleName())
            {

                @Override
                public int getValue()
                {
                    return 1;
                }
            });

            metrics.start();
        }
        catch (IOException e)
        {
            // Failed to submit the stats :-(
        }

        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting Plugin Messaging system...");
        messagingManager = new MessagingManager(this);
        messagingManager.init();
        messagingManager.getPacketRegistry().registerPlugin("Default");
        messagingManager.getPacketRegistry().registerPacket("Default", 1, SendMessagePacket.class);
        messagingManager.getPacketRegistry().registerPacket("Default", 2, DispatchCommandPacket.class);
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting Auto-Updater (v1.0.3)...");
        autoUpdater = new MineAPIAutoUpdater(true, this);
        if (autoUpdater.haveNewUpdate())
        {
            sendMessageToConsole(PREFIX + ChatColor.GREEN + "Update found: MineAPI v" + autoUpdater.getLatestVersion());
            if (config.allowAutoUpdate())
            {
                sendMessageToConsole(PREFIX + ChatColor.GREEN + "Starting auto-updating...");
                if (autoUpdater.getLatestLink().equals(""))
                {
                    sendMessageToConsole(PREFIX + ChatColor.RED + "No latest link specified, MineAPI can't update it.");
                }
                else
                    try
                    {
                        if (MineAPIUtils.isOnline(autoUpdater.getLatestLink()))
                        {
                            final File mineAPIFile = new File("plugins/MineAPI-" + autoUpdater.getLatestVersion() + ".jar");
                            if (MineAPIUtils.download(autoUpdater.getLatestLink(), mineAPIFile))
                                new Thread()
                                {

                                    @Override
                                    public void run()
                                    {
                                        PluginManagerUtils.uninstallPlugin(MineAPI.this);
                                        try
                                        {
                                            Plugin mineapi = Bukkit.getPluginManager().loadPlugin(mineAPIFile);
                                            Bukkit.getPluginManager().enablePlugin(mineapi);
                                        }
                                        catch (UnknownDependencyException
                                                | InvalidPluginException
                                                | InvalidDescriptionException e)
                                        {
                                            System.out.println("Error during updating MineAPI, please redownload it!");
                                        }
                                    }
                                }.start();
                            else
                            {
                                sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "[Error] " + ChatColor.RED + "Latest MineAPI can't be downloaded.");
                            }
                        }
                        else
                        {
                            sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "The latest link is invalid or the website is down.");
                        }
                    }
                    catch (MalformedURLException e1)
                    {
                        sendMessageToConsole(PREFIX + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "The latest link is invalid.");
                        e1.printStackTrace();
                    }
            }
            if (config.allowUpdateNotifications())
                this.getServer().getPluginManager().registerEvents(new Listener()
                {

                    @EventHandler
                    public void onPlayerJoin(PlayerJoinEvent e)
                    {
                        Player player = e.getPlayer();
                        if (player.hasPermission("mineapi.update_notifications") || player.isOp())
                        {
                            getNmsManager().getFancyMessage(PREFIX + ChatColor.DARK_AQUA + "Update found: " + ChatColor.AQUA + "MineAPI v" + MineAPI.autoUpdater.getLatestVersion()).send(player);
                            getNmsManager().getFancyMessage(PREFIX + ChatColor.DARK_AQUA + "Download: ").then("On Spigot").addHoverMessage(ChatColor.GREEN + "Click to open url to download the latest MineAPI! \n" + ChatColor.RED + "Recommended!").addLink("https://www.spigotmc.org/resources/mineapi.8614/").then(" / ").then("On Bukkit").addHoverMessage(ChatColor.GREEN + "Click to open url to download the latest MineAPI!").addLink("http://dev.bukkit.org/bukkit-plugins/mineapi/").send(player);
                        }
                    }
                }, this);
        }
    }

    @Override
    public void onDisable()
    {
        try
        {
            // Disabling protocol manager
            if (protocolManager != null)
            {
                sendMessageToConsole(PREFIX + ChatColor.RED + "Disabling protocol injector...");
                protocolManager.disable();
            }
        }
        catch (NoClassDefFoundError e)
        {
            sendMessageToConsole(PREFIX + ChatColor.RED + "Error detected during disabling protocol injector!");
            sendMessageToConsole(PREFIX + ChatColor.RED + "Are you reload with new MineAPI's JarFile?");
            sendMessageToConsole(DEBUG_PREFIX + ChatColor.RED + "Error: " + e.getClass().getSimpleName() + " ;; Message: " + e.getMessage());
        }
        sendMessageToConsole(PREFIX + ChatColor.RED + "Disabling MineAPI v" + this.getDescription().getVersion());

        // Disabling modules
        if (!moduleManager.getModules().isEmpty())
        {
            moduleManager.disableModules();
        }
    }

    public void packetSend(MC_PacketWrapper<?> packetWrapper, PacketCancellable cancellable, Player player)
    {
        try
        {
            for (Entry<PacketListener, List<Method>> listener : packetListeners.entrySet())
            {
                for (Method method : listener.getValue())
                {
                    if (!method.getParameterTypes()[0].equals(SendPacketEvent.class))
                    {
                        continue;
                    }

                    PacketHandler ann = method.getAnnotation(PacketHandler.class);

                    if (ann.listenType() == PacketList.ALL || ann.listenType().getPacketName().equals(packetWrapper.getPacketName()) || ann.listenType().getPacketAliases().contains(packetWrapper.getPacketName()))
                    {

                        method.setAccessible(true);
                        try
                        {
                            method.invoke(listener.getKey(), new SendPacketEvent<>(packetWrapper, cancellable, player));
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

    public void pingPacketSend(MC_PacketWrapper<?> packetWrapper, PacketCancellable cancellable, String ip)
    {
        try
        {
            for (Entry<PacketListener, List<Method>> listener : packetListeners.entrySet())
            {
                for (Method method : listener.getValue())
                {
                    if (!method.getParameterTypes()[0].equals(PacketPingSendEvent.class))
                    {
                        continue;
                    }

                    PacketHandler ann = method.getAnnotation(PacketHandler.class);

                    if (ann.listenType() == PacketList.ALL || ann.listenType().getPacketName().equals(packetWrapper.getPacketName()) || ann.listenType().getPacketAliases().contains(packetWrapper.getPacketName()))
                    {

                        method.setAccessible(true);
                        try
                        {
                            method.invoke(listener.getKey(), new PacketPingSendEvent<>(packetWrapper, cancellable, ip));
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

    public void packetReceive(MC_PacketWrapper<?> packetWrapper, PacketCancellable cancellable, Player player)
    {
        for (Entry<PacketListener, List<Method>> listener : packetListeners.entrySet())
        {
            for (Method method : listener.getValue())
            {
                if (!method.getParameterTypes()[0].equals(ReceivePacketEvent.class))
                {
                    continue;
                }

                PacketHandler ann = method.getAnnotation(PacketHandler.class);

                if (ann.listenType() == PacketList.ALL || ann.listenType().getPacketName().equals(packetWrapper.getPacketName()) || ann.listenType().getPacketAliases().contains(packetWrapper.getPacketName()))
                {

                    method.setAccessible(true);
                    try
                    {
                        method.invoke(listener.getKey(), new ReceivePacketEvent<>(packetWrapper, cancellable, player));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void pingPacketReceive(MC_PacketWrapper<?> packetWrapper, PacketCancellable cancellable, String ip)
    {
        for (Entry<PacketListener, List<Method>> listener : packetListeners.entrySet())
        {
            for (Method method : listener.getValue())
            {
                if (!method.getParameterTypes()[0].equals(PacketPingReceiveEvent.class))
                {
                    continue;
                }

                PacketHandler ann = method.getAnnotation(PacketHandler.class);

                if (ann.listenType() == PacketList.ALL || ann.listenType().getPacketName().equals(packetWrapper.getPacketName()) || ann.listenType().getPacketAliases().contains(packetWrapper.getPacketName()))
                {

                    method.setAccessible(true);
                    try
                    {
                        method.invoke(listener.getKey(), new PacketPingReceiveEvent<>(packetWrapper, cancellable, ip));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class MineAPIConfig
    {

        private MineAPI mineapi;
        private File file;

        public MineAPIConfig(MineAPI mineapi)
        {
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

        public boolean enableDebug()
        {
            return getConfig().getBoolean("EnableDebug", false);
        }

        public boolean enableConnectionDebug()
        {
            return getConfig().getBoolean("EnableConnectionDebug", false);
        }
    }
}

// End of MineAPI class