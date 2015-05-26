package com.aol.w67clement.mineapi.nms.v1_8_R3.play_in;

import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.entity.player.ClientCommand;
import com.aol.w67clement.mineapi.enums.PacketType;

/**
 *  Implemented class for 1_8_R3 of ClientCommand interface
 * @author 67clement
 *
 */
public class ClientCommand_v1_8_R3 implements ClientCommand {

	private ClientCommand.ClientCommandType commandType;

	public ClientCommand_v1_8_R3(ClientCommand.ClientCommandType type) {
		this.commandType = type;
	}

	@Override
	public void send(Player player) {
		// Create packet
		PacketPlayInClientCommand packet = null;
		/* GET THE COMMAND TYPE */
		if (this.commandType
				.equals(ClientCommand.ClientCommandType.PERFORM_RESPAWN)) {
			packet = new PacketPlayInClientCommand(
					PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
		} else if (this.commandType
				.equals(ClientCommand.ClientCommandType.REQUEST_STATS)) {
			packet = new PacketPlayInClientCommand(
					PacketPlayInClientCommand.EnumClientCommand.REQUEST_STATS);
		} else if (this.commandType
				.equals(ClientCommand.ClientCommandType.OPEN_INVENTORY_ACHIEVEMENT)) {
			packet = new PacketPlayInClientCommand(
					PacketPlayInClientCommand.EnumClientCommand.OPEN_INVENTORY_ACHIEVEMENT);
		} else {
			return;
		}
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	@Deprecated
	public void sendAll() {
		return;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PACKETPLAYIN;
	}

	@Override
	public ClientCommand setClientCommandType(ClientCommandType type) {
		this.commandType = type;
		return this;
	}

	@Override
	public ClientCommandType getClientCommandType() {
		return this.commandType;
	}

}

// End of ClientCommand_v1_8_R3 class