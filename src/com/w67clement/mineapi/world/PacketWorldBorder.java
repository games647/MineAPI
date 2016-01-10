package com.w67clement.mineapi.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.enums.PacketType;

public abstract class PacketWorldBorder extends WorldPacket
{

	protected World world;
	protected Location center;
	protected int warningDistance;
	protected int warningTime;
	protected double radius;
	protected long radiusTime;
	protected boolean radiusTimeChanged;

	public PacketWorldBorder(World world) {
		this.world = world;
		if (MineAPI.isGlowstone())
		{
			this.center = world.getSpawnLocation();
			this.radius = 60000000;
			this.warningDistance = 5;
			this.warningTime = 15;
		}
		else
		{
			WorldBorder worldBorder = this.world.getWorldBorder();
			this.center = worldBorder.getCenter();
			this.warningDistance = worldBorder.getWarningDistance();
			this.warningTime = worldBorder.getWarningTime();
			this.radius = worldBorder.getSize();
		}
	}

	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	public PacketWorldBorder setCenterX(double x)
	{
		this.center.setX(x);
		return this;
	}

	public double getCenterX()
	{
		return this.center.getX();
	}

	public PacketWorldBorder setCenterZ(double z)
	{
		this.center.setZ(z);
		return this;
	}

	public double getCenterZ()
	{
		return this.center.getZ();
	}

	public PacketWorldBorder setCenter(Location loc)
	{
		this.center = loc;
		return this;
	}

	public Location getCenter()
	{
		return this.center;
	}

	public WorldBorder getWorldBorder()
	{
		return this.getWorld().getWorldBorder();
	}

	public PacketWorldBorder setWarningDistance(int blocks)
	{
		warningDistance = blocks;
		return this;
	}

	public int getWarningDistance()
	{
		return this.warningDistance;
	}

	public PacketWorldBorder setWarningTime(int time)
	{
		this.warningTime = time;
		return this;
	}

	public int getWarningTime()
	{
		return this.warningTime;
	}

	public PacketWorldBorder setNewRadius(double radius)
	{
		this.radius = radius;
		return this;
	}

	public double getNewRadius()
	{
		return this.radius;
	}

	public PacketWorldBorder setNewRadius(double radius, long time)
	{
		this.radius = radius;
		this.radiusTime = time;
		this.radiusTimeChanged = true;
		return this;
	}

	public long getRadiusTime()
	{
		return this.radiusTime;
	}

	public enum PacketWorldBorderAction
	{
		SET_SIZE(0),
		LERP_SIZE(1),
		SET_CENTER(2),
		INITIALIZE(3),
		SET_WARNING_TIME(4),
		SET_WARNING_BLOCKS(5);

		private int actionId;

		private PacketWorldBorderAction(int id) {
			this.actionId = id;
		}

		public int getActionId()
		{
			return this.actionId;
		}
	}
}
