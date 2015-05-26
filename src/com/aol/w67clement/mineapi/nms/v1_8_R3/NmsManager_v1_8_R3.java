package com.aol.w67clement.mineapi.nms.v1_8_R3;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.entity.player.ClientCommand;
import com.aol.w67clement.mineapi.entity.player.ClientCommand.ClientCommandType;
import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.message.ActionBarMessage;
import com.aol.w67clement.mineapi.message.FancyMessage;
import com.aol.w67clement.mineapi.message.Title;
import com.aol.w67clement.mineapi.nms.NmsManager;
import com.aol.w67clement.mineapi.nms.v1_8_R3.entity.MC_Player_v1_8_R3;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_in.ClientCommand_v1_8_R3;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.TabTitle_v1_8_R3;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.message.ActionBarMessage_v1_8_R3;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.message.FancyMessage_v1_8_R3;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.message.Title_v1_8_R3;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.world.PacketExplosion_v1_8_R3;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.world.PacketWorldBorder_v1_8_R3;
import com.aol.w67clement.mineapi.tab.TabTitle;
import com.aol.w67clement.mineapi.world.PacketExplosion;
import com.aol.w67clement.mineapi.world.PacketWorldBorder;

public class NmsManager_v1_8_R3 implements NmsManager {

	@Override
	public Title getTitle(String title, String subtitle, int fadeIn, int stay,
			int fadeOut) {
		return new Title_v1_8_R3(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public ActionBarMessage getActionBarMessage(String message) {
		return new ActionBarMessage_v1_8_R3(message);
	}

	@Override
	public FancyMessage getFancyMessage(String message) {
		return new FancyMessage_v1_8_R3(message);
	}

	@Override
	public TabTitle getTabTitle(String header, String footer) {
		return new TabTitle_v1_8_R3(header, footer);
	}

	@Override
	public PacketExplosion getExplosionPacket(World world, double x, double y,
			double z, float radius, boolean sound) {
		return new PacketExplosion_v1_8_R3(world, x, y, z, radius, sound);
	}

	@Override
	public PacketExplosion getExplosionPacket(Location loc, float radius,
			boolean sound) {
		return new PacketExplosion_v1_8_R3(loc, radius, sound);
	}

	@Override
	public PacketWorldBorder getPacketWorldBorder(World world) {
		return new PacketWorldBorder_v1_8_R3(world);
	}

	@Override
	public ClientCommand getPacketPlayInClientCommand(
			ClientCommandType commandType) {
		return new ClientCommand_v1_8_R3(commandType);
	}

	@Override
	public MC_Player getMCPlayer(Player player) {
		return new MC_Player_v1_8_R3(player);
	}

}
