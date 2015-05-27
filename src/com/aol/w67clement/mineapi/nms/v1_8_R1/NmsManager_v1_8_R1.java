package com.aol.w67clement.mineapi.nms.v1_8_R1;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.aol.w67clement.mineapi.entity.player.ClientCommand;
import com.aol.w67clement.mineapi.entity.player.ClientCommand.ClientCommandType;
import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.message.ActionBarMessage;
import com.aol.w67clement.mineapi.message.FancyMessage;
import com.aol.w67clement.mineapi.message.Title;
import com.aol.w67clement.mineapi.nms.NmsManager;
import com.aol.w67clement.mineapi.nms.v1_8_R1.entity.MC_Player_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.play_in.ClientCommand_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.TabTitle_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.message.ActionBarMessage_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.message.FancyMessage_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.message.Title_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.world.PacketExplosion_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.world.PacketWorldBorder_v1_8_R1;
import com.aol.w67clement.mineapi.nms.v1_8_R1.wrappers.ServerPingWrapper_v1_8_R1;
import com.aol.w67clement.mineapi.tab.TabTitle;
import com.aol.w67clement.mineapi.world.PacketExplosion;
import com.aol.w67clement.mineapi.world.PacketWorldBorder;

public class NmsManager_v1_8_R1 implements NmsManager {

	@Override
	public Title getTitle(String title, String subtitle, int fadeIn, int stay,
			int fadeOut) {
		return new Title_v1_8_R1(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public ActionBarMessage getActionBarMessage(String message) {
		return new ActionBarMessage_v1_8_R1(message);
	}

	@Override
	public FancyMessage getFancyMessage(String message) {
		return new FancyMessage_v1_8_R1(message);
	}
	
	@Override
	public TabTitle getTabTitle(String header, String footer) {
		return new TabTitle_v1_8_R1(header, footer);
	}

	@Override
	public PacketExplosion getExplosionPacket(World world, double x, double y,
			double z, float radius, boolean sound) {
		return new PacketExplosion_v1_8_R1(world, x, y, z, radius, sound);
	}

	@Override
	public PacketExplosion getExplosionPacket(Location loc, float radius,
			boolean sound) {
		return new PacketExplosion_v1_8_R1(loc, radius, sound);
	}
	
	public PacketWorldBorder getPacketWorldBorder(World world) {
		return new PacketWorldBorder_v1_8_R1(world);
	}

	@Override
	public ClientCommand getPacketPlayInClientCommand(
			ClientCommandType commandType) {
		return new ClientCommand_v1_8_R1(commandType);
	}

	@Override
	public MC_Player getMCPlayer(Player player) {
		return new MC_Player_v1_8_R1(player);
	}

	@Override
	public ServerPingWrapper getServerPingWrapper(Object obj) {
		return new ServerPingWrapper_v1_8_R1(obj);
	}
}
