package com.w67clement.mineapi.nms.v1_9_R1.entity;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatVisibilityWrapper;
import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.entity.player.EnumHand;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.enums.mc.MC_ChatVisibility;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.nms.reflection.packets.play.in.CraftClientCommand;
import com.w67clement.mineapi.system.MC_GameProfile;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.block.*;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by w67clement on 01/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class MC_Player_v1_9_R1 extends MC_EntityLiving_v1_9_R1 implements MC_Player
{

    private EntityPlayer player;

    public MC_Player_v1_9_R1(Player player)
    {
        super(player);
        this.player = ((CraftPlayer) player).getHandle();
    }

    @Override
    public void reset()
    {
        this.player.reset();
    }

    @Override
    public void sendChunkChange(Chunk chunk)
    {
    }

    @Override
    public void respawn()
    {
        if (getHandle().isDead())
            new CraftClientCommand(ClientCommand.ClientCommandType.PERFORM_RESPAWN).send(this.player.getBukkitEntity());
    }

    @Override
    public void spectateEntity(Entity entity)
    {
        if (this.getHandle().getGameMode() != GameMode.SPECTATOR)
        {
            this.getHandle().setGameMode(GameMode.SPECTATOR);
        }
        this.player.playerConnection.sendPacket(new PacketPlayOutCamera(((CraftEntity) entity).getHandle()));
    }

    @Override
    public void spectateEntity(MC_Entity entity)
    {
        this.spectateEntity(entity.getEntityHandle());
    }

    @Override
    public void openBook(ItemStack item, EnumHand hand)
    {
        net.minecraft.server.v1_9_R1.EnumHand nmsHand;
        switch (hand)
        {
            case MAIN_HAND:
                nmsHand = net.minecraft.server.v1_9_R1.EnumHand.MAIN_HAND;
                break;
            case OFF_HAND:
                nmsHand = net.minecraft.server.v1_9_R1.EnumHand.OFF_HAND;
                break;
            default:
                nmsHand = net.minecraft.server.v1_9_R1.EnumHand.MAIN_HAND;
                break;
        }
        this.player.a(CraftItemStack.asNMSCopy(item), nmsHand);
    }

    @Override
    public void openFurnace(Furnace furnace)
    {
        TileEntityFurnace tileEntity = (TileEntityFurnace) ((CraftWorld) furnace.getWorld()).getTileEntityAt(furnace.getX(), furnace.getY(), furnace.getZ());
        this.player.openTileEntity(tileEntity);
    }

    @Override
    public void openBrewingStand(BrewingStand bStand)
    {
        TileEntityBrewingStand tileEntity = (TileEntityBrewingStand) ((CraftWorld) bStand.getWorld()).getTileEntityAt(bStand.getX(), bStand.getY(), bStand.getZ());
        this.player.openTileEntity(tileEntity);
    }

    @Override
    public void openBeacon(Beacon beacon)
    {
        TileEntityBeacon tileEntity = (TileEntityBeacon) ((CraftWorld) beacon.getWorld()).getTileEntityAt(beacon.getX(), beacon.getY(), beacon.getZ());
        this.player.openTileEntity(tileEntity);
    }

    @Override
    public void openDispenser(Dispenser dispenser)
    {
        TileEntityDispenser tileEntity = (TileEntityDispenser) ((CraftWorld) dispenser.getWorld()).getTileEntityAt(dispenser.getX(), dispenser.getY(), dispenser.getZ());
        this.player.openTileEntity(tileEntity);
    }

    @Override
    public void openHopper(Hopper hopper)
    {
        TileEntityHopper tileEntity = (TileEntityHopper) ((CraftWorld) hopper.getWorld()).getTileEntityAt(hopper.getX(), hopper.getY(), hopper.getZ());
        this.player.openTileEntity(tileEntity);
    }

    @Override
    public void openSign(Sign sign)
    {
        this.openSign(sign, true);
    }

    @Override
    public void openSign(Sign sign, boolean isEditable)
    {
        TileEntitySign tileEntitySign = (TileEntitySign) ((CraftWorld) sign.getLocation().getWorld()).getTileEntityAt(sign.getX(), sign.getY(), sign.getZ());
        tileEntitySign.isEditable = isEditable;
        this.player.openSign(tileEntitySign);
    }

    @Override
    public int getPing()
    {
        return this.player.ping;
    }

    @Override
    public int getActiveContainerId()
    {
        return this.player.activeContainer.windowId;
    }

    @Override
    public String getLangUsed()
    {
        try
        {
            return ReflectionAPI.getStringValue(this.player, "locale", true);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            return "en_US";
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return "en_US";
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
            return "en_US";
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
            return "en_US";
        }
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
        return ChatVisibilityWrapper.makeMCChatVisibilityByEnumChatVisibility(this.player.getChatFlags());
    }

    @Override
    public FancyMessage sendMessage(String message)
    {
        return MineAPI.getNmsManager().getFancyMessage(message);
    }

    @Override
    public void sendActionBarMessage(String message)
    {
        MineAPI.getNmsManager().getActionBarMessage(message).send(this.getHandle());
    }

    @Override
    public void sendTitle(int fadeIn, int stay, int fadeOut, String title, String subtitle)
    {
        MineAPI.getNmsManager().getTitle(title, subtitle, fadeIn, stay, fadeOut).send(this.getHandle());
    }

    @Override
    public void sendTabTitle(String header, String footer)
    {
        MineAPI.getNmsManager().getTabTitle(header, footer).send(this.getHandle());
    }

    @Override
    public Player getHandle()
    {
        return this.player.getBukkitEntity();
    }

    @Override
    public Object getMC_Handle()
    {
        return this.player;
    }

    @Override
    public void sendPacket(Object packet) throws IllegalArgumentException
    {
        Packet<?> nmsPacket;
        try
        {
            nmsPacket = (Packet<?>) packet;
        }
        catch (ClassCastException e)
        {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        this.player.playerConnection.sendPacket(nmsPacket);
    }

    @Override
    public MC_GameProfile getMC_GameProfile()
    {
        return MC_GameProfile.getByMojangObject(this.player.getProfile());
    }
}
