package com.w67clement.mineapi.nms.v1_9_R2.packets.play.out;

import com.w67clement.mineapi.api.ReflectionAPI.*;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.server.v1_9_R2.PacketPlayOutWindowItems;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 03/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class WindowItems_v1_9_R2 extends WindowItems<PacketPlayOutWindowItems>
{
    private static final Field idField = getField(PacketPlayOutWindowItems.class, "a", true);
    private static final Field itemsField = getField(PacketPlayOutWindowItems.class, "b", true);

    public WindowItems_v1_9_R2(PacketPlayOutWindowItems packetPlayOutWindowItems)
    {
        super(packetPlayOutWindowItems);
    }

    public WindowItems_v1_9_R2(int windowId, List<ItemStack> items)
    {
        super(new PacketPlayOutWindowItems());
        this.setWindowId(windowId);
        this.setItems(items);
    }

    public WindowItems_v1_9_R2(int windowId, Inventory inventory)
    {
        this(windowId, Arrays.asList(inventory.getContents()));
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.getHandle());
    }

    @Override
    public int getWindowId()
    {
        return getIntValue(packet, idField);
    }

    @Override
    public WindowItems setWindowId(int windowId)
    {
        setValue(packet, idField, windowId);
        return this;
    }

    @Override
    public List<ItemStack> getItems()
    {
        net.minecraft.server.v1_9_R2.ItemStack[] nmsItems = getValueWithType(packet, itemsField, net.minecraft.server.v1_9_R2.ItemStack[].class);
        if (nmsItems == null)
            return null;
        ArrayList<ItemStack> items = new ArrayList<>();
        for (net.minecraft.server.v1_9_R2.ItemStack item : nmsItems)
        {
            items.add(CraftItemStack.asBukkitCopy(item));
        }
        return items;
    }

    @Override
    public WindowItems setItems(List<ItemStack> items)
    {
        net.minecraft.server.v1_9_R2.ItemStack[] nmsItems = items.stream().map(CraftItemStack::asNMSCopy).collect(Collectors.toList()).toArray(new net.minecraft.server.v1_9_R2.ItemStack[items.size()]);
        setValue(packet, itemsField, nmsItems);
        return this;
    }
}
