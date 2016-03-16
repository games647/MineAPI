package com.w67clement.mineapi.packets;

import java.lang.reflect.ParameterizedType;

/**
 * Created by w67clement on 12/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class NmsMinePacket<T extends MinePacket>
{

    protected T packet;

    public NmsMinePacket(T packet)
    {
        this.packet = packet;
    }

    public static <U extends MinePacket> Class<U> returnedClass(Class<? extends NmsMinePacket<U>> packet)
    {
        ParameterizedType parameterizedType = (ParameterizedType) packet.getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<U> ret = (Class<U>) parameterizedType.getActualTypeArguments()[0];
        return ret;
    }

    /**
     * Gets the MinePacket.
     *
     * @return MinePacket.
     */
    public T getPacket()
    {
        return packet;
    }
}
