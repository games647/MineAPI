package com.w67clement.mineapi.nms;

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
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.entity.villager.MC_Villager;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import com.w67clement.mineapi.inventory.packets.WindowOpen;
import com.w67clement.mineapi.inventory.packets.WindowType;
import com.w67clement.mineapi.message.ActionBarMessage;
import com.w67clement.mineapi.message.FancyMessage;
import com.w67clement.mineapi.message.PacketChat;
import com.w67clement.mineapi.message.Title;
import com.w67clement.mineapi.nms.reflection.packets.handshake.CraftPacketHandshake;
import com.w67clement.mineapi.nms.reflection.packets.play.in.CraftPacketPlayInChat;
import com.w67clement.mineapi.nms.reflection.packets.play.out.CraftPacketChat;
import com.w67clement.mineapi.nms.reflection.packets.status.CraftPacketStatusOutPong;
import com.w67clement.mineapi.nms.reflection.packets.status.CraftPacketStatusOutServerInfo;
import com.w67clement.mineapi.nms.reflection.play_out.inventory.CraftWindowOpen;
import com.w67clement.mineapi.packets.ProtocolState;
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
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * NmsManager is a manager of nms' class!
 *
 * @author w67clement
 */
public abstract class NmsManager
{
    protected boolean isInit = false;
    protected HashMap<Class<?>, IndividualPacketDecoder<?>> decoders = new HashMap<>();

    public abstract void init();

