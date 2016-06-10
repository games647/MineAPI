package com.w67clement.mineapi.system.messaging.defaults;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.w67clement.mineapi.bungee.BungeeProxy;
import com.w67clement.mineapi.system.messaging.MessagingPacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;

/**
 * Created by w67clement on 17/05/2016.
 * <p>
 * Class of project: MineAPI
 */
public class BungeeServerDataPacket extends MessagingPacket
{
    private static Gson GSON = new GsonBuilder().create();
    private static BungeeProxy BUNGEEPROXY = BungeeProxy.getInstance();
    private String name;
    private String version;
    private int protocol;
    private int onlineCount;
    private int maxPlayersCount;
    private String[] players;

    public BungeeServerDataPacket()
    {
    }

    @Override
    public void encode(PacketBuffer buffer)
    {
        if (name.length() > 64)
            name = name.substring(0, 64);
        if (version.length() > 64)
            version = version.substring(0, 64);
        buffer.writeString(name);
        buffer.writeString(version);
        buffer.writeVarIntToBuffer(protocol);
        buffer.writeVarIntToBuffer(onlineCount);
        buffer.writeVarIntToBuffer(maxPlayersCount);
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < players.length; i++)
        {
            jsonArray.add(players[i]);
        }
        buffer.writeString(GSON.toJson(jsonArray));
    }

    @Override
    public void decode(PacketBuffer buffer)
    {
        name = buffer.readStringFromBuffer(64);
        version = buffer.readStringFromBuffer(64);
        protocol = buffer.readVarIntFromBuffer();
        onlineCount = buffer.readVarIntFromBuffer();
        maxPlayersCount = buffer.readVarIntFromBuffer();
        JsonArray jsonArray = GSON.fromJson(buffer.readStringFromBuffer(32767), JsonArray.class);
        players = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++)
        {
            players[i] = jsonArray.get(i).getAsString();
        }
    }

    @Override
    public void handle()
    {
        System.out.println("Data received, server: " + name);
        if (BUNGEEPROXY.hasServer(name))
            BUNGEEPROXY.getServer(name).update(this);
        else
            BUNGEEPROXY.put(this);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public int getProtocol()
    {
        return protocol;
    }

    public void setProtocol(int protocol)
    {
        this.protocol = protocol;
    }

    public int getOnlineCount()
    {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount)
    {
        this.onlineCount = onlineCount;
    }

    public int getMaxPlayersCount()
    {
        return maxPlayersCount;
    }

    public void setMaxPlayersCount(int maxPlayersCount)
    {
        this.maxPlayersCount = maxPlayersCount;
    }

    public String[] getPlayers()
    {
        return players;
    }

    public void setPlayers(String[] players)
    {
        this.players = players;
    }
}
