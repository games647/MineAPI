package com.w67clement.mineapi.nms.reflection.play_out.tab;

import com.google.gson.JsonObject;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.tab.TabTitle;
import java.lang.reflect.Constructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Set a title (header and footer) of the player list Tab.
 *
 * @author w67clement
 * @version 3.0 - For none NMS version.
 */
public class CraftTabTitle extends TabTitle
{
    private static Class<?> tabtitlePacketClass;

    static
    {
        if (MineAPI.isGlowstone())
        {
            tabtitlePacketClass = ReflectionAPI.getClass("net.glowstone.net.message.play.game.UserListHeaderFooterMessage");
        }
        else
            tabtitlePacketClass = getNmsClass("PacketPlayOutPlayerListHeaderFooter");
    }

    public CraftTabTitle(String header, String footer)
    {
        super(header, footer);
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYOUT;
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.constructPacket());
    }

    @Override
    public Object constructPacket()
    {
        if (MineAPI.isSpigot())
        {
            return this.constructPacket_Bukkit();
        }
        else if (MineAPI.isGlowstone())
        {
            return this.constructPacket_Glowstone();
        }
        return this.constructPacket_Bukkit();
    }

    private Object constructPacket_Bukkit()
    {
        this.header = ChatColor.translateAlternateColorCodes('&', this.header);
        this.footer = ChatColor.translateAlternateColorCodes('&', this.footer);

        JsonObject jsonHeader = new JsonObject(), jsonFooter = new JsonObject();
        jsonHeader.addProperty("text", this.header + ChatColor.RESET);
        jsonFooter.addProperty("text", this.footer + ChatColor.RESET);

        // Create packet
        Object tabTitlePacket = newInstance(getConstructor(tabtitlePacketClass));

        // Convert JSON strings to IChatBaseComponent objects
        Object headerComponent = invokeMethod(null, getMethod(NmsClass.getChatSerializerClass(), "a", String.class), jsonHeader.toString());
        Object footerComponent = invokeMethod(null, getMethod(NmsClass.getChatSerializerClass(), "a", String.class), jsonFooter.toString());
        // Set fields
        if ((this.header != null) && (!this.header.isEmpty()))
            setValue(tabTitlePacket, getField(tabtitlePacketClass, "a", true), headerComponent);
        if ((this.footer != null) && (!this.footer.isEmpty()))
            setValue(tabTitlePacket, getField(tabtitlePacketClass, "b", true), footerComponent);
        return tabTitlePacket;
    }

    private Object constructPacket_Glowstone()
    {
        this.header = ChatColor.translateAlternateColorCodes('&', this.header);
        this.footer = ChatColor.translateAlternateColorCodes('&', this.footer);

        Class<?> textMsgClass = ReflectionAPI.getClass("net.glowstone.util.TextMessage");
        Class<?> jsonObject = ReflectionAPI.getClass("org.json.simple.JSONObject");
        Class<?> jsonParserClass = ReflectionAPI.getClass("org.json.simple.parser.JSONParser");
        Object jsonParser = newInstance(getConstructor(jsonParserClass));
        Constructor<?> textMsgConstructor = getConstructor(textMsgClass, jsonObject);

        JsonObject jsonHeader = new JsonObject(), jsonFooter = new JsonObject();
        jsonHeader.addProperty("text", this.header + ChatColor.RESET);
        jsonFooter.addProperty("text", this.footer + ChatColor.RESET);

        Object header = newInstance(textMsgConstructor, invokeMethod(jsonParser, getMethod(jsonParserClass, "parse", String.class), jsonHeader.toString()));
        Object footer = newInstance(textMsgConstructor, invokeMethod(jsonParser, getMethod(jsonParserClass, "parse", String.class), jsonFooter.toString()));

        // Create packet
        Object tabTitlePacket = newInstance(getConstructor(tabtitlePacketClass, textMsgClass, textMsgClass), header, footer);
        return tabTitlePacket;
    }

}
