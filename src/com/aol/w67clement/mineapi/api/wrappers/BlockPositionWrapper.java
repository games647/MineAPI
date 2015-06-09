package com.aol.w67clement.mineapi.api.wrappers;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.World;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.api.ReflectionAPI;

/**
 * 
 * @author 67clement
 * 
 */
public class BlockPositionWrapper {

	private Object blockPosition;
	private Field x;
	private Field y;
	private Field z;

	public BlockPositionWrapper(Object blockPosition) {
		if (blockPosition != null) {
			if (blockPosition instanceof net.minecraft.server.v1_8_R1.BlockPosition) {
				this.blockPosition = blockPosition;
			} else if (blockPosition instanceof net.minecraft.server.v1_8_R2.BlockPosition) {
				this.blockPosition = blockPosition;
			} else if (blockPosition instanceof net.minecraft.server.v1_8_R3.BlockPosition) {
				this.blockPosition = blockPosition;
			} else {
				this.defineDefaultBlockPosition();
			}
		} else {
			this.defineDefaultBlockPosition();
		}
		this.x = ReflectionAPI.getField(blockPosition.getClass(), "a", true);
		if (MineAPI.getServerVersion().equals("v1_8_R1")) {
			this.y = ReflectionAPI.getField(blockPosition.getClass(), "b", true);
			this.z = ReflectionAPI.getField(blockPosition.getClass(), "c", true);
		} else if (MineAPI.getServerVersion().equals("v1_8_R2")
				|| MineAPI.getServerVersion().equals("v1_8_R3")) {
			this.y = ReflectionAPI.getField(blockPosition.getClass(), "c", true);
			this.z = ReflectionAPI.getField(blockPosition.getClass(), "d", true);
		}
	}

	private void defineDefaultBlockPosition() {
		if (MineAPI.getServerVersion().equals("v1_8_R1")) {
			blockPosition = net.minecraft.server.v1_8_R1.BlockPosition.ZERO;
		} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
			blockPosition = net.minecraft.server.v1_8_R3.BlockPosition.ZERO;
		} else if (MineAPI.getServerVersion().equals("v1_8_R3")) {
			blockPosition = net.minecraft.server.v1_8_R3.BlockPosition.ZERO;
		}
	}

	public int getX() {
		return (int) ReflectionAPI.getValue(blockPosition, this.x);
	}

	public void setX(int xValue) {
		ReflectionAPI.setValue(this.blockPosition, this.x, xValue);
	}

	public int getY() {
		return (int) ReflectionAPI.getValue(blockPosition, this.y);
	}

	public void setY(int yValue) {
		ReflectionAPI.setValue(this.blockPosition, this.y, yValue);
	}

	public int getZ() {
		return (int) ReflectionAPI.getValue(blockPosition, this.z);
	}

	public void setZ(int zValue) {
		ReflectionAPI.setValue(this.blockPosition, this.z, zValue);
	}

	public Location toLocation(World world) {
		return new Location(world, getX(), getY(), getZ());
	}
	
	public Object toBlockPosition() {
		return this.blockPosition;
	}
}
