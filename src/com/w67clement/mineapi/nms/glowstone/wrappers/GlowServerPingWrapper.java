package com.w67clement.mineapi.nms.glowstone.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;

import net.glowstone.GlowServer;
import net.glowstone.util.GlowServerIcon;

public class GlowServerPingWrapper implements ServerPingWrapper
{
	public static final Serializer serializer = new Serializer();
	private String motd;
	private String versionName;
	private int protocol;
	private int onlinePlayers;
	private int maxplayers;
	private List<OfflinePlayer> players;
	private String favicon;

	public GlowServerPingWrapper() {
		this.motd = Bukkit.getMotd();
		this.versionName = GlowServer.GAME_VERSION;
		this.protocol = GlowServer.PROTOCOL_VERSION;
		this.onlinePlayers = Bukkit.getOnlinePlayers().size();
		this.maxplayers = Bukkit.getMaxPlayers();
		List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		Bukkit.getOnlinePlayers().forEach(player -> {
			players.add(player);
		});
		this.players = players;
		this.favicon = ((GlowServerIcon) Bukkit.getServerIcon()).getData();
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

	@Override
	public List<OfflinePlayer> getPlayerList()
	{
		return this.players;
	}

	@Override
	public void setPlayerList(List<OfflinePlayer> players)
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
