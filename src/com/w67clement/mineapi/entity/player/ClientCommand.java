package com.w67clement.mineapi.entity.player;

import com.w67clement.mineapi.nms.PacketSender;

public interface ClientCommand extends PacketSender
{

	/**
	 * Set the ClientCommandType
	 * 
	 * @param type
	 *            A ClientCommandType Enum, cannot be null!
	 * @return THIS
	 */
	public ClientCommand setClientCommandType(ClientCommand.ClientCommandType type);

	/**
	 * Get the ClientCommandType.
	 * 
	 * @return A ClientCommandType enum Object.
	 */
	public ClientCommand.ClientCommandType getClientCommandType();

	@Deprecated
	/**
	 * Not USE THIS METHOD!
	 */
	public void sendAll();

	public enum ClientCommandType
	{
		PERFORM_RESPAWN, REQUEST_STATS, OPEN_INVENTORY_ACHIEVEMENT;
	}

}
