package com.aol.w67clement.mineapi.nms.v1_8_R3.play_out.world;

import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder.EnumWorldBorderAction;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.api.Reflection;
import com.aol.w67clement.mineapi.enums.PacketType;
import com.aol.w67clement.mineapi.world.PacketWorldBorder;

public class PacketWorldBorder_v1_8_R3 implements PacketWorldBorder {

	private World world;
	private Location center;
	private int warningDistance;
	private int warningTime;
	private double radius;
	private long radiusTime;
	private boolean radiusTimeChanged;

	public PacketWorldBorder_v1_8_R3(World world) {
		this.world = world;
		WorldBorder worldBorder = this.world.getWorldBorder();
		this.center = worldBorder.getCenter();
		this.warningDistance = worldBorder.getWarningDistance();
		this.warningTime = worldBorder.getWarningTime();
		this.radius = worldBorder.getSize();
		this.radiusTime = ((CraftWorld) this.world).getHandle().getWorldBorder().i();
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public String getWorldName() {
		return this.world.getName();
	}

	@Override
	public void send(Player player) {
		PacketPlayOutWorldBorder worldBorder = new PacketPlayOutWorldBorder(
				((CraftWorld) this.world).getHandle().getWorldBorder(),
				EnumWorldBorderAction.INITIALIZE);
		try {
			Reflection.setValue(worldBorder,
					Reflection.getField(worldBorder.getClass(), "e", true),
					this.radius);
			Reflection.setValue(worldBorder,
					Reflection.getField(worldBorder.getClass(), "c", true),
					this.center.getX());
			Reflection.setValue(worldBorder,
					Reflection.getField(worldBorder.getClass(), "d", true),
					this.center.getZ());
			if (this.radiusTimeChanged) {
				Reflection.setValue(worldBorder,
						Reflection.getField(worldBorder.getClass(), "g", true),
						this.radiusTime);
			}
			Reflection.setValue(worldBorder,
					Reflection.getField(worldBorder.getClass(), "i", true),
					this.warningDistance);
			Reflection.setValue(worldBorder,
					Reflection.getField(worldBorder.getClass(), "h", true),
					this.warningTime);
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			((CraftPlayer) player).getHandle().playerConnection
					.sendPacket(worldBorder);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendAll() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			this.send(players);
		}
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public PacketWorldBorder setCenterX(double x) {
		this.center.setX(x);
		return this;
	}

	@Override
	public double getCenterX() {
		return this.center.getX();
	}

	@Override
	public PacketWorldBorder setCenterZ(double z) {
		this.center.setZ(z);
		return this;
	}

	@Override
	public double getCenterZ() {
		return this.center.getZ();
	}

	@Override
	public PacketWorldBorder setCenter(Location loc) {
		this.center = loc;
		return this;
	}

	@Override
	public Location getCenter() {
		return this.center;
	}

	@Override
	public WorldBorder getWorldBorder() {
		return this.getWorld().getWorldBorder();
	}

	@Override
	public PacketWorldBorder setWarningDistance(int blocks) {
		warningDistance = blocks;
		return this;
	}

	@Override
	public int getWarningDistance() {
		return this.warningDistance;
	}

	@Override
	public PacketWorldBorder setWarningTime(int time) {
		this.warningTime = time;
		return this;
	}

	@Override
	public int getWarningTime() {
		return this.warningTime;
	}

	@Override
	public PacketWorldBorder setNewRadius(double radius) {
		this.radius = radius;
		return this;
	}

	@Override
	public double getNewRadius() {
		return this.radius;
	}

	@Override
	public PacketWorldBorder setNewRadius(double radius, long time) {
		this.radius = radius;
		this.radiusTime = time;
		this.radiusTimeChanged = true;
		return this;
	}

	@Override
	public long getRadiusTime() {
		return this.radiusTime;
	}

}
