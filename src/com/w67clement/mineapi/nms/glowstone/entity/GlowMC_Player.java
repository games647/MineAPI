package com.w67clement.mineapi.nms.glowstone.entity;

import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.flowpowered.networking.Message;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.enums.mc.MC_ChatVisibility;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.nms.glowstone.play_in.GlowClientCommand;
import com.w67clement.mineapi.nms.glowstone.play_out.message.GlowActionBarMessage;
import com.w67clement.mineapi.nms.glowstone.play_out.message.GlowFancyMessage;
import com.w67clement.mineapi.nms.glowstone.play_out.message.GlowTitle;
import com.w67clement.mineapi.nms.glowstone.play_out.tab.GlowTabTitle;

import net.glowstone.GlowChunk;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.inventory.InventoryMonitor;
import net.glowstone.net.message.play.game.PluginMessage;
import net.glowstone.net.message.play.player.CameraMessage;

public class GlowMC_Player extends GlowMC_EntityLiving implements MC_Player
{

	private GlowPlayer player;

	public GlowMC_Player(Player player) {
		super(player);
		this.player = (GlowPlayer) player;
	}

	@Override
	public void reset()
	{
		this.player.reset();
	}

	@Override
	public void respawn()
	{
		if (getHandle().isDead()) new GlowClientCommand(
				ClientCommand.ClientCommandType.PERFORM_RESPAWN)
						.send(this.player);
	}

	@Override
	public void sendChunkChange(Chunk chunk)
	{
		@SuppressWarnings("unchecked")
		Set<GlowChunk.Key> knownChunks = (Set<GlowChunk.Key>) ReflectionAPI
				.getValue(this.player, ReflectionAPI
						.getField(this.player.getClass(), "knownChunks", true));
		knownChunks.add(new GlowChunk.Key(chunk.getX(), chunk.getZ()));
	}

	@Override
	public void spectateEntity(Entity entity)
	{
		if (this.player.getGameMode() != GameMode.SPECTATOR)
		{
			this.player.setGameMode(GameMode.SPECTATOR);
		}
		this.player.getSession().send(new CameraMessage(entity.getEntityId()));
	}

	@Override
	public void spectateEntity(MC_Entity entity)
	{
		this.spectateEntity(entity.getEntityHandle());
	}

	@Override
	public void openBook(ItemStack item)
	{
		if (item.getType() == Material.WRITTEN_BOOK)
		{
			this.player.getSession()
					.send(PluginMessage.fromString("MC|BOpen", ""));
		}
	}

	@Override
	public void openFurnace(Furnace furnace)
	{
		this.player.openInventory(furnace.getInventory());
	}

	@Override
	public void openBrewingStand(BrewingStand bStand)
	{
		this.player.openInventory(bStand.getInventory());
	}

	@Override
	public void openBeacon(Beacon beacon)
	{
		this.player.openInventory(beacon.getInventory());
	}

	@Override
	public void openDispenser(Dispenser dispenser)
	{
		this.player.openInventory(dispenser.getInventory());
	}

	@Override
	public void openHopper(Hopper hopper)
	{
		this.player.openInventory(hopper.getInventory());
	}

	@Override
	public void openSign(Sign sign)
	{
		this.player.openSignEditor(sign.getLocation());
	}

	@Override
	public void openSign(Sign sign, boolean isEditable)
	{
		this.player.openSignEditor(sign.getLocation());
	}

	@Override
	public int getPing()
	{
		return 0;
	}

	@Override
	public int getActiveContainerId()
	{
		InventoryMonitor invMonitor = (InventoryMonitor) ReflectionAPI
				.getValue(this.player, ReflectionAPI
						.getField(this.player.getClass(), "invMonitor", true));
		return invMonitor.getId();
	}

	@Override
	public String getLangUsed()
	{
		return this.player.getSettings().getLocale();
	}

	@Override
	public boolean useDefaultLanguage()
	{
		if (this.getLangUsed() != null)
			return this.getLangUsed().equals("en_US");
		return false;
	}

	@Override
	public MC_ChatVisibility getChatVisibility()
	{
		return MC_ChatVisibility
				.getById(this.player.getSettings().getChatFlags());
	}

	@Override
	public FancyMessage sendMessage(String message)
	{
		return new GlowFancyMessage(message);
	}

	@Override
	public void sendActionBarMessage(String message)
	{
		new GlowActionBarMessage(message).send(this.player);
	}

	@Override
	public void sendTitle(int fadeIn, int stay, int fadeOut, String title,
			String subtitle)
	{
		new GlowTitle(fadeIn, stay, fadeOut, title, subtitle).send(this.player);
	}

	@Override
	public void sendTabTitle(String header, String footer)
	{
		new GlowTabTitle(header, footer).send(this.player);
	}

	@Override
	public void sendPacket(Object packet) throws IllegalArgumentException
	{
		try
		{
			Message glowPacket = (Message) packet;
			this.player.getSession().send(glowPacket);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Player getHandle()
	{
		return this.player;
	}

}
