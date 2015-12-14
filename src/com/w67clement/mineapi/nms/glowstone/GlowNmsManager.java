package com.w67clement.mineapi.nms.glowstone;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.block.BlockAction;
import com.w67clement.mineapi.block.PacketBlockAction;
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
import com.w67clement.mineapi.inventory.packets.WindowItems;
import com.w67clement.mineapi.message.ActionBarMessage;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.message.Title;
import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.nms.glowstone.entity.GlowMC_Entity;
import com.w67clement.mineapi.nms.glowstone.entity.GlowMC_Player;
import com.w67clement.mineapi.nms.glowstone.play_in.GlowClientCommand;
import com.w67clement.mineapi.nms.glowstone.play_out.inventory.GlowWindowItems;
import com.w67clement.mineapi.nms.glowstone.play_out.message.GlowActionBarMessage;
import com.w67clement.mineapi.nms.glowstone.play_out.message.GlowFancyMessage;
import com.w67clement.mineapi.nms.glowstone.play_out.message.GlowTitle;
import com.w67clement.mineapi.nms.glowstone.play_out.tab.GlowTabTitle;
import com.w67clement.mineapi.tab.PacketPlayerInfo;
import com.w67clement.mineapi.tab.PacketPlayerInfo.PacketPlayerInfoData;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.PacketExplosion;
import com.w67clement.mineapi.world.PacketWorldBorder;

public class GlowNmsManager implements NmsManager
{

	@Override
	public Title getTitle(String title, String subtitle, int fadeIn, int stay,
			int fadeOut)
	{
		return new GlowTitle(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public ActionBarMessage getActionBarMessage(String message)
	{
		return new GlowActionBarMessage(message);
	}

	@Override
	public FancyMessage getFancyMessage(String message)
	{
		return new GlowFancyMessage(message);
	}

	@Override
	public TabTitle getTabTitle(String header, String footer)
	{
		return new GlowTabTitle(header, footer);
	}

	@Override
	public PacketPlayerInfo getPacketPlayerInfo(
			PacketPlayerInfo.MC_EnumPlayerInfoAction action,
			List<PacketPlayerInfoData> data)
	{
		return null;
	}

	@Override
	public WindowItems getWindowItemsPacket(int windowId, List<ItemStack> items)
	{
		return new GlowWindowItems(windowId, items);
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
	public PacketBlockAction getPacketBlockAction(Location location,
			BlockAction action)
	{
		return null;
	}

	@Override
	public PacketBlockAction getPacketBlockAction(Location location,
			BlockAction action, int data)
	{
		return null;
	}

	@Override
	public PacketBlockAction getPacketBlockAction(int x, int y, int z,
			BlockAction action)
	{
		return null;
	}

	@Override
	public PacketBlockAction getPacketBlockAction(int x, int y, int z,
			BlockAction action, int data)
	{
		return null;
	}

	@Override
	public ClientCommand getPacketPlayInClientCommand(
			ClientCommandType commandType)
	{
		return new GlowClientCommand(commandType);
	}

	@Override
	public MC_Entity getMC_Entity(Entity entity)
	{
		return new GlowMC_Entity(entity);
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
		return new GlowMC_Player(player);
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
