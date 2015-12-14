package com.w67clement.mineapi.nms.none.play_in;

import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.player.ClientCommand;

public class CraftClientCommand extends ClientCommand
{
	private static Class<?> packetClass;
	private static Class<?> enum_client_command;
	private static Object enum_perform_respawn;
	private static Object enum_request_stats;
	private static Object enum_open_inventory_achievement;

	public CraftClientCommand(ClientCommandType commandType) {
		super(commandType);
	}

	static
	{
		packetClass = ReflectionAPI.getNmsClass("PacketPlayInClientCommand");
		enum_client_command = ReflectionAPI.NmsClass
				.getEnumClientCommandClass();
		for (Object obj : enum_client_command.getEnumConstants())
		{
			if (obj.toString().equals("PERFORM_RESPAWN"))
			{
				enum_perform_respawn = obj;
			}
			else if (obj.toString().equals("REQUEST_STATS"))
			{
				enum_request_stats = obj;
			}
			else if (obj.toString().equals("OPEN_INVENTORY_ACHIEVEMENT"))
			{
				enum_open_inventory_achievement = obj;
			}
		}
	}

	@Override
	public void send(Player player)
	{
		Object command = null;
		switch (this.commandType)
		{
			case PERFORM_RESPAWN:
				command = enum_perform_respawn;
				break;
			case REQUEST_STATS:
				command = enum_request_stats;
				break;
			case OPEN_INVENTORY_ACHIEVEMENT:
				command = enum_open_inventory_achievement;
				break;
			default:
				break;
		}
		if (command != null)
		{
			Object packet = ReflectionAPI.newInstance(ReflectionAPI
					.getConstructor(packetClass, enum_client_command), command);
			ReflectionAPI.NmsClass.sendPacket(player, packet);
		}
	}

}
