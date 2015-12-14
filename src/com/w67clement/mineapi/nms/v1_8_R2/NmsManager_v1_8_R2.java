package com.w67clement.mineapi.nms.v1_8_R2;

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
import com.w67clement.mineapi.nms.none.play_in.CraftClientCommand;
import com.w67clement.mineapi.nms.none.play_out.block.CraftPacketBlockAction;
import com.w67clement.mineapi.nms.none.play_out.block.CraftPacketBlockBreakAnimation;
import com.w67clement.mineapi.nms.none.play_out.inventory.CraftWindowItems;
import com.w67clement.mineapi.nms.none.play_out.message.CraftActionBarMessage;
import com.w67clement.mineapi.nms.none.play_out.message.CraftFancyMessage;
import com.w67clement.mineapi.nms.none.play_out.message.CraftTitle;
import com.w67clement.mineapi.nms.none.play_out.tab.CraftPacketPlayerInfo;
import com.w67clement.mineapi.nms.none.play_out.tab.CraftTabTitle;
import com.w67clement.mineapi.nms.v1_8_R2.entity.MC_ArmorStand_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.entity.MC_EntityEnderman_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.entity.MC_Entity_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.entity.MC_Pig_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.entity.MC_Player_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.entity.MC_Villager_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.play_out.block.PacketBlockChange_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.play_out.world.PacketExplosion_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.play_out.world.PacketWorldBorder_v1_8_R2;
import com.w67clement.mineapi.nms.v1_8_R2.wrappers.ServerPingWrapper_v1_8_R2;
import com.w67clement.mineapi.tab.PacketPlayerInfo;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.tab.PacketPlayerInfo.PacketPlayerInfoData;
import com.w67clement.mineapi.world.PacketExplosion;
import com.w67clement.mineapi.world.PacketWorldBorder;

public class NmsManager_v1_8_R2 implements NmsManager
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
		return new CraftActionBarMessage(message);
	}

	@Override
	public FancyMessage getFancyMessage(String message)
	{
		return new CraftFancyMessage(message);
	}

	@Override
	public TabTitle getTabTitle(String header, String footer)
	{
		return new CraftTabTitle(header, footer);
	}
	
	@Override
	public PacketPlayerInfo getPacketPlayerInfo(
			PacketPlayerInfo.MC_EnumPlayerInfoAction action,
			List<PacketPlayerInfoData> data)
	{
		return new CraftPacketPlayerInfo(action, data);
	}

	@Override
	public WindowItems getWindowItemsPacket(int windowId, List<ItemStack> items)
	{
		return new CraftWindowItems(windowId, items);
	}

	@Override
	public PacketExplosion getExplosionPacket(World world, double x, double y,
			double z, float radius, boolean sound)
	{
		return new PacketExplosion_v1_8_R2(world, x, y, z, radius, sound);
	}

	@Override
	public PacketExplosion getExplosionPacket(Location loc, float radius,
			boolean sound)
	{
		return new PacketExplosion_v1_8_R2(loc, radius, sound);
	}

	public PacketWorldBorder getPacketWorldBorder(World world)
	{
		return new PacketWorldBorder_v1_8_R2(world);
	}

	/* Packet play out - Block */

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(
			MC_Player player, Location loc, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, loc, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(
			MC_Player player, int x, int y, int z, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, x, y, z,
				destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player,
			Location loc, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, loc, destroyStage);
	}

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player,
			int x, int y, int z, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, x, y, z,
				destroyStage);
	}

	public PacketBlockChange getPacketBlockChange(Material material,
			Location loc)
	{
		return new PacketBlockChange_v1_8_R2(material, loc);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int data,
			Location loc)
	{
		return new PacketBlockChange_v1_8_R2(material, data, loc);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int x,
			int y, int z)
	{
		return new PacketBlockChange_v1_8_R2(material, x, y, z);
	}

	public PacketBlockChange getPacketBlockChange(Material material, int data,
			int x, int y, int z)
	{
		return new PacketBlockChange_v1_8_R2(material, data, x, y, z);
	}

	@Override
	public PacketBlockAction getPacketBlockAction(Location location,
			BlockAction action)
	{
		return new CraftPacketBlockAction(location, action);
	}

	@Override
	public PacketBlockAction getPacketBlockAction(Location location,
			BlockAction action, int data)
	{
		return new CraftPacketBlockAction(location, action, data);
	}

	@Override
	public PacketBlockAction getPacketBlockAction(int x, int y, int z,
			BlockAction action)
	{
		return new CraftPacketBlockAction(x, y, z, action);
	}

	@Override
	public PacketBlockAction getPacketBlockAction(int x, int y, int z,
			BlockAction action, int data)
	{
		return new CraftPacketBlockAction(x, y, z, action, data);
	}

	@Override
	public ClientCommand getPacketPlayInClientCommand(
			ClientCommandType commandType)
	{
		return new CraftClientCommand(commandType);
	}

	@Override
	public MC_Entity getMC_Entity(Entity entity)
	{
		return new MC_Entity_v1_8_R2(entity);
	}

	@Override
	public MC_ArmorStand getMC_ArmorStand(ArmorStand armorstand)
	{
		return new MC_ArmorStand_v1_8_R2(armorstand);
	}

	@Override
	public MC_EntityEnderman getMC_EntityEnderman(Enderman enderman)
	{
		return new MC_EntityEnderman_v1_8_R2(enderman);
	}

	@Override
	public MC_Player getMCPlayer(Player player)
	{
		return new MC_Player_v1_8_R2(player);
	}

	@Override
	public MC_Pig getMCPig(Pig pig)
	{
		return new MC_Pig_v1_8_R2(pig);
	}

	@Override
	public MC_Villager getMCVillager(Villager villager)
	{
		return new MC_Villager_v1_8_R2(villager);
	}

	@Override
	public ServerPingWrapper getServerPingWrapper(Object obj)
	{
		return new ServerPingWrapper_v1_8_R2(obj);
	}
}
