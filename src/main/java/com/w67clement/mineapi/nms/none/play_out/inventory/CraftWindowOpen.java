package com.w67clement.mineapi.nms.none.play_out.inventory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.inventory.packets.WindowOpen;
import com.w67clement.mineapi.inventory.packets.WindowType;
import java.lang.reflect.Constructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by w67clement on 09/02/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftWindowOpen extends WindowOpen
{
    private static Class<?> packetClass;
    private static Constructor<?> packetConstructor;

    static
    {
        if (MineAPI.isGlowstone())
        {
            packetClass = ReflectionAPI.getClass("net.glowstone.net.message.play.inv.OpenWindowMessage");
            packetConstructor = ReflectionAPI.getConstructor(packetClass, int.class, String.class, String.class, int.class);
        }
        else
        {
            packetClass = ReflectionAPI.getNmsClass("PacketPlayOutOpenWindow");
            packetConstructor = ReflectionAPI.getConstructor(packetClass, int.class, String.class, ReflectionAPI.NmsClass.getIChatBaseComponentClass(), int.class, int.class);
        }
    }

    public CraftWindowOpen(int id, WindowType type, String title, int size)
    {
        super(id, type, title, size);
    }

    public CraftWindowOpen(int id, WindowType type, String title, int size, int horseId)
    {
        super(id, type, title, size, horseId);
    }

    public CraftWindowOpen(int id, Inventory inventory)
    {
        super(id, inventory);
    }

    @Override
    public void send(Player player)
    {
        ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
    }

    @Override
    public Object constructPacket()
    {
        if (MineAPI.isSpigot())
        {
            return this.constructBukkit();
        }
        else if (MineAPI.isGlowstone())
            return this.constructGlowstone();
        return this.constructBukkit();
    }

    private Object constructBukkit()
    {
        return ReflectionAPI.newInstance(packetConstructor, this.id, this.type.getMCValue(), ChatComponentWrapper.makeChatComponentByJson(this.title), this.size, this.horseId);
    }

    private Object constructGlowstone()
    {
        Object packet = ReflectionAPI.newInstance(packetConstructor, this.id, this.type.getMCValue(), null, this.size);

        Class<?> jsonObject = ReflectionAPI.getClass("org.json.simple.JSONObject");
        Class<?> jsonParserClass = ReflectionAPI.getClass("org.json.simple.parser.JSONParser");
        Object jsonParser = ReflectionAPI.newInstance(ReflectionAPI.getConstructor(jsonParserClass));
        Class<?> textMsgClass = ReflectionAPI.getClass("net.glowstone.util.TextMessage");
        Constructor<?> textMsgConstructor = ReflectionAPI.getConstructor(textMsgClass, jsonObject);

        JsonObject json = (JsonObject) new JsonParser().parse(this.title);

        Object title = ReflectionAPI.newInstance(textMsgConstructor, ReflectionAPI.invokeMethod(jsonParser, ReflectionAPI.getMethod(jsonParserClass, "parse", String.class), json.toString()));

        ReflectionAPI.setValue(packet, ReflectionAPI.getField(packetClass, "title", true), title);

        if (this.type.equals(WindowType.HORSE))
        {
            ReflectionAPI.setValue(packet, ReflectionAPI.getField(packetClass, "entityId", true), this.horseId);
        }

        return packet;
    }
}
