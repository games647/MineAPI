package com.aol.w67clement.mineapi.nms.v1_8_R2.entity;

import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.TileEntitySign;

import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.api.ReflectionAPI;
import com.aol.w67clement.mineapi.api.wrappers.ChatVisibilityWrapper;
import com.aol.w67clement.mineapi.entity.player.ClientCommand;
import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.enums.mc.MC_ChatVisibility;
import com.aol.w67clement.mineapi.message.FancyMessage;
import com.aol.w67clement.mineapi.nms.v1_8_R2.play_in.ClientCommand_v1_8_R2;

public class MC_Player_v1_8_R2 implements MC_Player {

	private EntityPlayer player;

	public MC_Player_v1_8_R2(Player player) {
		this.player = ((CraftPlayer) player).getHandle();
	}

	@Override
	public void respawn() {
		if (getHandle().isDead())
			new ClientCommand_v1_8_R2(
					ClientCommand.ClientCommandType.PERFORM_RESPAWN)
					.send(this.player.getBukkitEntity());
	}

	@Override
	public int getPing() {
		return this.player.ping;
	}

	@Override
	public String getLangUsed() {
		try {
			return ReflectionAPI.getStringValue(this.player, "locale", true);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return "en_US";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return "en_US";
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return "en_US";
		} catch (SecurityException e) {
			e.printStackTrace();
			return "en_US";
		}
	}

	@Override
	public boolean useDefaultLanguage() {
		if (this.getLangUsed() != null)
			return this.getLangUsed().equals("en_US");
		return false;
	}

	@Override
	public MC_ChatVisibility getChatVisibility() {
		try {
			return ChatVisibilityWrapper
					.makeMCChatVisibilityByEnumChatVisibility(ReflectionAPI
							.getField(this.player.getClass(), "bR", true).get(
									this.player));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return MC_ChatVisibility.FULL;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return MC_ChatVisibility.FULL;
		} catch (SecurityException e) {
			e.printStackTrace();
			return MC_ChatVisibility.FULL;
		}
	}

	@Override
	public Player getHandle() {
		return this.player.getBukkitEntity();
	}
	
	@Override
	public Object getMC_Handle() {
		return this.player;
	}

	@Override
	public FancyMessage sendMessage(String message) {
		return MineAPI.getNmsManager().getFancyMessage(message);
	}

	@Override
	public void sendActionBarMessage(String message) {
		MineAPI.getNmsManager().getActionBarMessage(message)
				.send(this.getHandle());
	}

	@Override
	public void sendTitle(int fadeIn, int stay, int fadeOut, String title,
			String subtitle) {
		MineAPI.getNmsManager()
				.getTitle(title, subtitle, fadeIn, stay, fadeOut)
				.send(this.getHandle());
	}

	@Override
	public void sendTabTitle(String header, String footer) {
		MineAPI.getNmsManager().getTabTitle(header, footer)
				.send(this.getHandle());
	}

	@Override
	public void openSign(Sign sign) {
		this.openSign(sign, true);
	}

	@Override
	public void openSign(Sign sign, boolean isEditable) {
		TileEntitySign tileEntitySign = (TileEntitySign) ((CraftWorld) sign
				.getLocation().getWorld()).getTileEntityAt(sign.getX(),
				sign.getY(), sign.getZ());
		tileEntitySign.isEditable = isEditable;
		this.player.openSign(tileEntitySign);
	}
}

// End class of Minecraft Player