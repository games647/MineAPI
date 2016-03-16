package com.w67clement.mineapi.nms.reflection.packets.play.in.decoders;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.entity.player.ClientCommand.ClientCommandType;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftClientCommandDecoder implements IndividualPacketDecoder<ClientCommand>
{
    @Override
    public ClientCommand decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayInClientCommand.getPacketName()) || PacketList.PacketPlayInClientCommand.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            ClientCommand minePacket = null;
            if (MineAPI.isGlowstone())
            {
                int id = getIntValue(packet, getField(packet.getClass(), "action", true));
                for (ClientCommandType type : ClientCommandType.values())
                {
                    if (id == type.getId())
                        minePacket = MineAPI.getNmsManager().getPacketPlayInClientCommand(type);
                }
            }
            else
            {
                Object enum_constant = getValue(packet, getField(packet.getClass(), "a", true));
                int id = invokeMethodWithType(enum_constant, getMethod(enum_constant, "ordinal"), int.class);
                for (ClientCommandType type : ClientCommandType.values())
                {
                    if (id == type.getId())
                        minePacket = MineAPI.getNmsManager().getPacketPlayInClientCommand(type);
                }
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
