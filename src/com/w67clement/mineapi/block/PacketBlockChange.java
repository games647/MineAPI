package com.w67clement.mineapi.block;

import org.bukkit.Location;
import org.bukkit.Material;

import com.w67clement.mineapi.nms.PacketSender;

public interface PacketBlockChange extends PacketSender
{

	public PacketBlockChange setLocation(Location loc);

	public PacketBlockChange setLocation(int x, int y, int z);

	public Location getLocation();

	public int getX();

	public int getY();

	public int getZ();

	public PacketBlockChange setX(int x);

	public PacketBlockChange setY(int y);

	public PacketBlockChange setZ(int z);

	public Material getMaterial();

	public PacketBlockChange setMaterial(Material material);

	public int getData();

	public PacketBlockChange setData(int data);

}
