package com.w67clement.mineapi.system.messaging;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * Created by w67clement on 24/02/2016.
 * <p>
 * Class of project: MineAPI
 */
public class MessagingListener implements PluginMessageListener
{
    private MessagingManager messagingManager;

    MessagingListener(MessagingManager messagingManager)
    {
        this.messagingManager = messagingManager;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes)
    {
        if (!channel.equals("MineMessaging"))
            return;
        PacketBuffer buffer = new PacketBuffer(Unpooled.wrappedBuffer(bytes));
        String plugin = buffer.readStringFromBuffer(64);
        int packetId = buffer.readVarIntFromBuffer();

        if (this.messagingManager.getPacketRegistry().hasPlugin(plugin))
        {
            if (this.messagingManager.getPacketRegistry().hasPacket(plugin, packetId))
            {
                MessagingPacket packet = (MessagingPacket) ReflectionAPI.newInstance(ReflectionAPI.getConstructor(this.messagingManager.getPacketRegistry().getPacketById(plugin, packetId)));
                assert packet != null : "Error: [{\"class\":\"MessagingListener\",\"method\":\"onPluginMessageReceived(String, Player, byte[])\",\"line\":36,\"error\":\"Anti-NullPointerException failed.\"}], please contact author and report the bug.";
                packet.decode(buffer);
                MineAPI.sendMessageToConsole(MineAPI.DEBUG_PREFIX + "MineMessaging Packet received: " + packet.getClass().getSimpleName() + ", id: " + packetId + ", plugin:" + plugin, true);
                packet.handle();
            }
        }
    }
}
