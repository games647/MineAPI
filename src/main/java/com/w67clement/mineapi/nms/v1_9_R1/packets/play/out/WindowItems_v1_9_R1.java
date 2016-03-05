package com.w67clement.mineapi.nms.v1_9_R1.packets.play.out;

import com.w67clement.mineapi.api.ReflectionAPI.NmsClass;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.server.v1_9_R1.PacketPlayOutWindowItems;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by w67clement on 03/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class WindowItems_v1_9_R1 extends WindowItems
{
    public WindowItems_v1_9_R1(int windowId, List<ItemStack> items)
    {
        super(windowId, items);
    }

    public WindowItems_v1_9_R1(int windowId, Inventory inventory)
    {
        super(windowId, inventory);
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.constructPacket());
    }

    @Override
    public Object constructPacket()
    {
        List<net.minecraft.server.v1_9_R1.ItemStack> itemsNms = this.items.stream().map(CraftItemStack::asNMSCopy).collect(Collectors.toList());
        return new PacketPlayOutWindowItems(this.windowId, itemsNms);
    }
}
