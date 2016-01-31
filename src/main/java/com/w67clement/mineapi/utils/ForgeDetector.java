package com.w67clement.mineapi.utils;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.PacketHandler;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.event.PacketListener;
import com.w67clement.mineapi.api.event.ReceivePacketEvent;
import com.w67clement.mineapi.api.event.SendPacketEvent;
import com.w67clement.mineapi.api.wrappers.MC_PacketWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.system.messaging.PacketBuffer;
import io.netty.buffer.ByteBuf;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created by w67clement on 28/01/2016.
 */
public class ForgeDetector implements PluginMessageListener, PacketListener
{
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes)
    {
        if (s.equals("FORGE"))
        {
            MineAPI.sendMessageToConsole("DEBUG: FORGE HAVE SENT " + bytes.length + " BYTES.");
        }
    }

    @PacketHandler(listenType = PacketList.PacketPlayInCustomPayload)
    public void onPluginMessage(ReceivePacketEvent<?> e)
    {
        MC_PacketWrapper<?> packetWrapper = e.getPacketWrapper();
        String channel = ReflectionAPI.getStringValue(packetWrapper.getNmsPacket(), ReflectionAPI.getField(packetWrapper.getNmsPacket().getClass(), "a", true));
        MineAPI.sendMessageToConsole("DEBUG: CUSTOM PAYLOAD IN ; CHANNEL: '" + channel + "'");
        if (channel.equals("MC|Brand"))
        {
            PacketBuffer buffer = new PacketBuffer(ReflectionAPI.getValueWithType(packetWrapper.getNmsPacket(), ReflectionAPI.getField(packetWrapper.getNmsPacket().getClass(), "b", true), ByteBuf.class));
            String brand = buffer.readStringFromBuffer(Short.MAX_VALUE);
            MineAPI.sendMessageToConsole("DEBUG: BRAND: " + brand);
            buffer.release();
        }
    }

    @PacketHandler(listenType = PacketList.PacketPlayOutCustomPayload)
    public void onPluginMessageOut(SendPacketEvent<?> e)
    {
        MineAPI.sendMessageToConsole("DEBUG: CUSTOM PAYLOAD OUT ; CHANNEL: '" + ReflectionAPI.getStringValue(e.getPacketWrapper().getNmsPacket(), ReflectionAPI.getField(e.getPacketWrapper().getNmsPacket().getClass(), "a", true)));
    }
}
