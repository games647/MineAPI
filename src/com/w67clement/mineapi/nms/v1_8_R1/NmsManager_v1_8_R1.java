package com.w67clement.mineapi.nms.v1_8_R1;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.block.PacketBlockChange;
import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.animals.MC_Pig;
import com.w67clement.mineapi.entity.monster.MC_EntityEnderman;
import com.w67clement.mineapi.entity.others.MC_ArmorStand;
import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.entity.player.ClientCommand.ClientCommandType;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.entity.villager.MC_Villager;
import com.w67clement.mineapi.message.ActionBarMessage;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.message.Title;
import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.nms.none.play_out.message.CraftTitle;
import com.w67clement.mineapi.nms.v1_8_R1.entity.MC_ArmorStand_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.entity.MC_EntityEnderman_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.entity.MC_Entity_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.entity.MC_Pig_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.entity.MC_Player_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.entity.MC_Villager_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_in.ClientCommand_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_out.TabTitle_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_out.block.PacketBlockBreakAnimation_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_out.block.PacketBlockChange_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_out.message.ActionBarMessage_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_out.message.FancyMessage_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_out.world.PacketExplosion_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.play_out.world.PacketWorldBorder_v1_8_R1;
import com.w67clement.mineapi.nms.v1_8_R1.wrappers.ServerPingWrapper_v1_8_R1;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.PacketExplosion;
import com.w67clement.mineapi.world.PacketWorldBorder;

public class NmsManager_v1_8_R1 implements NmsManager
{

	@Override
	public Title getTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
		return new CraftTitle(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public ActionBarMessage getActionBarMessage(String message)
	{
		return new ActionBarMessage_v1_8_R1(message);
	}

	@Override
	public FancyMessage getFancyMessage(String message)
	{
		return new FancyMessage_v1_8_R1(message);
	}

	@Override
	public TabTitle getTabTitle(String header, String footer)
	{
		return new TabTitle_v1_8_R1(header, footer);
	}

	@Override
	public PacketExplosion getExplosionPacket(World world, double x, double y, double z, float radius, boolean sound)
	{
		return new PacketExplosion_v1_8_R1(world, x, y, z, radius, sound);
	}

	@Override
	public PacketExplosion getExplosionPacket(Location loc, float radius, boolean sound)
	{
		return new PacketExplosion_v1_8_R1(loc, radius, sound);
	}

	public PacketWorldBorder getPacketWorldBorder(World world)
	{
		return new PacketWorldBorder_v1_8_R1(world);
	}

	/* Packet play out - Block */

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, Location loc, byte destroyStage)
	{
		return new PacketBlockBreakAnimation_v1_8_R1(player, loc, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, int x, int y, int z,
			byte destroyStage)
	{
		return new PacketBlockBreakAnimation_v1_8_R1(player, x, y, z, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, Location loc, byte destroyStage)
	{
		return new PacketBlockBreakAnimation_v1_8_R1(player, loc, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, int x, int y, int z, byte destroyStage)
	{
		return new PacketBlockBreakAnimation_v1_8_R1(player, x, y, z, destroyStage);
	}

	public PacketBlockChange getPacketBlockChange(Material material, Location loc)
	{
		return new PacketBlockChange_v1_8_R1(material, loc);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int data, Location loc)
	{
		return new PacketBlockChange_v1_8_R1(material, data, loc);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int x, int y, int z)
	{
		return new PacketBlockChange_v1_8_R1(material, x, y, z);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int data, int x, int y, int z)
	{
		return new PacketBlockChange_v1_8_R1(material, data, x, y, z);
	}

	@Override
	public ClientCommand getPacketPlayInClientCommand(ClientCommandType commandType)
	{
		return new ClientCommand_v1_8_R1(commandType);
	}

	/*
	 * MC_Entity
	 */

	@Override
	public MC_Entity getMC_Entity(Entity entity)
	{
		return new MC_Entity_v1_8_R1(entity);
	}

	@Override
	public MC_ArmorStand getMC_ArmorStand(ArmorStand armorstand)
	{
		return new MC_ArmorStand_v1_8_R1(armorstand);
	}

	@Override
	public MC_EntityEnderman getMC_EntityEnderman(Enderman enderman)
	{
		return new MC_EntityEnderman_v1_8_R1(enderman);
	}

	@Override
	public MC_Player getMCPlayer(Player player)
	{
		return new MC_Player_v1_8_R1(player);
	}

	@Override
	public MC_Pig getMCPig(Pig pig)
	{
		return new MC_Pig_v1_8_R1(pig);
	}

	@Override
	public MC_Villager getMCVillager(Villager villager)
	{
		return new MC_Villager_v1_8_R1(villager);
	}

	@Override
	public ServerPingWrapper getServerPingWrapper(Object obj)
	{
		return new ServerPingWrapper_v1_8_R1(obj);
	}
}
