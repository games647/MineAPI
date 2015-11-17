package com.w67clement.mineapi.nms;

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
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.entity.villager.MC_Villager;
import com.w67clement.mineapi.message.ActionBarMessage;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.message.Title;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.PacketExplosion;
import com.w67clement.mineapi.world.PacketWorldBorder;

/**
 * NmsManager is a manager of nms' class!
 * 
 * @author 67clement
 * 
 */
public interface NmsManager
{

	/**
	 * Get the MineAPI's title object.
	 * 
	 * @author 67clement
	 * @param title
	 *            The title will be displayed.
	 * @param subtitle
	 *            The subtitle.
	 * @param fadeIn
	 * @param stay
	 * @param fadeOut
	 * @return A new MineAPI's title object.
	 */
	public Title getTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut);

	/**
	 * 
	 * @author 67clement
	 * @param message
	 * @return A new MineAPI's action bar message object.
	 */
	public ActionBarMessage getActionBarMessage(String message);

	/**
	 * @author 67clement
	 * @param message
	 * @return A new MineAPI's FancyMessage object.
	 */
	public FancyMessage getFancyMessage(String message);

	public TabTitle getTabTitle(String header, String footer);

	/* Packet play out - World */

	public PacketExplosion getExplosionPacket(World world, double x, double y, double z, float radius, boolean sound);

	public PacketExplosion getExplosionPacket(Location loc, float radius, boolean sound);

	public PacketWorldBorder getPacketWorldBorder(World world);

	/* Packet play out - Block */

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, Location loc, byte destroyStage);

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, int x, int y, int z,
			byte destroyStage);

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, Location loc, byte destroyStage);

	public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, int x, int y, int z,
			byte destroyStage);

	public PacketBlockChange getPacketBlockChange(Material material, Location loc);

	public PacketBlockChange getPacketBlockChange(Material material, int data, Location loc);

	public PacketBlockChange getPacketBlockChange(Material material, int x, int y, int z);

	public PacketBlockChange getPacketBlockChange(Material material, int data, int x, int y, int z);

	/* PACKET PLAY IN */

	public ClientCommand getPacketPlayInClientCommand(ClientCommand.ClientCommandType commandType);

	/* MC ENTITY */

	public MC_Entity getMC_Entity(Entity entity);

	public MC_ArmorStand getMC_ArmorStand(ArmorStand armorstand);

	public MC_EntityEnderman getMC_EntityEnderman(Enderman enderman);

	/**
	 * Get a MC_Player, MC_Player is a advanced Player interface.
	 * 
	 * @param player
	 *            The Bukkit Player Object.
	 * @return A new MC_Player Object
	 */
	public MC_Player getMCPlayer(Player player);

	public MC_Pig getMCPig(Pig pig);

	public MC_Villager getMCVillager(Villager villager);

	/* WRAPPERS */

	public ServerPingWrapper getServerPingWrapper(Object serverPing);
}
