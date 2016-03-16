package com.w67clement.mineapi.nms;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public interface IndividualPacketDecoder<T extends NmsPacket>
{

    T decode(Object packet);

}
