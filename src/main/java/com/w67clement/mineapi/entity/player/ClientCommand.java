package com.w67clement.mineapi.entity.player;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;

public abstract class ClientCommand extends PacketSender
{

	protected ClientCommandType commandType;

	public ClientCommand(ClientCommandType commandType) {
		this.commandType = commandType;
	}

	/**
	 * Set the ClientCommandType
	 * 
	 * @param type
	 *            A ClientCommandType Enum, cannot be null!
	 * @return THIS
	 */
	public ClientCommand setClientCommandType(ClientCommandType type)
	{
		this.commandType = type;
		return this;
	}

	/**
	 * Get the ClientCommandType.
	 * 
	 * @return A ClientCommandType enum Object.
	 */
	public ClientCommandType getClientCommandType()
	{
		return this.commandType;
	}

	@Deprecated
	@Override
	/**
	 * DO NOT USE THIS METHOD!
	 */
	public void sendAll()
	{
		return;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYIN;
	}

	public enum ClientCommandType
	{
		PERFORM_RESPAWN(0),
		REQUEST_STATS(1),
		OPEN_INVENTORY_ACHIEVEMENT(2);

		private int id;

		private ClientCommandType(int id) {
			this.id = id;
		}

		public int getId()
		{
			return this.id;
		}
	}

}
