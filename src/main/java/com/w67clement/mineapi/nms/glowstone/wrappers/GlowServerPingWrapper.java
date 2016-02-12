package com.w67clement.mineapi.nms.glowstone.wrappers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.system.MC_GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.glowstone.GlowServer;
import net.glowstone.util.GlowServerIcon;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class GlowServerPingWrapper implements ServerPingWrapper
{
	public static final Serializer serializer = new Serializer();
	private static final JsonParser parser = new JsonParser();
	private String motd;
	private String versionName;
	private int protocol;
	private int onlinePlayers;
	private int maxplayers;
	private List<MC_GameProfile> players;
	private String favicon;

	public GlowServerPingWrapper() {
		this.motd = Bukkit.getMotd();
		this.versionName = GlowServer.GAME_VERSION;
		this.protocol = GlowServer.PROTOCOL_VERSION;
		this.onlinePlayers = Bukkit.getOnlinePlayers().size();
		this.maxplayers = Bukkit.getMaxPlayers();
		List<MC_GameProfile> players = new ArrayList<>();
		Bukkit.getOnlinePlayers().forEach(player -> players.add(
                new MC_GameProfile(player.getUniqueId(), player.getName())));
		this.players = players;
		this.favicon = ((GlowServerIcon) Bukkit.getServerIcon()).getData();
	}

	@Override
	public String getMotd()
	{
		return this.motd;
	}

	@Override
	public void setMotd(Object obj)
	{
		String newMotd = (String) obj;
		if (this.isJson(newMotd))
		{
			JsonElement json = parser.parse(newMotd);
			if (json instanceof JsonObject)
			{
				newMotd = ((JsonObject) json).get("text").getAsString();
			}
			else if (json instanceof JsonArray)
			{
				JsonArray array = (JsonArray) json;
				for (JsonElement value : array)
				{
					if (value instanceof JsonObject)
						if (((JsonObject) value).has("text"))
					{
						newMotd = ((JsonObject) value).get("text")
								.getAsString();
						break;
					}
				}
			}
		}
		this.motd = newMotd;
	}

	@Override
	public Object getChatComponentMotd()
	{
		return null;
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
		List<OfflinePlayer> players = new ArrayList<>();
		this.players.forEach(profile -> players.add(Bukkit.getOfflinePlayer(profile.getName())));
		return players;
	}

	@Override
	public void setPlayerList(List<OfflinePlayer> players)
	{
		List<MC_GameProfile> profiles = new ArrayList<>();
		players.forEach(player -> profiles.add(
                new MC_GameProfile(player.getUniqueId(), player.getName())));
		this.setPlayerListWithGameProfile(profiles);
	}

	@Override
	public List<MC_GameProfile> getProfilesList()
	{
		return this.players;
	}

	@Override
	public void setPlayerListWithName(List<String> players)
	{
		List<MC_GameProfile> profiles = new ArrayList<>();
		players.forEach(name -> profiles.add(new MC_GameProfile(UUID.randomUUID(), name)));
		this.setPlayerListWithGameProfile(profiles);
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

	private boolean isJson(String text)
	{
		try
		{
			parser.parse(text);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

}
