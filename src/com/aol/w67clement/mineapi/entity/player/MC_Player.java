package com.aol.w67clement.mineapi.entity.player;

import org.bukkit.Chunk;
import org.bukkit.block.Beacon;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aol.w67clement.mineapi.entity.MC_EntityLiving;
import com.aol.w67clement.mineapi.enums.mc.MC_ChatVisibility;
import com.aol.w67clement.mineapi.message.FancyMessage;

public interface MC_Player extends MC_EntityLiving {

	public void reset();
	
	/**
	 *  Respawn the player with packet if he are dead
	 */
	public void respawn();
	
	/**
	 *  An optimized version of sendChunkChange method
	 * @param chunk Chunk location
	 * @author Upd4ting
	 */
	public void sendChunkChange(Chunk chunk);
	
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
	 * @return Ping value
	 */
	public int getPing();
	
	public String getLangUsed();
	
	public boolean useDefaultLanguage();
	
	public MC_ChatVisibility getChatVisibility();
	
	public FancyMessage sendMessage(String message);
	
	public void sendActionBarMessage(String message);
	
	public void sendTitle(int fadeIn, int stay, int fadeOut, String title, String subtitle);
	
	public void sendTabTitle(String header, String footer);
	
	public Player getHandle();
	
	public Object getMC_Handle();
}
