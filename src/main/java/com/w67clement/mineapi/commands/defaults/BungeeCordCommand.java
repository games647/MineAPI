package com.w67clement.mineapi.commands.defaults;

import com.w67clement.mineapi.bungee.BungeeProxy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by w67clement on 17/05/2016.
 * <p>
 * Class of project: MineAPI
 */
public class BungeeCordCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (strings.length == 0)
        {
            if (!commandSender.hasPermission("mineapi.cmd.bungeecord"))
            {
                commandSender.sendMessage(ChatColor.RED + "You don't have the permission!");
                return true;
            }
            commandSender.sendMessage("> Use Bungeecord: " + BungeeProxy.getInstance().useBungeecord());
            if (BungeeProxy.getInstance().useBungeecord())
                commandSender.sendMessage("> Numbers of sub-servers: " + BungeeProxy.getInstance().getNumberOfServers());
                BungeeProxy.getInstance().forEachServers((name, server) -> {
                    commandSender.sendMessage(">> Data of server '" + name + "':");
                    commandSender.sendMessage(">>> Version/Protocol: '" + server.getVersion() + "'(" + server.getProtocol() + ")");
                    commandSender.sendMessage(">>> Online/Max: " + server.getOnlineCount() + "/" + server.getMaxPlayersCount());
                });
            return true;
        }
        return false;
    }
}
