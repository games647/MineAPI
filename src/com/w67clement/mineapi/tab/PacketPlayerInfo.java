package com.w67clement.mineapi.tab;

import java.util.List;

import org.bukkit.GameMode;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import com.w67clement.mineapi.system.MC_GameProfile;

/**
 * <h1>PacketPlayerInfo</h1>
 * Sent by the server to update the user list.
 * <br/>
 * @author w67clement
 * @version 0.1
 */
public abstract class PacketPlayerInfo extends PacketSender
{

	protected MC_EnumPlayerInfoAction action;
	protected List<PacketPlayerInfoData> data;

	public PacketPlayerInfo(MC_EnumPlayerInfoAction action,
			List<PacketPlayerInfoData> data) {
		this.action = action;
		this.data = data;
	}

	/**
	 * Action of the packet.
	 * 
	 * @return The action
	 */
	public MC_EnumPlayerInfoAction getAction()
	{
		return this.action;
	}

	/**
	 * Sets the action of the packet.
	 * 
	 * @param action
	 *            The action to set.
	 */
	public void setAction(MC_EnumPlayerInfoAction action)
	{
		this.action = action;
	}

	/**
	 * Gets the player's data of the packet.
	 * 
	 * @return The data in a list.
	 */
	public List<PacketPlayerInfoData> getData()
	{
		return this.data;
	}

	/**
	 * Sets the player's data list of the packet.
	 * 
	 * @param data
	 *            The data to set.
	 */
	public void setData(List<PacketPlayerInfoData> data)
	{
		this.data = data;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	public static final class PacketPlayerInfoData
	{

		private final MC_GameProfile profile;
		private final int ping;
		private final GameMode gamemode;
		private final String playerListName;

		public PacketPlayerInfoData(MC_GameProfile profile, int ping,
				GameMode gamemode, String playerListName) {
			this.profile = profile;
			this.ping = ping;
			this.gamemode = gamemode;
			this.playerListName = playerListName;
		}

		/**
		 * Gets the profile of the player.
		 * 
		 * @return MineAPI's GameProfile Object.
		 */
		public final MC_GameProfile getProfile()
		{
			return this.profile;
		}

		/**
		 * Gets the ping of the player.
		 * 
		 * @return Ping displayed on the TabList.
		 */
		public final int getPing()
		{
			return this.ping;
		}

		/**
		 * Gets the GameMode of the player.
		 * 
		 * @return GameMode of the player.
		 */
		public final GameMode getGamemode()
		{
			return this.gamemode;
		}

		/**
		 * Gets the player's list name. <br/>
		 * This name is displayed on the TabList.
		 * 
		 * @return Player's list name.
		 */
		public final String getPlayerListName()
		{
			return this.playerListName;
		}

	}

	public static enum MC_EnumPlayerInfoAction
	{
		ADD_PLAYER(),
		REMOVE_PLAYER(),
		UPDATE_DISPLAY_NAME(),
		UPDATE_GAME_MODE(),
		UPDATE_LATENCY();
	}
}
