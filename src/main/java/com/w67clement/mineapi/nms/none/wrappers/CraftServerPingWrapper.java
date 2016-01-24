package com.w67clement.mineapi.nms.none.wrappers;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.system.MC_GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.glowstone.GlowServer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class CraftServerPingWrapper implements ServerPingWrapper
{
	public static final Serializer serializer = new Serializer();
	private String motd;
	private String versionName;
	private int protocol;
	private int onlinePlayers;
	private int maxplayers;
	private List<MC_GameProfile> players;
	private String favicon;

	public CraftServerPingWrapper() {
		this.motd = Bukkit.getMotd();
		this.versionName = GlowServer.GAME_VERSION;
		this.protocol = GlowServer.PROTOCOL_VERSION;
		this.onlinePlayers = Bukkit.getOnlinePlayers().size();
		this.maxplayers = Bukkit.getMaxPlayers();
		List<MC_GameProfile> players = new ArrayList<MC_GameProfile>();
		Bukkit.getOnlinePlayers().forEach(player -> {
			players.add(
					new MC_GameProfile(player.getUniqueId(), player.getName()));
		});
		this.players = players;
		this.favicon = "";
	}

	@Override
	public String getMotd()
	{
		return this.motd;
	}

	@Override
	public Object getChatComponentMotd()
	{
		return null;
	}

	@Override
	public void setMotd(Object obj)
	{
		this.motd = (String) obj;
	}

	@Override
	public String getVersionName()
	{
		return this.versionName;
	}

	@Override
	public void setVersionName(String version)
	{
		this.versionName = version;
	}

	@Override
	public int getProtocolVersion()
	{
		return this.protocol;
	}

	@Override
	public void setProtocolVersion(int protocol)
	{
		this.protocol = protocol;
	}

	@Override
	public int getOnlinesPlayers()
	{
		return this.onlinePlayers;
	}

	@Override
	public void setOnlinesPlayers(int onlines)
	{
		this.onlinePlayers = onlines;
	}

	@Override
	public int getMaximumPlayers()
	{
		return this.maxplayers;
	}

	@Override
	public void setMaximumPlayers(int max)
	{
		this.maxplayers = max;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<OfflinePlayer> getPlayerList()
	{
		List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		this.players.forEach(profile -> {
			players.add(Bukkit.getOfflinePlayer(profile.getName()));
		});
		return players;
	}

	@Override
	public List<MC_GameProfile> getProfilesList()
	{
		return this.players;
	}

	@Override
	public void setPlayerList(List<OfflinePlayer> players)
	{
		List<MC_GameProfile> profiles = new ArrayList<MC_GameProfile>();
		players.forEach(player -> {
			profiles.add(
					new MC_GameProfile(player.getUniqueId(), player.getName()));
		});
		this.players = profiles;
	}

	@Override
	public void setPlayerListWithName(List<String> players)
	{
		List<MC_GameProfile> profiles = new ArrayList<MC_GameProfile>();
		players.forEach(player -> {
			profiles.add(new MC_GameProfile(UUID.randomUUID(), player));
		});
		this.players = profiles;
	}

	@Override
	public void setPlayerListWithGameProfile(List<MC_GameProfile> players)
	{
		this.players = players;
	}

	@Override
	public String getFavicon()
	{
		return this.favicon;
	}

	@Override
	public void setFavicon(String favicon)
	{
		this.favicon = favicon;
	}

	@Override
	public Object toServerPing()
	{
		return serializer.serialize(this, null, null).toString();
	}

	@Override
	public String toJson()
	{
		return serializer.serialize(this, null, null).toString();
	}

}
