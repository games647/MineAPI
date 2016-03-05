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

	void reset();

	/**
	 * Respawn the player with packet if he are dead
	 */
	void respawn();

	/**
	 * An optimized version of sendChunkChange method
	 * 
	 * @param chunk
	 *            Chunk location
	 * @author Upd4ting
	 */
	void sendChunkChange(Chunk chunk);

	/**
	 * If player isn't in Spectator mode is set, and he spectate a Entity.
	 * 
	 * @param entity
	 *            Observed entity.
	 */
	void spectateEntity(Entity entity);

	/**
	 * If player isn't in Spectator mode is set, and he spectate a Entity.
	 * 
	 * @param entity
	 *            Observed entity.
	 * @see #spectateEntity(Entity)
	 */
	void spectateEntity(MC_Entity entity);

	void openBook(ItemStack item, EnumHand hand);

	void openFurnace(Furnace furnace);

	void openBrewingStand(BrewingStand bStand);

	void openBeacon(Beacon beacon);

	void openDispenser(Dispenser dispenser);

	void openHopper(Hopper hopper);

	void openSign(Sign sign);

	void openSign(Sign sign, boolean isEditable);

	/**
	 * Gets the ping of this player.
	 * 
	 * @return Ping value
	 */
	int getPing();

	/**
	 * Gets the windowId of the active container.
	 * 
	 * @return WindowId.
	 */
	int getActiveContainerId();

	String getLangUsed();

	boolean useDefaultLanguage();

	MC_ChatVisibility getChatVisibility();

	FancyMessage sendMessage(String message);

	void sendActionBarMessage(String message);

	void sendTitle(int fadeIn, int stay, int fadeOut, String title,
			String subtitle);

	void sendTabTitle(String header, String footer);

	/**
	 * This method is not recommended.
	 * 
	 * @param packet
	 *            The packet Object (Nms)
	 */
	@Deprecated
	void sendPacket(Object packet) throws IllegalArgumentException;

	Player getHandle();

	Object getMC_Handle();

	/**
	 * Gets the GameProfile.
	 * 
	 * @return Player's GameProfile.
	 */
	MC_GameProfile getMC_GameProfile();
}
