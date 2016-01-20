package com.w67clement.mineapi.api.wrappers;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * 
 * @author w67clement
 * 
 */
public class BlockPositionWrapper
{

	private int x;
	private int y;
	private int z;

	public BlockPositionWrapper(Object blockPosition) {
		if (blockPosition != null)
		{
			Class<?> clazz = blockPosition.getClass();
			if (MineAPI.getServerVersion().equals("v1_8_R1")
					&& clazz.equals(ReflectionAPI.getNmsClass("BlockPosition")))
			{
				net.minecraft.server.v1_8_R1.BlockPosition position = (net.minecraft.server.v1_8_R1.BlockPosition) blockPosition;
				this.x = position.getX();
				this.y = position.getY();
				this.z = position.getZ();
			}
			else if (MineAPI.getServerVersion().equals("v1_8_R2")
					&& clazz.equals(ReflectionAPI.getNmsClass("BlockPosition")))
			{
				net.minecraft.server.v1_8_R2.BlockPosition position = (net.minecraft.server.v1_8_R2.BlockPosition) blockPosition;
				this.x = position.getX();
				this.y = position.getY();
				this.z = position.getZ();
			}
			else if (MineAPI.getServerVersion().equals("v1_8_R3")
					&& clazz.equals(ReflectionAPI.getNmsClass("BlockPosition")))
			{
				net.minecraft.server.v1_8_R3.BlockPosition position = (net.minecraft.server.v1_8_R3.BlockPosition) blockPosition;
				this.x = position.getX();
				this.y = position.getY();
				this.z = position.getZ();
			}
			else
			{
				this.x = (int) ReflectionAPI.invokeMethod(blockPosition,
						ReflectionAPI.getMethod(blockPosition, "getX"));
				this.y = (int) ReflectionAPI.invokeMethod(blockPosition,
						ReflectionAPI.getMethod(blockPosition, "getY"));
				this.z = (int) ReflectionAPI.invokeMethod(blockPosition,
						ReflectionAPI.getMethod(blockPosition, "getZ"));
			}
		}
		else
		{
			this.x = 0;
			this.y = 0;
			this.z = 0;
		}
	}

	public static Object defineDefaultBlockPosition()
	{
		if (MineAPI.getServerVersion().equals("v1_8_R1"))
		{
			return net.minecraft.server.v1_8_R1.BlockPosition.ZERO;
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R2"))
		{
			return net.minecraft.server.v1_8_R2.BlockPosition.ZERO;
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R3"))
		{
			return net.minecraft.server.v1_8_R3.BlockPosition.ZERO;
		}
		else
		{
			return ReflectionAPI.newInstance(ReflectionAPI.getConstructor(ReflectionAPI.getNmsClass("BlockPosition"),
					Integer.class, Integer.class, Integer.class), 0, 0, 0);
		}
	}

	public int getX()
	{
		return this.x;
	}

	public void setX(int xValue)
	{
		this.x = xValue;
	}

	public int getY()
	{
		return this.y;
	}

	public void setY(int yValue)
	{
		this.y = yValue;
	}

	public int getZ()
	{
		return this.z;
	}

	public void setZ(int zValue)
	{
		this.z = zValue;
	}

	public static BlockPositionWrapper fromLocation(Location loc)
	{
		BlockPositionWrapper wrapper = new BlockPositionWrapper(defineDefaultBlockPosition());
		wrapper.setX(loc.getBlockX());
		wrapper.setY(loc.getBlockY());
		wrapper.setZ(loc.getBlockZ());
		return wrapper;
	}

	public Location toLocation(World world)
	{
		return new Location(world, getX(), getY(), getZ());
	}

	public Object toBlockPosition()
	{
		if (MineAPI.getServerVersion().equals("v1_8_R1"))
		{
			return new net.minecraft.server.v1_8_R1.BlockPosition(this.x, this.y, this.z);
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R2"))
		{
			return new net.minecraft.server.v1_8_R2.BlockPosition(this.x, this.y, this.z);
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R3"))
		{
			return new net.minecraft.server.v1_8_R3.BlockPosition(this.x, this.y, this.z);
		}
		else
		{
			return ReflectionAPI.newInstance(ReflectionAPI.getConstructor(ReflectionAPI.getNmsClass("BlockPosition"),
					Integer.class, Integer.class, Integer.class), this.x, this.y, this.z);
		}
	}
}
