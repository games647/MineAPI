package com.w67clement.mineapi.inventory.packets;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public abstract class WindowItems<T> extends PacketSender<T>
{

    public WindowItems(T packet)
    {
        super(packet);
    }

    public abstract int getWindowId();

    public abstract WindowItems setWindowId(int windowId);

    public abstract List<ItemStack> getItems();

    public abstract WindowItems setItems(List<ItemStack> items);

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

}
