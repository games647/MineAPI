package com.w67clement.mineapi.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by w67clement on 20/01/2016.
 */
public class BungeeMineAPI extends Plugin
{

    public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "MineAPI" + ChatColor.GRAY + "]" + ChatColor.RESET + " ";
    public static final CommandSender console = ProxyServer.getInstance().getConsole();
    private static boolean debug = false;

    @Override
    public void onEnable()
    {
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Enabling MineAPI");
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Server version: " + ChatColor.DARK_GREEN + ProxyServer.getInstance().getVersion());
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Server type: " + ChatColor.DARK_GREEN + "BungeeCord");
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "OS used: " + ChatColor.DARK_GREEN + System.getProperty("os.name"));
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "OS version: " + ChatColor.DARK_GREEN + System.getProperty("os.version"));
        sendMessageToConsole(PREFIX + ChatColor.GREEN + "Java version: " + ChatColor.DARK_GREEN + System.getProperty("java.version"));
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
        if (debug)
        {
            if (!BungeeMineAPI.debug)
                return;
        }
        console.sendMessage(msg);
    }

}
