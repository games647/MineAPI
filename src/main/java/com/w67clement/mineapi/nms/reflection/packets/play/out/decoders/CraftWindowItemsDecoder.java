package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;


import static com.w67clement.mineapi.MineAPI.*;
import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftWindowItemsDecoder implements IndividualPacketDecoder<WindowItems>
{
    @Override
    public WindowItems decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutWindowItems.getPacketName()) || PacketList.PacketPlayOutWindowItems.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            WindowItems minePacket;
            if (isGlowstone())
            {
                int windowId = getIntValue(packet, getField(packet.getClass(), "id", true));
                ItemStack[] contents = getValueWithType(packet, getField(packet.getClass(), "items", true), ItemStack[].class);
                assert contents != null : "Error: [{\"class\":\"NmsPacketReader\",\"method\":\"readPacket_WindowItems(Object)\",\"line\":285,\"error\":\"Error when reading contents.\"}], please contact author and report the bug.";
                minePacket = getNmsManager().getWindowItemsPacket(windowId, Arrays.asList(contents));
            }
            else
            {
                int windowId = getIntValue(packet, getField(packet.getClass(), "a", true));
                Object items = getValue(packet, getField(packet.getClass(), "b", true));
                int itemsLength = Array.getLength(items);
                List<ItemStack> contents = new ArrayList<>();
                for (int i = 0; i < itemsLength; i++)
                {
                    Object nms_Item = Array.get(items, i);
                    contents.add(ItemStackConverter.fromNms(nms_Item));
                }
                minePacket = getNmsManager().getWindowItemsPacket(windowId, contents);
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
