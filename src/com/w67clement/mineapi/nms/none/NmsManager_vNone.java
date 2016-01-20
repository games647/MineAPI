package com.w67clement.mineapi.nms.none;

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
import com.w67clement.mineapi.message.PacketChat;
import com.w67clement.mineapi.message.Title;
import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.nms.none.play_in.CraftClientCommand;
import com.w67clement.mineapi.nms.none.play_out.block.CraftPacketBlockAction;
import com.w67clement.mineapi.nms.none.play_out.block.CraftPacketBlockBreakAnimation;
import com.w67clement.mineapi.nms.none.play_out.block.CraftPacketBlockChange;
import com.w67clement.mineapi.nms.none.play_out.inventory.CraftWindowItems;
import com.w67clement.mineapi.nms.none.play_out.message.CraftPacketChat;
import com.w67clement.mineapi.nms.none.play_out.message.CraftTitle;
import com.w67clement.mineapi.nms.none.play_out.tab.CraftPacketPlayerInfo;
import com.w67clement.mineapi.nms.none.play_out.tab.CraftTabTitle;
import com.w67clement.mineapi.nms.none.play_out.world.CraftPacketExplosion;
import com.w67clement.mineapi.nms.none.play_out.world.CraftPacketWorldBorder;
import com.w67clement.mineapi.nms.none.wrappers.CraftServerPingWrapper;
import com.w67clement.mineapi.tab.PacketPlayerInfo;
import com.w67clement.mineapi.tab.PacketPlayerInfo.PacketPlayerInfoData;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.MC_World;
import com.w67clement.mineapi.world.PacketExplosion;
import com.w67clement.mineapi.world.PacketWorldBorder;
import java.util.Arrays;
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

public class NmsManager_vNone extends NmsManager
{

	@Override
	public Title getTitle(String title, String subtitle, int fadeIn, int stay,
			int fadeOut)
	{
		return new CraftTitle(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public PacketChat getPacketChat(String content)
	{
		return new CraftPacketChat(content);
	}

	@Override
	public PacketChat getPacketChat(String content, byte data)
	{
		return new CraftPacketChat(content, data);
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
	public PacketPlayerInfo getPacketPlayerInfo(
			PacketPlayerInfo.MC_EnumPlayerInfoAction action,
			PacketPlayerInfoData... data)
	{
		return new CraftPacketPlayerInfo(action, Arrays.asList(data));
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
		return new CraftPacketExplosion(world, x, y, z, radius, sound);
	}

	@Override
	public PacketExplosion getExplosionPacket(Location loc, float radius,
			boolean sound)
	{
		return new CraftPacketExplosion(loc, radius, sound);
	}

	@Override
	public PacketWorldBorder getPacketWorldBorder(World world)
	{
		return new CraftPacketWorldBorder(world);
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(
			MC_Player player, Location loc, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, loc, destroyStage);
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(
			MC_Player player, int x, int y, int z, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, x, y, z,
				destroyStage);
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player,
			Location loc, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, loc, destroyStage);
	}

	@Override
	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player,
			int x, int y, int z, byte destroyStage)
	{
		return new CraftPacketBlockBreakAnimation(player, x, y, z,
				destroyStage);
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material,
			Location loc)
	{
		return new CraftPacketBlockChange(material, loc);
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material, int data,
			Location loc)
	{
		return new CraftPacketBlockChange(material, data, loc);
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material, int x,
			int y, int z)
	{
		return new CraftPacketBlockChange(material, x, y, z);
	}

	@Override
	public PacketBlockChange getPacketBlockChange(Material material, int data,
			int x, int y, int z)
	{
		return new CraftPacketBlockChange(material, data, x, y, z);
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

	/* World */

	@Override
	public MC_World getMC_World(World world)
	{
		return null;
	}

	@Override
	public ServerPingWrapper getServerPingWrapper()
	{
		return new CraftServerPingWrapper();
	}

	@Override
	public ServerPingWrapper getServerPingWrapper(Object serverPing)
	{
		return this.getServerPingWrapper();
	}

}
