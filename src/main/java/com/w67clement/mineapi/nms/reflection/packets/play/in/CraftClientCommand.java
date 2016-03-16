package com.w67clement.mineapi.nms.reflection.packets.play.in;

import com.w67clement.mineapi.entity.player.ClientCommand;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 15/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftClientCommand extends ClientCommand<Object>
{
    private static final Class<?> packetClass;
    private static final Field enumClientCommandField;
    private static final Class<?> enumClientCommand;
    private static final Method nameMethod;
    private static final Method valueOfMethod;

    static
    {
        packetClass = getNmsClass("PacketPlayInClientCommand");
        enumClientCommand = NmsClass.getEnumClientCommandClass();
        enumClientCommandField = getFirstFieldOfType(packetClass, enumClientCommand, true);
        nameMethod = getMethod(enumClientCommand, "name");
        valueOfMethod = getMethod(enumClientCommand, "valueOf", String.class);
    }

    public CraftClientCommand(Object packet) {
        super(packet);
    }

    public CraftClientCommand(ClientCommandType type)
    {
        super(SunUnsafe.newInstance(packetClass));
        this.setClientCommandType(type);
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, getHandle());
    }

    @Override
    public ClientCommand setClientCommandType(ClientCommandType type)
    {
        Object clientCommand = invokeMethod(null, valueOfMethod, type.name());
        setValue(packet, enumClientCommandField, clientCommand);
        return this;
    }

    @Override
    public ClientCommandType getClientCommandType()
    {
        String clientCommandType = invokeMethodWithType(getValue(packet, enumClientCommandField), nameMethod, String.class);
        return ClientCommandType.valueOf(clientCommandType);
    }
}
