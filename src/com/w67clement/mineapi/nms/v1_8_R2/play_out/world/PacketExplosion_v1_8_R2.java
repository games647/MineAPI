package com.w67clement.mineapi.nms.v1_8_R2.play_out.world;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.world.PacketExplosion;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.PacketPlayOutExplosion;

/**
 * Create some explosions with the packet PlayOutExplosion.
 * 
 * @author w67clement
 * @version 2.0 - CraftBukkit 1.8.3 SNAPSHOT
 */
public class PacketExplosion_v1_8_R2 extends PacketExplosion
{

	public PacketExplosion_v1_8_R2(World world, double x, double y, double z,
			float radius) {
		super(world, x, y, z, radius);
	}

	public PacketExplosion_v1_8_R2(Location location, float radius) {
		super(location, radius);
	}

	public PacketExplosion_v1_8_R2(World world, double x, double y, double z,
			float radius, boolean sound) {
		super(world, x, y, z, radius, sound);
	}

	public PacketExplosion_v1_8_R2(Location location, float radius,
			boolean sound) {
		super(location, radius, sound);
	}

	@Override
	public void send(Player player)
	{
		PacketPlayOutExplosion packet = new PacketPlayOutExplosion(x, y, z,
				radius, new ArrayList<BlockPosition>(), null);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		if (this.sound)
		{
			this.getWorld().playSound(
					new Location(this.getWorld(), this.x, this.y, this.z),
					Sound.EXPLODE, 4L, 2L);
		}
	}
}

// End of Explosion class