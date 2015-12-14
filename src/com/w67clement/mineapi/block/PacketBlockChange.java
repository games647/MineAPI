package com.w67clement.mineapi.block;

import org.bukkit.Location;
import org.bukkit.Material;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;

public abstract class PacketBlockChange extends PacketSender
{

	protected Location location;
	protected Material material;
	protected int data;

	public PacketBlockChange(Material material, int data, Location loc) {
		this.material = material;
		this.data = data;
		this.location = loc;
	}

	public PacketBlockChange(Material material, Location loc) {
		this(material, 0, loc);
	}

	public PacketBlockChange(Material material, int x, int y, int z) {
		this(material, 0, x, y, z);
	}

	public PacketBlockChange(Material material, int data, int x, int y, int z) {
		this(material, data, new Location(null, x, y, z));
	}

	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	public PacketBlockChange setLocation(Location loc)
	{
		this.location = loc;
		return this;
	}

	public PacketBlockChange setLocation(int x, int y, int z)
	{
		return this.setX(x).setY(y).setZ(z);
	}

	public Location getLocation()
	{
		return this.location;
	}

	public int getX()
	{
		return this.location.getBlockX();
	}

	public int getY()
	{
		return this.location.getBlockY();
	}

	public int getZ()
	{
		return this.location.getBlockZ();
	}

	public PacketBlockChange setX(int x)
	{
		this.location.setX(x);
		return this;
	}

	public PacketBlockChange setY(int y)
	{
		this.location.setY(y);
		return this;
	}

	public PacketBlockChange setZ(int z)
	{
		this.location.setZ(z);
		return this;
	}

	public Material getMaterial()
	{
		return this.material;
	}

	public PacketBlockChange setMaterial(Material material)
	{
		this.material = material;
		return this;
	}

	public int getData()
	{
		return this.data;
	}

	public PacketBlockChange setData(int data)
	{
		this.data = data;
		return this;
	}

}