    /**
     * Gets the MineAPI's title object.
     *
     * @param title    The title will be displayed.
     * @param subtitle The subtitle.
     * @param fadeIn   Fade in of the title.
     * @param stay     Stay times of the title.
     * @param fadeOut  Fade out of the title.
     *
     * @return A new MineAPI's title object.
     *
     * @author w67clement
     */
    public abstract Title getTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut);

    /**
     * @param message Message displayed in ActionBar.
     *
     * @return A new MineAPI's action bar message object.
     *
     * @author w67clement
     * @version MineAPI 2.2.0 (Event system v2)
     */
    public ActionBarMessage getActionBarMessage(String message)
    {
        return new ActionBarMessage(message);
    }

    /**
     * @param message First part of the message.
     *
     * @return A new MineAPI's FancyMessage object.
     *
     * @author w67clement
     * @version MineAPI 2.2.0 (Event system v2)
     */
    public FancyMessage getFancyMessage(String message)
    {
        return new FancyMessage(message);
    }

    /**
     * @param content Message in Json.
     *
     * @return A new PacketChat instance.
     *
     * @version MineAPI 2.2.0 (Event system v2)
     * @see PacketChat
     */
    public PacketChat<Object> getPacketChat(String content)
    {
        return new CraftPacketChat(content);
    }

    /**
     * @param content Message in Json.
     * @param data    Data of the message.
     *
     * @return A new PacketChat instance.
     *
     * @version MineAPI 2.2.0 (Event system v2)
     * @see PacketChat
     */
    public PacketChat<Object> getPacketChat(String content, byte data)
    {
        return new CraftPacketChat(content, data);
    }

    public abstract TabTitle getTabTitle(String header, String footer);

    public abstract PacketPlayerInfo getPacketPlayerInfo(PacketPlayerInfo.MC_EnumPlayerInfoAction action, List<PacketPlayerInfoData> data);

    public abstract PacketPlayerInfo getPacketPlayerInfo(PacketPlayerInfo.MC_EnumPlayerInfoAction action, PacketPlayerInfoData... data);

    public abstract WindowItems<?> getWindowItemsPacket(int windowId, List<ItemStack> items);

    public abstract WindowItems<?> getWindowItemsPacket(int windowId, Inventory inventory);

    public WindowOpen getWindowOpenPacket(int windowId, WindowType type, String title, int size)
    {
        return new CraftWindowOpen(windowId, type, title, size);
    }

    public WindowOpen getWindowOpenPacket(int windowId, WindowType type, String title, int size, int horseId)
    {
        return new CraftWindowOpen(windowId, type, title, size, horseId);
    }

    public WindowOpen getWindowOpenPacket(int windowId, Inventory inventory)
    {
        return new CraftWindowOpen(windowId, inventory);
    }

	/* Packet play out - World */

    public abstract PacketExplosion getExplosionPacket(World world, double x, double y, double z, float radius, boolean sound);

    public abstract PacketExplosion getExplosionPacket(Location loc, float radius, boolean sound);

    public abstract PacketWorldBorder getPacketWorldBorder(World world);

	/* Packet play out - Block */

    public abstract PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, Location loc, byte destroyStage);

    public abstract PacketBlockBreakAnimation getPacketBlockBreakAnimation(MC_Player player, int x, int y, int z, byte destroyStage);

    public abstract PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, Location loc, byte destroyStage);

    public abstract PacketBlockBreakAnimation getPacketBlockBreakAnimation(Player player, int x, int y, int z, byte destroyStage);

    public abstract PacketBlockChange getPacketBlockChange(Material material, Location loc);

    public abstract PacketBlockChange getPacketBlockChange(Material material, int data, Location loc);

    public abstract PacketBlockChange getPacketBlockChange(Material material, int x, int y, int z);

    public abstract PacketBlockChange getPacketBlockChange(Material material, int data, int x, int y, int z);

    public abstract PacketBlockAction getPacketBlockAction(Location location, BlockAction action);

    public abstract PacketBlockAction getPacketBlockAction(Location location, BlockAction action, int data);

    public abstract PacketBlockAction getPacketBlockAction(int x, int y, int z, BlockAction action);

    public abstract PacketBlockAction getPacketBlockAction(int x, int y, int z, BlockAction action, int data);

    public abstract PacketUpdateSign getPacketUpdateSign(Sign sign);

    public abstract PacketUpdateSign getPacketUpdateSign(Location location, String[] contents);

    public abstract PacketUpdateSign getPacketUpdateSign(int x, int y, int z, String[] contents);

	/* PACKET PLAY IN */

    public PacketPlayInChat getPacketPlayInChat(String msg)
    {
        return new CraftPacketPlayInChat(msg);
    }

    public abstract ClientCommand getPacketPlayInClientCommand(ClientCommand.ClientCommandType commandType);

	/* MC ENTITY */

    public abstract MC_Entity getMC_Entity(Entity entity);

    public abstract MC_ArmorStand getMC_ArmorStand(ArmorStand armorstand);

    public abstract MC_EntityEnderman getMC_EntityEnderman(Enderman enderman);

    /**
     * Gets a MC_Player, MC_Player is a advanced Player interface.
     *
     * @param player The Bukkit Player Object.
     *
     * @return A new MC_Player Object
     */
    public abstract MC_Player getMCPlayer(Player player);

    public abstract MC_Pig getMCPig(Pig pig);

    public abstract MC_Villager getMCVillager(Villager villager);

	/* World */

    public abstract MC_World getMC_World(World world);

	/* Status */

    public PacketStatusOutServerInfo getPacketStatusOutServerInfo(ServerPingWrapper ping)
    {
        return new CraftPacketStatusOutServerInfo(ping);
    }

    public PacketStatusOutPong getPacketStatusOutPong(long pong)
    {
        return new CraftPacketStatusOutPong(pong);
    }

	/* Handshake */

    public PacketHandshake getPacketHandshake(int protocol, String hostnameOrIp, int port, ProtocolState nextProtocolType)
    {
        return new CraftPacketHandshake(protocol, hostnameOrIp, port, nextProtocolType);
    }

	/* WRAPPERS */

    public abstract ServerPingWrapper getServerPingWrapper();

    public abstract ServerPingWrapper getServerPingWrapper(Object serverPing);

    /* DECODERS */

    public IndividualPacketDecoder<? extends NmsPacket> getPacketDecoder(Class<? extends NmsPacket> packet)
    {
        return this.decoders.get(packet);
    }

    public boolean hasPacketDecoder(Class<? extends NmsPacket> packet)
    {
        return this.decoders.containsKey(packet);
    }
}
