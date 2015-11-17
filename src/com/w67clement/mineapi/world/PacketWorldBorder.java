package com.w67clement.mineapi.world;

import org.bukkit.Location;
import org.bukkit.WorldBorder;

public interface PacketWorldBorder extends WorldPacket
{

	public PacketWorldBorder setCenterX(double x);

	public double getCenterX();

	public PacketWorldBorder setCenterZ(double z);

	public double getCenterZ();

	public PacketWorldBorder setCenter(Location loc);

	public Location getCenter();

	public WorldBorder getWorldBorder();

	public PacketWorldBorder setWarningDistance(int blocks);

	public int getWarningDistance();

	public PacketWorldBorder setWarningTime(int time);

	public int getWarningTime();

	public PacketWorldBorder setNewRadius(double radius);

	public PacketWorldBorder setNewRadius(double radius, long time);

	public double getNewRadius();

	public long getRadiusTime();

	public enum PacketWorldBorderAction
	{
		SET_SIZE(0), LERP_SIZE(1), SET_CENTER(2), INITIALIZE(3), SET_WARNING_TIME(4), SET_WARNING_BLOCKS(5);

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
