package com.w67clement.mineapi.nms.none.packets.play.out;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.packets.play.out.PacketUpdateSign;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 14/02/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketUpdateSign extends PacketUpdateSign
{
    private static Class<?> packetClass;

    static
    {
        if (MineAPI.isGlowstone())
        {
            packetClass = ReflectionAPI.getClass("net.glowstone.net.message.play.game.UpdateSignMessage");
        }
        else
        {
            packetClass = getNmsClass("PacketPlayOutUpdateSign");
        }
    }

    public CraftPacketUpdateSign(Sign sign)
    {
        super(sign);
        this.loadSignContents(sign);
    }

    public CraftPacketUpdateSign(Location location, String[] contents)
    {
        super(location, contents);
    }

    public CraftPacketUpdateSign(int x, int y, int z, String[] contents)
    {
        super(x, y, z, contents);
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.constructPacket());
    }

    @Override
    public Object constructPacket()
    {
        if (MineAPI.isGlowstone())
        {
            return this.constructPacketGlowstone();
        }
        return this.constructPacketBukkit();
    }

    private Object constructPacketBukkit()
    {
        Object packet = newInstance(getConstructor(packetClass));
        setValue(packet, getField(packetClass, "b", true), BlockPositionWrapper.fromLocation(this.location).toBlockPosition());
        Object array = Array.newInstance(NmsClass.getIChatBaseComponentClass(), 4);
        Array.set(array, 0, ChatComponentWrapper.makeChatComponentByJson(this.getLine(0)));
        Array.set(array, 1, ChatComponentWrapper.makeChatComponentByJson(this.getLine(1)));
        Array.set(array, 2, ChatComponentWrapper.makeChatComponentByJson(this.getLine(2)));
        Array.set(array, 3, ChatComponentWrapper.makeChatComponentByJson(this.getLine(3)));
        setValue(packet, getField(packetClass, "c", true), array);
        return packet;
    }

    private Object constructPacketGlowstone()
    {
        Object packet = newInstance(getConstructor(packetClass));
        setValue(packet, getField(packetClass, "x", true), this.location.getBlockX());
        setValue(packet, getField(packetClass, "y", true), this.location.getBlockY());
        setValue(packet, getField(packetClass, "z", true), this.location.getBlockZ());
        Object array = Array.newInstance(ReflectionAPI.getClass("net.glowstone.util.TextMessage"), 4);
        Array.set(array, 0, convertToTextMessage(this.getLine(0)));
        Array.set(array, 1, convertToTextMessage(this.getLine(1)));
        Array.set(array, 2, convertToTextMessage(this.getLine(2)));
        Array.set(array, 3, convertToTextMessage(this.getLine(3)));
        setValue(packet, getField(packetClass, "message", true), array);
        return packet;
    }

    private Object convertToTextMessage(String json)
    {
        Class<?> jsonObject = ReflectionAPI.getClass("org.json.simple.JSONObject");
        Class<?> jsonParserClass = ReflectionAPI.getClass("org.json.simple.parser.JSONParser");
        Object jsonParser = newInstance(ReflectionAPI.getConstructor(jsonParserClass));
        Class<?> textMsgClass = ReflectionAPI.getClass("net.glowstone.util.TextMessage");
        Constructor<?> textMsgConstructor = getConstructor(textMsgClass, jsonObject);

        JsonObject jsonObj = (JsonObject) new JsonParser().parse(json);

        return newInstance(textMsgConstructor, invokeMethod(jsonParser, getMethod(jsonParserClass, "parse", String.class), jsonObj.toString()));
    }

    private void loadSignContents(Sign sign)
    {
        if (MineAPI.isGlowstone())
        {
            Object tileEntity = invokeMethod(sign, getMethod(sign, "getTileEntity", true));
            Object array = getValue(tileEntity, getField(tileEntity.getClass(), "lines", true));
            Object msg0 = Array.get(array, 0);
            this.setLine(0, invokeMethodWithType(msg0, getMethod(msg0, "encode"), String.class));
            Object msg1 = Array.get(array, 1);
            this.setLine(1, invokeMethodWithType(msg1, getMethod(msg1, "encode"), String.class));
            Object msg2 = Array.get(array, 2);
            this.setLine(2, invokeMethodWithType(msg2, getMethod(msg2, "encode"), String.class));
            Object msg3 = Array.get(array, 3);
            this.setLine(3, invokeMethodWithType(msg3, getMethod(msg3, "encode"), String.class));
        }
        else
        {
            Object tileEntity = getValue(sign, getField(sign.getClass(), "sign", true));
            Object array = getValue(tileEntity, getField(tileEntity.getClass(), "lines", false));
            this.setLine(0, ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 0)));
            this.setLine(1, ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 1)));
            this.setLine(2, ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 2)));
            this.setLine(3, ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 3)));
        }
    }
}
