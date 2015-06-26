package com.aol.w67clement.mineapi.nms.v1_8_R3.entity;

import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.TileEntityBeacon;
import net.minecraft.server.v1_8_R3.TileEntityBrewingStand;
import net.minecraft.server.v1_8_R3.TileEntityDispenser;
import net.minecraft.server.v1_8_R3.TileEntityFurnace;
import net.minecraft.server.v1_8_R3.TileEntityHopper;
import net.minecraft.server.v1_8_R3.TileEntitySign;

import org.bukkit.Chunk;
import org.bukkit.block.Beacon;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.api.ReflectionAPI;
import com.aol.w67clement.mineapi.api.wrappers.ChatVisibilityWrapper;
import com.aol.w67clement.mineapi.entity.player.ClientCommand;
import com.aol.w67clement.mineapi.entity.player.MC_Player;
import com.aol.w67clement.mineapi.enums.mc.MC_ChatVisibility;
import com.aol.w67clement.mineapi.message.FancyMessage;
import com.aol.w67clement.mineapi.nms.v1_8_R3.play_in.ClientCommand_v1_8_R3;

public class MC_Player_v1_8_R3 implements MC_Player {

	private EntityPlayer player;

	public MC_Player_v1_8_R3(Player player) {
		this.player = ((CraftPlayer) player).getHandle();
	}

	@Override
	public void reset() {
		this.player.reset();
	}

	@Override
	public void sendChunkChange(Chunk chunk) {
		this.player.chunkCoordIntPairQueue.add(new ChunkCoordIntPair(chunk
				.getX(), chunk.getZ()));
	}

	@Override
	public void respawn() {
		if (getHandle().isDead())
			new ClientCommand_v1_8_R3(
					ClientCommand.ClientCommandType.PERFORM_RESPAWN)
					.send(this.player.getBukkitEntity());
	}

	@Override
	public void openBook(ItemStack item) {
		this.player.openBook(CraftItemStack.asNMSCopy(item));
	}

	@Override
	public void openFurnace(Furnace furnace) {
		TileEntityFurnace tileEntity = (TileEntityFurnace) ((CraftWorld) furnace
				.getWorld()).getTileEntityAt(furnace.getX(), furnace.getY(),
				furnace.getZ());
		this.player.openTileEntity(tileEntity);
	}

	@Override
	public void openBrewingStand(BrewingStand bStand) {
		TileEntityBrewingStand tileEntity = (TileEntityBrewingStand) ((CraftWorld) bStand
				.getWorld()).getTileEntityAt(bStand.getX(), bStand.getY(),
				bStand.getZ());
		this.player.openTileEntity(tileEntity);
	}

	@Override
	public void openBeacon(Beacon beacon) {
		TileEntityBeacon tileEntity = (TileEntityBeacon) ((CraftWorld) beacon
				.getWorld()).getTileEntityAt(beacon.getX(), beacon.getY(),
				beacon.getZ());
		this.player.openTileEntity(tileEntity);
	}

	@Override
	public void openDispenser(Dispenser dispenser) {
		TileEntityDispenser tileEntity = (TileEntityDispenser) ((CraftWorld) dispenser
				.getWorld()).getTileEntityAt(dispenser.getX(),
				dispenser.getY(), dispenser.getZ());
		this.player.openTileEntity(tileEntity);
	}

	@Override
	public void openHopper(Hopper hopper) {
		TileEntityHopper tileEntity = (TileEntityHopper) ((CraftWorld) hopper
				.getWorld()).getTileEntityAt(hopper.getX(), hopper.getY(),
				hopper.getZ());
		this.player.openTileEntity(tileEntity);
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

	@Override
	public int getPing() {
		return this.player.ping;
	}

	@Override
	public int getEntityId() {
		return this.player.getId();
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
		return ChatVisibilityWrapper
				.makeMCChatVisibilityByEnumChatVisibility(this.player
						.getChatFlags());
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
	public Player getHandle() {
		return this.player.getBukkitEntity();
	}

	@Override
	public Object getMC_Handle() {
		return this.player;
	}

	public void setAIDisabled(boolean ai) {
	}

	public boolean isAIDisabled() {
		return false;
	}
}
