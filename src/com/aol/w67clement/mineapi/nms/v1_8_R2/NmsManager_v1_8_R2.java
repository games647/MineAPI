package com.aol.w67clement.mineapi.nms.v1_8_R2;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.aol.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.aol.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.aol.w67clement.mineapi.block.PacketBlockChange;
import com.aol.w67clement.mineapi.entity.animals.MC_Pig;
import com.aol.w67clement.mineapi.entity.monster.MC_EntityEnderman;
import com.aol.w67clement.mineapi.entity.player.ClientCommand;
import com.aol.w67clement.mineapi.entity.player.ClientCommand.ClientCommandType;
import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.entity.villager.MC_Villager;
import com.aol.w67clement.mineapi.message.ActionBarMessage;
import com.aol.w67clement.mineapi.message.FancyMessage;
import com.aol.w67clement.mineapi.message.Title;
import com.aol.w67clement.mineapi.nms.NmsManager;
import com.aol.w67clement.mineapi.nms.v1_8_R2.entity.MC_EntityEnderman_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.entity.MC_Pig_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.entity.MC_Player_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.entity.MC_Villager_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_in.ClientCommand_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.TabTitle_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.block.PacketBlockBreakAnimation_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.block.PacketBlockChange_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.message.ActionBarMessage_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.message.FancyMessage_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.message.Title_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.world.PacketExplosion_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.world.PacketWorldBorder_v1_8_R2;
import com.aol.w67clement.mineapi.nms.v1_8_R2.wrappers.ServerPingWrapper_v1_8_R2;
import com.aol.w67clement.mineapi.tab.TabTitle;
import com.aol.w67clement.mineapi.world.PacketExplosion;
import com.aol.w67clement.mineapi.world.PacketWorldBorder;

public class NmsManager_v1_8_R2 implements NmsManager {

	@Override
	public Title getTitle(String title, String subtitle, int fadeIn, int stay,
			int fadeOut) {
		return new Title_v1_8_R2(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public ActionBarMessage getActionBarMessage(String message) {
		return new ActionBarMessage_v1_8_R2(message);
	}

	@Override
	public FancyMessage getFancyMessage(String message) {
		return new FancyMessage_v1_8_R2(message);
	}

	@Override
	public TabTitle getTabTitle(String header, String footer) {
		return new TabTitle_v1_8_R2(header, footer);
	}

	@Override
	public PacketExplosion getExplosionPacket(World world, double x, double y,
			double z, float radius, boolean sound) {
		return new PacketExplosion_v1_8_R2(world, x, y, z, radius, sound);
	}

	@Override
	public PacketExplosion getExplosionPacket(Location loc, float radius,
			boolean sound) {
		return new PacketExplosion_v1_8_R2(loc, radius, sound);
	}
	
	public PacketWorldBorder getPacketWorldBorder(World world) {
		return new PacketWorldBorder_v1_8_R2(world);
	}
	
	/* Packet play out - Block */

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, Location loc,
			byte destroyStage) {
		return new PacketBlockBreakAnimation_v1_8_R2(player, loc, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, int x, int y,
			int z, byte destroyStage) {
		return new PacketBlockBreakAnimation_v1_8_R2(player, x, y, z, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, Location loc,
			byte destroyStage) {
		return new PacketBlockBreakAnimation_v1_8_R2(player, loc, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, int x, int y,
			int z, byte destroyStage) {
		return new PacketBlockBreakAnimation_v1_8_R2(player, x, y, z, destroyStage);
	}
	
	public PacketBlockChange getPacketBlockChange(Material material,
			Location loc) {
		return new PacketBlockChange_v1_8_R2(material, loc);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int data,
			Location loc) {
		return new PacketBlockChange_v1_8_R2(material, data, loc);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int x,
			int y, int z) {
		return new PacketBlockChange_v1_8_R2(material, x, y, z);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int data,
			int x, int y, int z) {
		return new PacketBlockChange_v1_8_R2(material, data, x, y, z);
	}
	
	@Override
	public ClientCommand getPacketPlayInClientCommand(
			ClientCommandType commandType) {
		return new ClientCommand_v1_8_R2(commandType);
	}
	
	@Override
	public MC_EntityEnderman getMC_EntityEnderman(Enderman enderman) {
		return new MC_EntityEnderman_v1_8_R2(enderman);
	}

	@Override
	public MC_Player getMCPlayer(Player player) {
		return new MC_Player_v1_8_R2(player);
	}
	
	@Override
	public MC_Pig getMCPig(Pig pig) {
		return new MC_Pig_v1_8_R2(pig);
	}
	
	@Override
	public MC_Villager getMCVillager(Villager villager) {
		return new MC_Villager_v1_8_R2(villager);
	}

	@Override
	public ServerPingWrapper getServerPingWrapper(Object obj) {
		return new ServerPingWrapper_v1_8_R2(obj);
	}
}
