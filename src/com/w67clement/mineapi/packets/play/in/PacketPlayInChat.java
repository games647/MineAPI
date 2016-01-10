package com.w67clement.mineapi.packets.play.in;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.NmsPacket;

/**
 * Packet sent by the client with a text for the chat or process commands. <br />
 * <a href="http://wiki.vg/Protocol#Chat_Message_2">Wiki.vg PacketPlayInChat</a>
 * @author w67clement
 *
 */
public abstract class PacketPlayInChat implements NmsPacket
{
	protected String msg;

	public PacketPlayInChat(String msg) {
		this.msg = msg;
	}

	public final String getMessage()
	{
		return this.msg;
	}

	public final void setMessage(String msg)
	{
		this.msg = msg;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYIN;
	}

}
