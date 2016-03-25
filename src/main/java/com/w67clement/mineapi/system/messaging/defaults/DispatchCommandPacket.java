package com.w67clement.mineapi.system.messaging.defaults;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.system.messaging.MessagingPacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by w67clement on 21/02/2016.
 * <p>
 * Class of project: MineAPI
 */
public class DispatchCommandPacket extends MessagingPacket
{
    private String server;
    private String command;
    private boolean bungeeCmd;
    private String sender;

    public DispatchCommandPacket()
    {
    }

    public DispatchCommandPacket(String server, String command, boolean bungeeCmd, String sender)
    {
        this.server = server;
        this.command = command;
        this.bungeeCmd = bungeeCmd;
        this.sender = sender;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    public boolean isBungeeCmd()
    {
        return bungeeCmd;
    }

    public void setBungeeCmd(boolean bungeeCmd)
    {
        this.bungeeCmd = bungeeCmd;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

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

    @Override
    public String toString()
    {
        return "DispatchCommandPacket[Server:'" + server + "',Command:'" + command + "',BungeeCMD:" + bungeeCmd + ",Sender:'" + sender + "',toString:'" + super.toString() + "']";
    }
}
