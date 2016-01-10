package com.w67clement.mineapi.packets.handshake;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.NmsPacket;
import com.w67clement.mineapi.packets.ProtocolState;

/**
 * Packet of handshaking! More information at:
 * <a href=http://wiki.vg/Protocol#Handshake>Wiki.vg Handshake packet</a> <br/>
 * <b>This packet is read only!</b>
 * 
 * @author w67clement
 *
 */
public abstract class PacketHandshake implements NmsPacket
{
	protected final int protocol;
	protected final String hostNameOrIP;
	protected final int port;
	protected final ProtocolState nextProtocolType;

	public PacketHandshake(final int protocol, final String hostNameOrIP,
			final int port, final ProtocolState nextProtocolType) {
		this.protocol = protocol;
		this.hostNameOrIP = hostNameOrIP;
		this.port = port;
		this.nextProtocolType = nextProtocolType;
	}

	/**
	 * Gets the version of the protocol of the player.
	 */
	public final int getProtocol()
	{
		return this.protocol;
	}

	/**
	 * Gets the host name or IP of the player.
	 */
	public final String getHostNameOrIP()
	{
		return this.hostNameOrIP;
	}

	/**
	 * Gets the port used by the player.
	 */
	public final int getPort()
	{
		return this.port;
	}

	/**
	 * Gets the next protocol type used for the player. <br/>
	 * <b>DO NOT CHANGE IT!</b>
	 */
	public final ProtocolState getNextProtocolType()
	{
		return this.nextProtocolType;
	}

	@Override
	public final PacketType getPacketType()
	{
		return PacketType.HANDSHAKE;
	}

}
