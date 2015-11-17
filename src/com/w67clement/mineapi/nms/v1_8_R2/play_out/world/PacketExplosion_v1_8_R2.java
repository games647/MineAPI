package com.w67clement.mineapi.nms.v1_8_R2.play_out.world;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.world.PacketExplosion;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.PacketPlayOutExplosion;

/**
 * Create some explosions with the packet PlayOutExplosion.
 * 
 * @author w67clement
 * @version 1.2 - CraftBukkit 1.8.3 SNAPSHOT
 */
public class PacketExplosion_v1_8_R2 implements PacketExplosion
{

	private double x;
	private double y;
	private double z;
	private float radius;
	private boolean sound;
	private World world;

	public PacketExplosion_v1_8_R2(World world, double x, double y, double z, float radius) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.sound = true;
		this.world = world;
	}

	public PacketExplosion_v1_8_R2(Location location, float radius) {
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.radius = radius;
		this.sound = true;
		this.world = location.getWorld();
	}

	public PacketExplosion_v1_8_R2(World world, double x, double y, double z, float radius, boolean sound) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.sound = sound;
		this.world = world;
	}

	public PacketExplosion_v1_8_R2(Location location, float radius, boolean sound) {
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.radius = radius;
		this.sound = sound;
		this.world = location.getWorld();
	}

	@Override
	public void send(Player player)
	{
		PacketPlayOutExplosion packet = new PacketPlayOutExplosion(x, y, z, radius, new ArrayList<BlockPosition>(),
				null);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		if (this.sound)
		{
			this.getWorld().playSound(new Location(this.getWorld(), this.x, this.y, this.z), Sound.EXPLODE, 4L, 2L);
		}
	}

	@Override
	public void sendAll()
	{
		for (Player allPlayers : Bukkit.getOnlinePlayers())
		{
			this.send(allPlayers);
		}
	}

	@Override
	public PacketExplosion createExplosion(World world, double x, double y, double z, float radius)
	{
		this.createExplosion(world, x, y, z, radius, true);
		return this;
	}

	@Override
	public PacketExplosion createExplosion(Location location, float radius)
	{
		this.createExplosion(location.getWorld(), location.getX(), location.getY(), location.getZ(), radius, true);
		return this;
	}

	@Override
	public World getWorld()
	{
		return this.world;
	}

	@Override
	public String getWorldName()
	{
		return this.getWorld().getName();
	}

	@Override
	public PacketExplosion createExplosion(World world, double x, double y, double z, float radius, boolean sound)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.world = world;
		this.sound = sound;
		return this;
	}

	@Override
	public PacketExplosion createExplosion(Location location, float radius, boolean sound)
	{
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.radius = radius;
		this.world = location.getWorld();
		this.sound = sound;
		return this;
	}

	public String toString()
	{
		return "Explosion[World:" + this.getWorldName() + ",X:" + this.x + ",Y:" + this.y + ",Z:" + this.z + ",Radius:"
				+ this.radius + ",Sound:" + this.sound + "]";
	}

	@Override
	public double getX()
	{
		return this.x;
	}

	@Override
	public double getY()
	{
		return this.y;
	}

	@Override
	public double getZ()
	{
		return this.z;
	}

	@Override
	public Location getLocation()
	{
		return new Location(this.world, this.x, this.y, this.z);
	}

	@Override
	public PacketExplosion setX(double x)
	{
		this.x = x;
		return this;
	}

	@Override
	public PacketExplosion setY(double y)
	{
		this.y = y;
		return this;
	}

	@Override
	public PacketExplosion setZ(double z)
	{
		this.z = z;
		return this;
	}

	@Override
	public PacketExplosion setLocation(Location loc)
	{
		this.world = loc.getWorld();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		return this;
	}

	@Override
	public float getRadius()
	{
		return this.radius;
	}

	@Override
	public boolean hasSound()
	{
		return this.sound;
	}

	@Override
	public PacketExplosion setRadius(float radius)
	{
		this.radius = radius;
		return this;
	}

	@Override
	public PacketExplosion setSound(boolean sound)
	{
		this.sound = sound;
		return this;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}
}

// End of Explosion class