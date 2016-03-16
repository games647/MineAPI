package com.w67clement.mineapi.nms.reflection;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.block.BlockAction;
import com.w67clement.mineapi.block.PacketBlockAction;
import com.w67clement.mineapi.block.PacketBlockBreakAnimation;
import com.w67clement.mineapi.block.PacketBlockChange;
import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.animals.MC_Pig;
import com.w67clement.mineapi.entity.monster.MC_EntityEnderman;
import com.w67clement.mineapi.entity.others.MC_ArmorStand;
import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.entity.player.ClientCommand.ClientCommandType;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.entity.villager.MC_Villager;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import com.w67clement.mineapi.inventory.packets.WindowOpen;
import com.w67clement.mineapi.message.PacketChat;
import com.w67clement.mineapi.message.Title;
import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.nms.reflection.packets.handshake.CraftPacketHandshakeDecoder;
import com.w67clement.mineapi.nms.reflection.packets.play.in.CraftClientCommand;
import com.w67clement.mineapi.nms.reflection.packets.play.in.decoders.CraftClientCommandDecoder;
import com.w67clement.mineapi.nms.reflection.packets.play.in.decoders.CraftPacketPlayInChatDecoder;
import com.w67clement.mineapi.nms.reflection.packets.play.out.CraftPacketBlockBreakAnimation;
import com.w67clement.mineapi.nms.reflection.packets.play.out.CraftPacketPlayerInfo;
import com.w67clement.mineapi.nms.reflection.packets.play.out.CraftPacketUpdateSign;
import com.w67clement.mineapi.nms.reflection.packets.play.out.decoders.*;
import com.w67clement.mineapi.nms.reflection.packets.status.CraftPacketStatusOutPongDecoder;
import com.w67clement.mineapi.nms.reflection.packets.status.CraftPacketStatusOutServerInfoDecoder;
import com.w67clement.mineapi.nms.reflection.play_out.block.CraftPacketBlockAction;
import com.w67clement.mineapi.nms.reflection.play_out.block.CraftPacketBlockChange;
import com.w67clement.mineapi.nms.reflection.play_out.message.CraftTitle;
import com.w67clement.mineapi.nms.reflection.play_out.tab.CraftTabTitle;
import com.w67clement.mineapi.nms.reflection.play_out.world.CraftPacketExplosion;
import com.w67clement.mineapi.nms.reflection.play_out.world.CraftPacketWorldBorder;
import com.w67clement.mineapi.nms.reflection.wrappers.CraftServerPingWrapper;
import com.w67clement.mineapi.packets.handshake.PacketHandshake;
import com.w67clement.mineapi.packets.play.in.PacketPlayInChat;
import com.w67clement.mineapi.packets.play.out.PacketUpdateSign;
import com.w67clement.mineapi.packets.status.PacketStatusOutPong;
import com.w67clement.mineapi.packets.status.PacketStatusOutServerInfo;
import com.w67clement.mineapi.tab.PacketPlayerInfo;
import com.w67clement.mineapi.tab.PacketPlayerInfo.PacketPlayerInfoData;
import com.w67clement.mineapi.tab.TabTitle;
import com.w67clement.mineapi.world.MC_World;
import com.w67clement.mineapi.world.PacketExplosion;
import com.w67clement.mineapi.world.PacketWorldBorder;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftNmsManager extends NmsManager
{

    public void init()
    {
        if (!this.isInit)
        {
            /* DECODERS */
            // Handshake
            this.decoders.put(PacketHandshake.class, new CraftPacketHandshakeDecoder());
            // Status
            this.decoders.put(PacketStatusOutServerInfo.class, new CraftPacketStatusOutServerInfoDecoder());
            this.decoders.put(PacketStatusOutPong.class, new CraftPacketStatusOutPongDecoder());
            // Play (IN)
            this.decoders.put(PacketPlayInChat.class, new CraftPacketPlayInChatDecoder());
            this.decoders.put(ClientCommand.class, new CraftClientCommandDecoder());
            // Play (OUT)
            this.decoders.put(PacketBlockBreakAnimation.class, new CraftPacketBlockBreakAnimationDecoder());
            this.decoders.put(PacketChat.class, new CraftPacketChatDecoder());
            this.decoders.put(PacketExplosion.class, new CraftPacketExplosionDecoder());
            this.decoders.put(WindowOpen.class, new CraftWindowOpenDecoder());
            this.decoders.put(TabTitle.class, new CraftTabTitleDecoder());
            this.decoders.put(PacketUpdateSign.class, new CraftPacketUpdateSignDecoder());
            this.decoders.put(WindowItems.class, new CraftWindowItemsDecoder());
            this.isInit = true;
        }
        else
            throw new RuntimeException("NmsManager is already initialized");
    }

    @Override
    public Title getTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        return new CraftTitle(fadeIn, stay, fadeOut, title, subtitle);
    }

    @Override
    public TabTitle getTabTitle(String header, String footer)
    {
        return new CraftTabTitle(header, footer);
    }

    @Override
    public PacketPlayerInfo getPacketPlayerInfo(PacketPlayerInfo.MC_EnumPlayerInfoAction action, List<PacketPlayerInfoData> data)
    {
        return new CraftPacketPlayerInfo(action, data);
    }

    @Override
    public PacketPlayerInfo getPacketPlayerInfo(PacketPlayerInfo.MC_EnumPlayerInfoAction action, PacketPlayerInfoData... data)
    {
        return new CraftPacketPlayerInfo(action, Arrays.asList(data));
    }

    @Override
    public WindowItems getWindowItemsPacket(int windowId, List<ItemStack> items)
    {
        return null;
    }

    @Override
    public WindowItems getWindowItemsPacket(int windowId, Inventory inventory)
    {
        return null;
    }

    @Override
    public PacketExplosion getExplosionPacket(World world, double x, double y, double z, float radius, boolean sound)
    {
        return new CraftPacketExplosion(world, x, y, z, radius, sound);
    }

    @Override
    public PacketExplosion getExplosionPacket(Location loc, float radius, boolean sound)
    {
        return new CraftPacketExplosion(loc, radius, sound);
    }

    @Override
    public PacketWorldBorder getPacketWorldBorder(World world)
    {
        return new CraftPacketWorldBorder(world);
    }

    @Override
    public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, Location loc, byte destroyStage)
    {
        return new CraftPacketBlockBreakAnimation(player, loc, destroyStage);
    }

    @Override
    public PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, int x, int y, int z, byte destroyStage)
    {
        return new CraftPacketBlockBreakAnimation(player, x, y, z, destroyStage);
    }

    @Override
    public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, Location loc, byte destroyStage)
    {
        return new CraftPacketBlockBreakAnimation(player, loc, destroyStage);
    }

    @Override
    public PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, int x, int y, int z, byte destroyStage)
    {
        return new CraftPacketBlockBreakAnimation(player, x, y, z, destroyStage);
    }

    @Override
    public PacketBlockChange getPacketBlockChange(Material material, Location loc)
    {
        return new CraftPacketBlockChange(material, loc);
    }

    @Override
    public PacketBlockChange getPacketBlockChange(Material material, int data, Location loc)
    {
        return new CraftPacketBlockChange(material, data, loc);
    }

    @Override
    public PacketBlockChange getPacketBlockChange(Material material, int x, int y, int z)
    {
        return new CraftPacketBlockChange(material, x, y, z);
    }

    @Override
    public PacketBlockChange getPacketBlockChange(Material material, int data, int x, int y, int z)
    {
        return new CraftPacketBlockChange(material, data, x, y, z);
    }

    @Override
    public PacketBlockAction getPacketBlockAction(Location location, BlockAction action)
    {
        return new CraftPacketBlockAction(location, action);
    }

    @Override
    public PacketBlockAction getPacketBlockAction(Location location, BlockAction action, int data)
    {
        return new CraftPacketBlockAction(location, action, data);
    }

    @Override
    public PacketBlockAction getPacketBlockAction(int x, int y, int z, BlockAction action)
    {
        return new CraftPacketBlockAction(x, y, z, action);
    }

    @Override
    public PacketBlockAction getPacketBlockAction(int x, int y, int z, BlockAction action, int data)
    {
        return new CraftPacketBlockAction(x, y, z, action, data);
    }

    public PacketUpdateSign getPacketUpdateSign(Sign sign)
    {
        return new CraftPacketUpdateSign(sign);
    }

    public PacketUpdateSign getPacketUpdateSign(Location location, String[] contents)
    {
        return new CraftPacketUpdateSign(location, contents);
    }

    public PacketUpdateSign getPacketUpdateSign(int x, int y, int z, String[] contents)
    {
        return new CraftPacketUpdateSign(x, y, z, contents);
    }

    @Override
    public ClientCommand getPacketPlayInClientCommand(ClientCommandType commandType)
    {
        return new CraftClientCommand(commandType);
    }

    @Override
    public MC_Entity getMC_Entity(Entity entity)
    {
        return null;
    }

    @Override
    public MC_ArmorStand getMC_ArmorStand(ArmorStand armorstand)
    {
        return null;
    }

    @Override
    public MC_EntityEnderman getMC_EntityEnderman(Enderman enderman)
    {
        return null;
    }

    @Override
    public MC_Player getMCPlayer(Player player)
    {
        return null;
    }

    @Override
    public MC_Pig getMCPig(Pig pig)
    {
        return null;
    }

    @Override
    public MC_Villager getMCVillager(Villager villager)
    {
        return null;
    }

	/* World */

    @Override
    public MC_World getMC_World(World world)
    {
        return null;
    }

    @Override
    public ServerPingWrapper getServerPingWrapper()
    {
        return new CraftServerPingWrapper();
    }

    @Override
    public ServerPingWrapper getServerPingWrapper(Object serverPing)
    {
        return this.getServerPingWrapper();
    }

}
