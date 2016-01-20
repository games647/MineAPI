package com.w67clement.mineapi.entity.player;

import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.MC_EntityLiving;
import com.w67clement.mineapi.enums.mc.MC_ChatVisibility;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.system.MC_GameProfile;
import org.bukkit.Chunk;
import org.bukkit.block.Beacon;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MC_Player extends MC_EntityLiving
{

	public void reset();

	/**
	 * Respawn the player with packet if he are dead
	 */
	public void respawn();

	/**
	 * An optimized version of sendChunkChange method
	 * 
	 * @param chunk
	 *            Chunk location
	 * @author Upd4ting
	 */
	public void sendChunkChange(Chunk chunk);

	/**
	 * If player isn't in Spectator mode is set, and he spectate a Entity.
	 * 
	 * @param entity
	 *            Observed entity.
	 */
	public void spectateEntity(Entity entity);

	/**
	 * If player isn't in Spectator mode is set, and he spectate a Entity.
	 * 
	 * @param entity
	 *            Observed entity.
	 * @see #spectateEntity(Entity)
	 */
	public void spectateEntity(MC_Entity entity);

	public void openBook(ItemStack item);

	public void openFurnace(Furnace furnace);

	public void openBrewingStand(BrewingStand bStand);

	public void openBeacon(Beacon beacon);

	public void openDispenser(Dispenser dispenser);

	public void openHopper(Hopper hopper);

	public void openSign(Sign sign);

	public void openSign(Sign sign, boolean isEditable);

	/**
	 * Gets the ping of this player.
	 * 
	 * @return Ping value
	 */
	public int getPing();

	/**
	 * Gets the windowId of the active container.
	 * 
	 * @return WindowId.
	 */
	public int getActiveContainerId();

	public String getLangUsed();

	public boolean useDefaultLanguage();

	public MC_ChatVisibility getChatVisibility();

	public FancyMessage sendMessage(String message);

	public void sendActionBarMessage(String message);

	public void sendTitle(int fadeIn, int stay, int fadeOut, String title,
			String subtitle);

	public void sendTabTitle(String header, String footer);

	/**
	 * This method is not recommended.
	 * 
	 * @param packet
	 *            The packet Object (Nms)
	 */
	@Deprecated
	public void sendPacket(Object packet) throws IllegalArgumentException;

	public Player getHandle();

	public Object getMC_Handle();

	/**
	 * Gets the GameProfile.
	 * 
	 * @return Player's GameProfile.
	 */
	public MC_GameProfile getMC_GameProfile();
}
