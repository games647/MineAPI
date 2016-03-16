package com.w67clement.mineapi.nms.reflection.play_out.tab;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.tab.TabTitle;
import java.lang.reflect.Field;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Set a title (header and footer) of the player list Tab.
 *
 * @author w67clement
 * @version 3.0 - For none NMS version.
 */
public class CraftTabTitle extends TabTitle<Object>
{
    private static final JsonParser jsonParser = new JsonParser();
    private static final Class<?> packetClass;
    private static final Field headerField;
    private static final Field footerField;

    static
    {
        packetClass = getNmsClass("PacketPlayOutPlayerListHeaderFooter");
        headerField = getField(packetClass, "a", true);
        footerField = getField(packetClass, "b", true);
    }

    public CraftTabTitle(Object packet)
    {
        super(packet);
    }

    public CraftTabTitle(String header, String footer)
    {
        super(SunUnsafe.newInstance(packetClass));
        this.setHeader(header);
        this.setFooter(footer);
    }

    @Override
    public String getHeader()
    {
        Object header = getValue(packet, headerField);
        if (header == null)
            return null;
        JsonObject jsonHeader = (JsonObject) jsonParser.parse(ChatComponentWrapper.makeJsonByChatComponent(header));
        return jsonHeader.get("text").getAsString();
    }

    @Override
    public TabTitle setHeader(String header)
    {
        if (header == null || header.isEmpty())
        {
            setValue(packet, headerField, null);
            return this;
        }
        setValue(packet, headerField, ChatComponentWrapper.makeChatComponentByText(ChatColor.translateAlternateColorCodes('&', header) + ChatColor.RESET));
        return this;
    }

    @Override
    public String getFooter()
    {
        Object header = getValue(packet, footerField);
        if (header == null)
            return null;
        JsonObject jsonHeader = (JsonObject) jsonParser.parse(ChatComponentWrapper.makeJsonByChatComponent(header));
        return jsonHeader.get("text").getAsString();
    }

    @Override
    public TabTitle setFooter(String footer)
    {
        if (footer == null || footer.isEmpty())
        {
            setValue(packet, footerField, null);
            return this;
        }
        setValue(packet, footerField, ChatComponentWrapper.makeChatComponentByText(ChatColor.translateAlternateColorCodes('&', footer) + ChatColor.RESET));
        return this;
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.getHandle());
    }
}
