package com.w67clement.mineapi.api.wrappers;

import com.w67clement.mineapi.api.ReflectionAPI;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author w67clement
 */
public class BlockPositionWrapper
{

    private int x;
    private int y;
    private int z;

    public BlockPositionWrapper(Object blockPosition)
    {
        if (blockPosition != null)
        {
            this.x = ReflectionAPI.invokeMethodWithType(blockPosition, ReflectionAPI.getMethod(blockPosition, "getX"), int.class);
            this.y = ReflectionAPI.invokeMethodWithType(blockPosition, ReflectionAPI.getMethod(blockPosition, "getY"), int.class);
            this.z = ReflectionAPI.invokeMethodWithType(blockPosition, ReflectionAPI.getMethod(blockPosition, "getZ"), int.class);
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
        return ReflectionAPI.newInstance(ReflectionAPI.getConstructor(ReflectionAPI.getNmsClass("BlockPosition"), int.class, int.class, int.class), 0, 0, 0);
    }

    public static BlockPositionWrapper fromLocation(Location loc)
    {
        BlockPositionWrapper wrapper = new BlockPositionWrapper(defineDefaultBlockPosition());
        wrapper.setX(loc.getBlockX());
        wrapper.setY(loc.getBlockY());
        wrapper.setZ(loc.getBlockZ());
        return wrapper;
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

    public Location toLocation(World world)
    {
        return new Location(world, getX(), getY(), getZ());
    }

    public Object toBlockPosition()
    {
        return ReflectionAPI.newInstance(ReflectionAPI.getConstructor(ReflectionAPI.getNmsClass("BlockPosition"), int.class, int.class, int.class), this.x, this.y, this.z);
    }
}
