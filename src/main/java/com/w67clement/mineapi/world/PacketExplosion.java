package com.w67clement.mineapi.world;

import com.w67clement.mineapi.enums.PacketType;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * With this class, you can send the packet of the explosion.
 * 
 * @author w67clement
 */
public abstract class PacketExplosion extends WorldPacket
{

	protected double x;
	protected double y;
	protected double z;
	protected float radius;
	protected boolean sound;

	public PacketExplosion(World world, double x, double y, double z,
			float radius) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.sound = true;
		this.world = world;
	}

	public PacketExplosion(Location location, float radius) {
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.radius = radius;
		this.sound = true;
		this.world = location.getWorld();
	}

	public PacketExplosion(World world, double x, double y, double z,
			float radius, boolean sound) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.sound = sound;
		this.world = world;
	}

	public PacketExplosion(Location location, float radius, boolean sound) {
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.radius = radius;
		this.sound = sound;
		this.world = location.getWorld();
	}

	public PacketExplosion createExplosion(World world, double x, double y,
			double z, float radius)
	{
		this.createExplosion(world, x, y, z, radius, true);
		return this;
	}

	public PacketExplosion createExplosion(Location location, float radius)
	{
		this.createExplosion(location.getWorld(), location.getX(),
				location.getY(), location.getZ(), radius, true);
		return this;
	}

	public PacketExplosion createExplosion(World world, double x, double y,
			double z, float radius, boolean sound)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.world = world;
		this.sound = sound;
		return this;
	}

	public PacketExplosion createExplosion(Location location, float radius,
			boolean sound)
	{
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.radius = radius;
		this.world = location.getWorld();
		this.sound = sound;
		return this;
	}

	@Override
	public String toString()
	{
		return "Explosion[World:" + this.getWorldName() + ",X:" + this.x + ",Y:"
				+ this.y + ",Z:" + this.z + ",Radius:" + this.radius + ",Sound:"
				+ this.sound + "]";
	}

	/*
	 * Location
	 */

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	public double getZ()
	{
		return this.z;
	}

	public Location getLocation()
	{
		return new Location(this.world, this.x, this.y, this.z);
	}

	public PacketExplosion setX(double x)
	{
		this.x = x;
		return this;
	}

	public PacketExplosion setY(double y)
	{
		this.y = y;
		return this;
	}

	public PacketExplosion setZ(double z)
	{
		this.z = z;
		return this;
	}

	public PacketExplosion setLocation(Location loc)
	{
		this.world = loc.getWorld();
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		return this;
	}

	/*
	 * Explosion Manager
	 */

	public float getRadius()
	{
		return this.radius;
	}

	public boolean hasSound()
	{
		return this.sound;
	}

	public PacketExplosion setRadius(float radius)
	{
		this.radius = radius;
		return this;
	}

	public PacketExplosion setSound(boolean sound)
	{
		this.sound = sound;
		return this;
	}

	/*
	 * Other
	 */

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}
}

// End of Explosion interface.