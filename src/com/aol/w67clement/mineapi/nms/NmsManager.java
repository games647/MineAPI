package com.aol.w67clement.mineapi.nms;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.entity.player.ClientCommand;
import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.message.ActionBarMessage;
import com.aol.w67clement.mineapi.message.FancyMessage;
import com.aol.w67clement.mineapi.message.Title;
import com.aol.w67clement.mineapi.tab.TabTitle;
import com.aol.w67clement.mineapi.world.PacketExplosion;
import com.aol.w67clement.mineapi.world.PacketWorldBorder;

/**
 * NmsManager is a manager of nms' class!
 * @author 67clement
 *
 */
public interface NmsManager {
	
	/**
	 *  Get the MineAPI's title object.
	 *  @author 67clement
	 * @param title The title will be displayed.
	 * @param subtitle The subtitle.
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
	
	public PacketExplosion getExplosionPacket(World world, double x, double y, double z, float radius,
			boolean sound);
	
	public PacketExplosion getExplosionPacket(Location loc, float radius,
			boolean sound);
	
	public PacketWorldBorder getPacketWorldBorder(World world);
	
	/* PACKET PLAY IN */
	
	public ClientCommand getPacketPlayInClientCommand(ClientCommand.ClientCommandType commandType);

	/* MC ENTITY */
	
	/**
	 *  Get a MC_Player, MC_Player is a advanced Player interface.
	 * @param player The Bukkit Player Object.
	 * @return A new MC_Player Object
	 */
	public MC_Player getMCPlayer(Player player);
}
