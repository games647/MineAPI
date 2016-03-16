package com.w67clement.mineapi.nms.reflection.play_out.inventory;

import com.w67clement.mineapi.api.ReflectionAPI.*;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.inventory.packets.WindowOpen;
import com.w67clement.mineapi.inventory.packets.WindowType;
import java.lang.reflect.Field;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 09/02/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftWindowOpen extends WindowOpen<Object>
{
    private static final Class<?> packetClass;
    private static final Field idField;
    private static final Field typeField;
    private static final Field titleField;
    private static final Field sizeField;
    private static final Field horseIdField;

    static
    {
        packetClass = getNmsClass("PacketPlayOutOpenWindow");
        idField = getFirstFieldOfType(packetClass, int.class, true);
        typeField = getFirstFieldOfType(packetClass, String.class, true);
        titleField = getFirstFieldOfType(packetClass, NmsClass.getIChatBaseComponentClass(), true);
        sizeField = getField(packetClass, "d", true);
        horseIdField = getField(packetClass, "e", true);
    }

    public CraftWindowOpen(Object packet)
    {
        super(packet);
    }

    public CraftWindowOpen(int id, WindowType type, String title, int size)
    {
        this(id, type, title, size, 0);
    }

    public CraftWindowOpen(int id, WindowType type, String title, int size, int horseId)
    {
        super(SunUnsafe.newInstance(packetClass));
        setId(id);
        setWindowType(type);
        setTitle(title);
        setSize(size);
        setHorseId(horseId);
    }

    public CraftWindowOpen(int id, Inventory inventory)
    {
        super(SunUnsafe.newInstance(packetClass));
        setId(id);
        setWindowType(WindowType.getByInventory(inventory.getType()));
        setTitleText(inventory.getTitle());
        setSize(inventory.getSize());
        setHorseId(0);
    }

    @Override
    public int getId()
    {
        return getIntValue(packet, idField);
    }

    @Override
    public WindowOpen setId(int id)
    {
        setValue(packet, idField, id);
        return this;
    }

    @Override
    public WindowType getWindowType()
    {
        return WindowType.getByMCValue(getStringValue(packet, typeField));
    }

    @Override
    public WindowOpen setWindowType(WindowType type)
    {
        setValue(packet, typeField, type.getMCValue());
        return this;
    }

    @Override
    public String getTitle()
    {
        return ChatComponentWrapper.makeJsonByChatComponent(getValue(packet, titleField));
    }

    @Override
    public WindowOpen setTitle(String title)
    {
        setValue(packet, titleField, ChatComponentWrapper.makeChatComponentByJson(title));
        return this;
    }

    @Override
    public int getSize()
    {
        return getIntValue(packet, sizeField);
    }

    @Override
    public WindowOpen setSize(int size)
    {
        setValue(packet, sizeField, size);
        return this;
    }

    @Override
    public int getHorseId()
    {
        return getIntValue(packet, horseIdField);
    }

    @Override
    public WindowOpen setHorseId(int horseId)
    {
        setValue(packet, horseIdField, horseId);
        return this;
    }

    @Override
    public void send(Player player)
    {
        NmsClass.sendPacket(player, this.getHandle());
    }
}
