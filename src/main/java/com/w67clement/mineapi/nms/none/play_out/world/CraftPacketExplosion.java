package com.w67clement.mineapi.nms.none.play_out.world;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.world.PacketExplosion;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Create some explosions with the packet PlayOutExplosion.
 * 
 * @author w67clement
 * @version 3.0 - MineAPI v2.2.0 (Event system v2)
 */
public class CraftPacketExplosion extends PacketExplosion
{
	private static Class<?> packetClass;
	private static Constructor<?> packetConstructor;

	public CraftPacketExplosion(World world, double x, double y, double z,
			float radius) {
		super(world, x, y, z, radius);
	}

	public CraftPacketExplosion(Location location, float radius) {
		super(location, radius);
	}

	public CraftPacketExplosion(World world, double x, double y, double z,
			float radius, boolean sound) {
		super(world, x, y, z, radius, sound);
	}

	public CraftPacketExplosion(Location location, float radius,
			boolean sound) {
		super(location, radius, sound);
	}

	@Override
	public void send(Player player)
	{
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
		if (this.sound)
		{
			this.getWorld().playSound(
					new Location(this.getWorld(), this.x, this.y, this.z),
					Sound.EXPLODE, 4L, 2L);
		}
	}

	@Override
	public Object constructPacket()
	{
		if (MineAPI.isSpigot())
		{
			return this.constructPacket_Bukkit();
		}
		else if (MineAPI
				.isGlowstone()) { return this.constructPacket_Glowstone(); }
		return this.constructPacket_Bukkit();
	}

	private Object constructPacket_Bukkit()
	{
		return ReflectionAPI.newInstance(packetConstructor, this.x, this.y,
				this.z, this.radius, new ArrayList<>(), null);
	}

	private Object constructPacket_Glowstone()
	{
		return ReflectionAPI.newInstance(packetConstructor, (float) this.x,
				(float) this.y, (float) this.z, this.radius, 0.0F, 0.0F, 0.0F,
				new ArrayList<>());
	}

	static
	{
		if (MineAPI.isGlowstone())
		{
			packetClass = ReflectionAPI.getClass(
					"net.glowstone.net.message.play.game.ExplosionMessage");
			packetConstructor = ReflectionAPI.getConstructor(packetClass,
					float.class, float.class, float.class, float.class,
					float.class, float.class, float.class, Collection.class);
		}
		else
		{
			packetClass = ReflectionAPI.getNmsClass("PacketPlayOutExplosion");
			packetConstructor = ReflectionAPI.getConstructor(packetClass,
					double.class, double.class, double.class, float.class,
					List.class, ReflectionAPI.getNmsClass("Vec3D"));
		}
	}

}
