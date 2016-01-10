package com.w67clement.mineapi.packets.status;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.NmsPacket;

/**
 * PacketStatusOutServerInfo is the packet send to client when the client ping
 * in the server list the server.
 * 
 * @author w67clement
 * @version MineAPI v2.2.0
 */
public abstract class PacketStatusOutServerInfo implements NmsPacket
{
	protected ServerPingWrapper ping;

	public PacketStatusOutServerInfo(ServerPingWrapper ping) {
		this.ping = ping;
	}

	/**
	 * Gets the ServerPing of the packet. <br />
	 * It contains the informations of the ping.
	 */
	public ServerPingWrapper getServerPing()
	{
		return this.ping;
	}

	/**
	 * Sets the ServerPing of the packet. <br />
	 * It contains the informations of the ping.
	 */
	public void setServerPing(ServerPingWrapper ping)
	{
		this.ping = ping;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETSTATUS;
	}

}
