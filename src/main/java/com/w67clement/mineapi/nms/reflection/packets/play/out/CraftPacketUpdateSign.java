package com.w67clement.mineapi.nms.reflection.packets.play.out;

import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.packets.play.out.PacketUpdateSign;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 14/02/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketUpdateSign extends PacketUpdateSign<Object>
{
    private static final Class<?> packetClass;
    private static final Field worldField;
    private static final Field blockPosField;
    private static final Field contentsField;
    private static final Method getWorldMethod;
    private static final Method getHandleWorldMethod;

    static
    {
        packetClass = getNmsClass("PacketPlayOutUpdateSign");
        worldField = getField(packetClass, "a", true);
        blockPosField = getField(packetClass, "b", true);
        contentsField = getField(packetClass, "c", true);
        getWorldMethod = getMethod(getNmsClass("World"), "getWorld");
        getHandleWorldMethod = getMethod(getCraftClass("CraftWorld", CraftPackage.ORG_BUKKIT_CRAFTBUKKIT), "getHandle");
    }

    public CraftPacketUpdateSign(Object packet)
    {
        super(packet);
    }

    public CraftPacketUpdateSign(Sign sign)
    {
        this(sign.getLocation(), loadSignContents(sign));
    }

    public CraftPacketUpdateSign(Location location, String[] contents)
    {
        super(SunUnsafe.newInstance(packetClass));
        setLocation(location);
        setContents(contents);
    }

    public CraftPacketUpdateSign(int x, int y, int z, String[] contents)
    {
        this(new Location(null, x, y, z), contents);
    }

    private static String[] loadSignContents(Sign sign)
    {
        Object tileEntity = getValue(sign, getField(sign.getClass(), "sign", true));
        Object array = getValue(tileEntity, getField(tileEntity.getClass(), "lines", false));
        String[] contents = new String[4];
        contents[0] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 0));
        contents[1] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 1));
        contents[2] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 2));
        contents[3] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(array, 3));
        return contents;
    }

    @Override
    public Location getLocation()
    {
        Object nmsWorld = getValue(packet, worldField);
        World world = invokeMethodWithType(nmsWorld, getWorldMethod, World.class);
        return new BlockPositionWrapper(getValue(packet, blockPosField)).toLocation(world);
    }

    @Override
    public void setLocation(Location location)
    {
        setValue(packet, worldField, invokeMethod(location.getWorld(), getHandleWorldMethod));
        setValue(packet, blockPosField, BlockPositionWrapper.fromLocation(location).toBlockPosition());
    }

    @Override
    public String[] getContents()
    {
        Object nmsContents = getValue(packet, contentsField);
        String[] contents = new String[4];
        contents[0] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(nmsContents, 0));
        contents[1] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(nmsContents, 1));
        contents[2] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(nmsContents, 2));
        contents[3] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(nmsContents, 3));
        return contents;
    }

    @Override
    public void setContents(String[] contents)
    {
        Object array = Array.newInstance(NmsClass.getIChatBaseComponentClass(), 4);
        Array.set(array, 0, ChatComponentWrapper.makeChatComponentByJson(contents[0]));
        Array.set(array, 1, ChatComponentWrapper.makeChatComponentByJson(contents[1]));
        Array.set(array, 2, ChatComponentWrapper.makeChatComponentByJson(contents[2]));
        Array.set(array, 3, ChatComponentWrapper.makeChatComponentByJson(contents[3]));
        setValue(packet, getField(packetClass, "c", true), array);
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.getHandle());
    }
}
