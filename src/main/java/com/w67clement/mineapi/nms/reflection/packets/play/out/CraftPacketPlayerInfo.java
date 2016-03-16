package com.w67clement.mineapi.nms.reflection.packets.play.out;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI.*;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.tab.PacketPlayerInfo;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

public class CraftPacketPlayerInfo extends PacketPlayerInfo<Object>
{

    private static final Class<?> packetClass;
    private static final Field actionField;
    private static final Class<?> enumPlayerInfoActionClass;
    private static final Method valueOfMethod;
    private static final Method nameMethod;
    private static Class<?> playerDataClass;
    private static Constructor<?> playerDataConstructor;
    private static Class<?> enum_game_modeClass;
    private static Object enum_adventure;
    private static Object enum_creative;
    private static Object enum_spectator;
    private static Object enum_survival;

    static
    {
        packetClass = getNmsClass("PacketPlayOutPlayerInfo");
        actionField = getField(packetClass, "a", true);
        if (MineAPI.getServerVersion().equals("v1_8_R1"))
            playerDataClass = getNmsClass("PlayerInfoData");
        else
            playerDataClass = getNmsClass("PacketPlayOutPlayerInfo$PlayerInfoData");
        if (MineAPI.getServerVersion().equals("v1_8_R1"))
            enumPlayerInfoActionClass = getNmsClass("EnumPlayerInfoAction");
        else
            enumPlayerInfoActionClass = getNmsClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
        valueOfMethod = getMethod(enumPlayerInfoActionClass, "valueOf", String.class);
        nameMethod = getMethod(enumPlayerInfoActionClass, "name");
        if (MineAPI.getServerVersion().equals("v1_8_R1"))
            enum_game_modeClass = getNmsClass("EnumGamemode");
        else
            enum_game_modeClass = getNmsClass("WorldSettings$EnumGamemode");
        for (Object obj : enum_game_modeClass.getEnumConstants())
        {
            if (obj.toString().equals("ADVENTURE"))
            {
                enum_adventure = obj;
            }
            else if (obj.toString().equals("CREATIVE"))
            {
                enum_creative = obj;
            }
            else if (obj.toString().equals("SPECTATOR"))
            {
                enum_spectator = obj;
            }
            else if (obj.toString().equals("SURVIVAL"))
            {
                enum_survival = obj;
            }
        }
        playerDataConstructor = playerDataClass.getConstructors()[0];
    }

    public CraftPacketPlayerInfo(Object packet)
    {
        super(packet);
    }

    public CraftPacketPlayerInfo(MC_EnumPlayerInfoAction action, List<PacketPlayerInfoData> data)
    {
        super(SunUnsafe.newInstance(packetClass));
    }

    @Override
    public MC_EnumPlayerInfoAction getAction()
    {
        String enumPlayerInfoAction = invokeMethodWithType(getValue(packet, actionField), nameMethod, String.class);
        return MC_EnumPlayerInfoAction.valueOf(enumPlayerInfoAction);
    }

    @Override
    public void setAction(MC_EnumPlayerInfoAction action)
    {
        Object clientCommand = invokeMethod(null, valueOfMethod, action.name());
        setValue(packet, actionField, clientCommand);
    }

    @Override
    public List<PacketPlayerInfoData> getData()
    {
        return null;
    }

    @Override
    public void setData(List<PacketPlayerInfoData> data)
    {

    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.getHandle());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Object constructPacket_Bukkit()
    {
        List data = new ArrayList();
        this.data.forEach(playerinfodata -> {
            Object gamemode = null;
            switch (playerinfodata.getGamemode())
            {
                case ADVENTURE:
                    gamemode = enum_adventure;
                    break;
                case CREATIVE:
                    gamemode = enum_creative;
                    break;
                case SPECTATOR:
                    gamemode = enum_spectator;
                    break;
                case SURVIVAL:
                    gamemode = enum_survival;
                    break;
                default:
                    break;
            }
            data.add(newInstance(playerDataConstructor, packet, playerinfodata.getProfile().toNms(), playerinfodata.getPing(), gamemode, ChatComponentWrapper.makeChatComponentByText(playerinfodata.getPlayerListName())));
        });

        setValue(packet, getField(packetClass, "b", true), data);
        return packet;
    }
}
