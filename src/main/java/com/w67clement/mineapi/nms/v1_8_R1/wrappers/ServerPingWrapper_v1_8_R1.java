package com.w67clement.mineapi.nms.v1_8_R1.wrappers;

import com.mojang.authlib.GameProfile;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.system.MC_GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.ServerPing;
import net.minecraft.server.v1_8_R1.ServerPingPlayerSample;
import net.minecraft.server.v1_8_R1.ServerPingServerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;

public class ServerPingWrapper_v1_8_R1 implements ServerPingWrapper
{

	private ServerPing ping;

	public ServerPingWrapper_v1_8_R1(Object serverPing) {
		if (serverPing != null)
		{
			if (ServerPing.class.equals(serverPing.getClass()))
			{
				this.ping = (ServerPing) serverPing;
			}
			else
			{
				MinecraftServer server = (MinecraftServer) ReflectionAPI
						.getValue(Bukkit.getServer(),
								  ReflectionAPI
										.getField(
												((CraftServer) Bukkit
														.getServer())
																.getClass(),
												"console", true));
				this.ping = server != null ? server.aE() : null;
			}
		}
		else
		{
			MinecraftServer server = (MinecraftServer) ReflectionAPI.getValue(Bukkit.getServer(),
					ReflectionAPI.getField(
							((CraftServer) Bukkit.getServer()).getClass(),
							"console", true));
			this.ping = server != null ? server.aE() : null;
		}
	}

	@Override
	public String getMotd()
	{
		return ChatComponentWrapper.makeJsonByChatComponent(this.ping.a());
	}

	@Override
	public void setMotd(Object obj)
	{
		if (obj instanceof String)
		{
			this.ping.setMOTD((IChatBaseComponent) ChatComponentWrapper
					.makeChatComponentByJson((String) obj));
		}
		else
			this.ping.setMOTD((IChatBaseComponent) obj);
	}

	@Override
	public Object getChatComponentMotd()
	{
		return this.ping.a();
	}

	@Override
	public String getVersionName()
	{
		return this.ping.c().a();
	}

	@Override
	public void setVersionName(String version)
	{
		// ServerData
		ServerPingServerData data = this.ping.c();
		// Change version
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "a", true), version);
		// Apply change
		this.ping.setServerInfo(data);
	}

	@Override
	public int getProtocolVersion()
	{
		return this.ping.c().b();
	}

	@Override
	public void setProtocolVersion(int protocol)
	{
		// ServerData
		ServerPingServerData data = this.ping.c();
		// Change version
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "b", true), protocol);
		// Apply change
		this.ping.setServerInfo(data);
	}

	@Override
	public int getOnlinesPlayers()
	{
		return this.ping.b().a();
	}

	@Override
	public void setOnlinesPlayers(int onlines)
	{
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		// Change onlines players
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "a", true), onlines);
		// Apply change
		this.ping.setPlayerSample(data);
	}

	@Override
	public int getMaximumPlayers()
	{
		return this.ping.b().b();
	}

	@Override
	public void setMaximumPlayers(int max)
	{
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		// Change maximum players
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "b", true), max);
		// Apply change
		this.ping.setPlayerSample(data);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<OfflinePlayer> getPlayerList()
	{
		List<OfflinePlayer> playerList = new ArrayList<>();
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		for (GameProfile player : data.c())
		{
			playerList.add(Bukkit.getOfflinePlayer(player.getName()));
		}
		return playerList;
	}

	@Override
	public void setPlayerList(List<OfflinePlayer> players)
	{
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		GameProfile[] array = new GameProfile[players.size()];
		for (int i = 0; i < array.length; i++)
		{
			OfflinePlayer p = players.get(i);
			array[i] = new GameProfile(p.getUniqueId(), p.getName());
		}
		// Change the player list
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "c", true), array);
		// Apply change
		this.ping.setPlayerSample(data);
	}

	@Override
	public List<MC_GameProfile> getProfilesList()
	{
		List<MC_GameProfile> profiles = new ArrayList<>();
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		for (GameProfile player : data.c())
		{
			profiles.add(MC_GameProfile.getByMojangObject(player));
		}
		return profiles;
	}

	@Override
	public void setPlayerListWithName(List<String> players)
	{
		List<MC_GameProfile> profiles = new ArrayList<>();
		players.forEach(player -> profiles.add(new MC_GameProfile(UUID.randomUUID(), player)));
		this.setPlayerListWithGameProfile(profiles);
	}

	@Override
	public void setPlayerListWithGameProfile(List<MC_GameProfile> players)
	{
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		GameProfile[] array = new GameProfile[players.size()];
		for (int i = 0; i < array.length; i++)
		{
			array[i] = (GameProfile) players.get(i).toNms();
		}
		// Change the player list
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "c", true), array);
		// Apply change
		this.ping.setPlayerSample(data);
	}

	@Override
	public String getFavicon()
	{
		return this.ping.d();
	}

	@Override
	public void setFavicon(String favicon)
	{
		this.ping.setFavicon(favicon);
	}

	@Override
	public Object toServerPing()
	{
		return this.ping;
	}

	@Override
	public String toJson()
	{
		return new Serializer().serialize(this, null, null).toString();
	}
}
