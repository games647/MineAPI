package com.w67clement.mineapi.nms.none;

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
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.PacketExplosion;
import com.w67clement.mineapi.world.PacketWorldBorder;

public class NmsManager_vNone implements NmsManager
{

	@Override
	public Title getTitle(String title, String subtitle, int fadeIn, int stay,
			int fadeOut)
	{
		return new CraftTitle(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public ActionBarMessage getActionBarMessage(String message)
	{
		return null;
	}

	@Override
	public FancyMessage getFancyMessage(String message)
	{
		return null;
	}

	@Override
	public TabTitle getTabTitle(String header, String footer)
	{
		return null;
	}

	@Override
	public PacketExplosion getExplosionPacket(World world, double x, double y,
			double z, float radius, boolean sound)
	{
		return null;
	}

	@Override
	public PacketExplosion getExplosionPacket(Location loc, float radius,
			boolean sound)
	{
		return null;
	}

	@Override
	public PacketWorldBorder getPacketWorldBorder(World world)
	{
		return null;
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(
			MC_Player player, Location loc, byte destroyStage)
	{
		return null;
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(
			MC_Player player, int x, int y, int z, byte destroyStage)
	{
		return null;
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player,
			Location loc, byte destroyStage)
	{
		return null;
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player,
			int x, int y, int z, byte destroyStage)
	{
		return null;
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material,
			Location loc)
	{
		return null;
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material, int data,
			Location loc)
	{
		return null;
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material, int x,
			int y, int z)
	{
		return null;
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material, int data,
			int x, int y, int z)
	{
		return null;
	}

	@Override
	public ClientCommand getPacketPlayInClientCommand(
			ClientCommandType commandType)
	{
		return null;
	}

	@Override
	public MC_Entity getMC_Entity(Entity entity)
	{
		return null;
	}

	@Override
	public MC_ArmorStand getMC_ArmorStand(ArmorStand armorstand)
	{
		return null;
	}

	@Override
	public MC_EntityEnderman getMC_EntityEnderman(Enderman enderman)
	{
		return null;
	}

	@Override
	public MC_Player getMCPlayer(Player player)
	{
		return null;
	}

	@Override
	public MC_Pig getMCPig(Pig pig)
	{
		return null;
	}

	@Override
	public MC_Villager getMCVillager(Villager villager)
	{
		return null;
	}

	@Override
	public ServerPingWrapper getServerPingWrapper(Object serverPing)
	{
		return null;
	}

}
