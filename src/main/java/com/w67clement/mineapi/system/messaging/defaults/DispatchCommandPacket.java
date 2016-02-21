package com.w67clement.mineapi.system.messaging.defaults;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.system.messaging.MessagingPacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by w67clement on 21/02/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class DispatchCommandPacket extends MessagingPacket
{
    private String server;
    private String command;
    private boolean bungeeCmd;
    private String sender;

    @Override
    public void encode(PacketBuffer buffer)
    {
        if (this.server.length() > 256)
            this.server = this.server.substring(0, 256);
        if (this.command.length() > 256)
            this.command = this.command.substring(0, 256);
        if (this.sender.length() > 16)
            this.sender = this.sender.substring(1, 16);
        buffer.writeString(this.server);
        buffer.writeString(this.command);
        buffer.writeBoolean(this.bungeeCmd);
        buffer.writeString(this.sender);
    }

    @Override
    public void decode(PacketBuffer buffer)
    {
        this.server = buffer.readStringFromBuffer(256);
        this.command = buffer.readStringFromBuffer(256);
        this.bungeeCmd = buffer.readBoolean();
        this.sender = buffer.readStringFromBuffer(16);
    }

    @Override
    public void handle()
    {
        if (this.bungeeCmd)
        {
            if (this.sender.equals("CONSOLE"))
            {
                ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), this.command);
            }
            else
            {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(this.sender);
                if (player == null)
                {
                    MineAPI.sendMessageToConsole("&7[&2PluginMessaging&7]&4[Error] &cReceived packet '" + this.getClass().getSimpleName() + "'. Error code: 404");
                    MineAPI.sendMessageToConsole("&7[&2PluginMessaging&7]&4[Error] &cPlayer '" + this.sender + "' cannot be found.");
                    return;
                }
                ProxyServer.getInstance().getPluginManager().dispatchCommand(player, this.command);
            }
        }
        else
        {
            if (this.sender.equals("CONSOLE"))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command);
            else
            {
                Player player = Bukkit.getPlayerExact(this.sender);
                if (player == null)
                {
                    MineAPI.sendMessageToConsole("&7[&2PluginMessaging&7]&4[Error] &cReceived packet '" + this.getClass().getSimpleName() + "'. Error code: 404");
                    MineAPI.sendMessageToConsole("&7[&2PluginMessaging&7]&4[Error] &cPlayer '" + this.sender + "' cannot be found.");
                    return;
                }
                Bukkit.dispatchCommand(player, this.command);
            }
        }
    }
}
