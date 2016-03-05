package com.w67clement.mineapi.nms.v1_9_R1.packets.play.out;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.packets.play.out.PacketUpdateSign;
import java.util.stream.IntStream;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.PacketPlayOutUpdateSign;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.entity.Player;

/**
 * Created by w67clement on 01/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class PacketUpdateSign_v1_9_R1 extends PacketUpdateSign
{
    public PacketUpdateSign_v1_9_R1(Sign sign)
    {
        super(sign);
    }

    public PacketUpdateSign_v1_9_R1(Location location, String[] contents)
    {
        super(location, contents);
    }

    public PacketUpdateSign_v1_9_R1(int x, int y, int z, String[] contents)
    {
        super(x, y, z, contents);
    }

    @Override
    public void send(Player player)
    {
        ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
    }

    @Override
    public Object constructPacket()
    {
        IChatBaseComponent[] contents = new IChatBaseComponent[4];
        IntStream.range(0, 3).forEach(i -> contents[i] = (IChatBaseComponent) ChatComponentWrapper.makeChatComponentByJson(this.getLine(i)));
        PacketPlayOutUpdateSign packet = new PacketPlayOutUpdateSign(((CraftWorld) this.getLocation().getWorld()).getHandle(), (BlockPosition) BlockPositionWrapper.fromLocation(this.location).toBlockPosition(), contents);
        return packet;
    }
}
