package com.aol.w67clement.mineapi.entity.player;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.enums.mc.MC_ChatVisibility;
import com.aol.w67clement.mineapi.message.FancyMessage;

public interface MC_Player {

	public void respawn();
	
	public void openSign(Sign sign);
	
	public void openSign(Sign sign, boolean isEditable);
	
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
