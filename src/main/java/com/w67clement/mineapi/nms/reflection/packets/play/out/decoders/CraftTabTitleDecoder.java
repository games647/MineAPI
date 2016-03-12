package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.tab.TabTitle;

/**
 * Created by w67clement on 06/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftTabTitleDecoder implements IndividualPacketDecoder<TabTitle>
{
    private static JsonParser json_parser = new JsonParser();

    @Override
    public TabTitle decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutPlayerListHeaderFooter.getPacketName()) || PacketList.PacketPlayOutPlayerListHeaderFooter.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            TabTitle minePacket = MineAPI.getNmsManager().getTabTitle("", "");
            if (MineAPI.isGlowstone())
            {
                Object msg_header = ReflectionAPI.getValue(packet, ReflectionAPI.getField(packet.getClass(), "header", true));
                Object msg_footer = ReflectionAPI.getValue(packet, ReflectionAPI.getField(packet.getClass(), "footer", true));
                String header = ReflectionAPI.invokeMethodWithType(packet, ReflectionAPI.getMethod(msg_header, "asPlaintext"), String.class);
                String footer = ReflectionAPI.invokeMethodWithType(packet, ReflectionAPI.getMethod(msg_footer, "asPlaintext"), String.class);
                minePacket.setHeader(header);
                minePacket.setFooter(footer);
            }
            else
            {
                JsonElement jsonElement_header = json_parser.parse(ChatComponentWrapper.makeJsonByChatComponent(ReflectionAPI.getValue(packet, ReflectionAPI.getField(packet.getClass(), "a", true))));
                JsonElement jsonElement_footer = json_parser.parse(ChatComponentWrapper.makeJsonByChatComponent(ReflectionAPI.getValue(packet, ReflectionAPI.getField(packet.getClass(), "b", true))));
                if (jsonElement_header instanceof JsonObject)
                {
                    JsonObject json_header = (JsonObject) jsonElement_header;
                    minePacket.setHeader(json_header.get("text").getAsString());
                }
                if (jsonElement_footer instanceof JsonObject)
                {
                    JsonObject json_footer = (JsonObject) jsonElement_footer;
                    minePacket.setFooter(json_footer.get("text").getAsString());
                }
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
