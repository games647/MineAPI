package com.w67clement.mineapi.nms;

import com.w67clement.mineapi.enums.PacketType;

public interface NmsPacket
{
	
	/**
	 * Construct packet Object, it was sent to player.
	 * @return Nms' Packet Object.
	 */
	Object constructPacket();

	PacketType getPacketType();

}

// End of NmsPacket interface