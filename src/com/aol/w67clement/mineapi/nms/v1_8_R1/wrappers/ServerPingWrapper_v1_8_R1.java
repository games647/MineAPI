package com.aol.w67clement.mineapi.nms.v1_8_R1.wrappers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.ServerPing;
import net.minecraft.server.v1_8_R1.ServerPingPlayerSample;
import net.minecraft.server.v1_8_R1.ServerPingServerData;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;

import com.aol.w67clement.mineapi.api.ReflectionAPI;
import com.aol.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.aol.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.mojang.authlib.GameProfile;

public class ServerPingWrapper_v1_8_R1 implements ServerPingWrapper {

	private ServerPing ping;

	public ServerPingWrapper_v1_8_R1(Object serverPing) {
		if (ServerPing.class.equals(serverPing.getClass())) {
			this.ping = (ServerPing) serverPing;
		} else {
			MinecraftServer server = (MinecraftServer) ReflectionAPI.getValue(
					((CraftServer) Bukkit.getServer()), ReflectionAPI.getField(
							((CraftServer) Bukkit.getServer()).getClass(),
							"console", true));
			this.ping = server.aE();
		}
	}

	@Override
	public String getMotd() {
		return ChatComponentWrapper.makeJsonByChatComponent(this.ping.a());
	}

	@Override
	public Object getChatComponentMotd() {
		return this.ping.a();
	}

	@Override
	public void setMotd(Object obj) {
		this.ping.setMOTD((IChatBaseComponent) obj);
	}

	@Override
	public String getVersionName() {
		return this.ping.c().a();
	}

	@Override
	public void setVersionName(String version) {
		// ServerData
		ServerPingServerData data = this.ping.c();
		// Change version
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "a", true), version);
		// Apply change
		this.ping.setServerInfo(data);
	}

	@Override
	public int getProtocolVersion() {
		return this.ping.c().b();
	}

	@Override
	public void setProtocolVersion(int protocol) {
		// ServerData
		ServerPingServerData data = this.ping.c();
		// Change version
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "b", true), protocol);
		// Apply change
		this.ping.setServerInfo(data);
	}

	@Override
	public int getOnlinesPlayers() {
		return this.ping.b().a();
	}

	@Override
	public void setOnlinesPlayers(int onlines) {
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		// Change onlines players
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "a", true), onlines);
		// Apply change
		this.ping.setPlayerSample(data);
	}

	@Override
	public int getMaximumPlayers() {
		return this.ping.b().b();
	}

	@Override
	public void setMaximumPlayers(int max) {
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
	public List<OfflinePlayer> getPlayerList() {
		List<OfflinePlayer> playerList = new ArrayList<OfflinePlayer>();
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		for (GameProfile player : data.c()) {
			playerList.add(Bukkit.getOfflinePlayer(player.getName()));
		}
		return playerList;
	}

	@Override
	public void setPlayerList(List<OfflinePlayer> players) {
		// ServerData
		ServerPingPlayerSample data = this.ping.b();
		List<GameProfile> playerList = new ArrayList<GameProfile>();
		for (OfflinePlayer player : players) {
			playerList.add(new GameProfile(player.getUniqueId(), player
					.getName()));
		}
		GameProfile[] temp = new GameProfile[] {};
		GameProfile[] playerListArray = playerList.toArray(temp);
		// Change the player list
		ReflectionAPI.setValue(data,
				ReflectionAPI.getField(data.getClass(), "c", true),
				playerListArray);
		// Apply change
		this.ping.setPlayerSample(data);
	}

	@Override
	public Object toServerPing() {
		return this.ping;
	}
}
