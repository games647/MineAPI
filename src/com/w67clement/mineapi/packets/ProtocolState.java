package com.w67clement.mineapi.packets;

/**
 * ProtocolState is a state of the protocol of the player. <br />
 * Used for differencing the different packets. <br />
 * Used for the Handshake Packet.
 * 
 * @author w67clement
 *
 */
public enum ProtocolState
{

	HANDSHAKE(-1),
	PLAY(0),
	STATUS(1),
	LOGIN(2);

	private final int protocolId;

	private ProtocolState(int protocolId) {
		this.protocolId = protocolId;
	}

	/**
	 * Gets the protocol Id used.
	 */
	public int getProtocolId()
	{
		return this.protocolId;
	}

	public static ProtocolState getById(int protocolId)
	{
		for (ProtocolState state : values())
			if (state.protocolId == protocolId) return state;
		return null;
	}

}
