package com.w67clement.mineapi.api.wrappers;

import com.w67clement.mineapi.api.ReflectionAPI;
import java.lang.reflect.Field;
import org.apache.commons.lang.Validate;

public class PacketWrapper
{

    private Object packet;

    public PacketWrapper(Object packet)
    {
        this.packet = packet;
    }

    public Object getValue(String fieldName, boolean declared) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        Validate.notNull(fieldName, "Field name cannot be null!");
        return ReflectionAPI.getField(packet.getClass(), fieldName, declared).get(packet);
    }

    public Field getField(String fieldname, boolean declared) throws NoSuchFieldException, SecurityException
    {
        return ReflectionAPI.getField(this.packet.getClass(), fieldname, declared);
    }

    public String getStringValue(String fieldName, boolean declared) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
    {
        Validate.notNull(fieldName, "Field name cannot be null!");
        return ReflectionAPI.getStringValue(packet, fieldName, declared);
    }

    public Object getPacket()
    {
        return this.packet;
    }

    public Class<?> getPacketClass()
    {
        return this.packet.getClass();
    }

    public String getPacketName()
    {
        return this.packet.getClass().getSimpleName();
    }
}
