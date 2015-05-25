package com.aol.w67clement.mineapi.world;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * The interface of the class Explosion
 * <p/>
 * Please see com.aol.w67clement.mineapi.world.Explosion !
 * 
 * @author 67clement
 */
public interface PacketExplosion extends WorldPacket {

	public PacketExplosion createExplosion(World world, double x, double y,
			double z, float radius);

	public PacketExplosion createExplosion(Location location, float radius);

	public PacketExplosion createExplosion(World world, double x, double y,
			double z, float radius, boolean sound);

	public PacketExplosion createExplosion(Location location, float radius,
			boolean sound);
	
	/*
	 *  Location
	 */
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public Location getLocation();

	public PacketExplosion setX(double x);
	
	public PacketExplosion setY(double y);
	
	public PacketExplosion setZ(double z);
	
	public PacketExplosion setLocation(Location loc);
	
	/*
	 *  Explosion Manager
	 */
	
	public float getRadius();
	
	public boolean hasSound();
	
	public PacketExplosion setRadius(float radius);
	
	public PacketExplosion setSound(boolean sound);
}

// End of Explosion interface.