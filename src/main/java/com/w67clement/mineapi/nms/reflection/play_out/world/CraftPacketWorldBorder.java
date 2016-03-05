package com.w67clement.mineapi.nms.reflection.play_out.world;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.world.PacketWorldBorder;
import java.lang.reflect.Constructor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class CraftPacketWorldBorder extends PacketWorldBorder
{
	private static Class<?> packetClass;
	private static Constructor<?> packetConstructor;

	static
	{
		if (MineAPI.isGlowstone())
		{
			packetClass = ReflectionAPI.getClass(
					"net.glowstone.net.message.play.game.WorldBorderMessage");
			packetConstructor = ReflectionAPI.getConstructor(packetClass,
					ReflectionAPI.getClass(
							"net.glowstone.net.message.play.game.WorldBorderMessage$Action"),
					double.class, double.class, double.class, double.class,
					long.class, int.class, int.class, int.class);
		}
		else
		{
			packetClass = ReflectionAPI.getNmsClass("PacketPlayOutWorldBorder");
			packetConstructor = ReflectionAPI.getConstructor(packetClass,
					ReflectionAPI.getNmsClass("WorldBorder"),
					ReflectionAPI.NmsClass.getEnumWorldBorderActionClass());
		}
	}

	public CraftPacketWorldBorder(World world) {
		super(world);
		if (MineAPI.isGlowstone())
		{
			this.radiusTime = 0L;
		}
		else
		{
			Object nms_border = this
					.getWorldBorderHandle(world.getWorldBorder());
			this.radiusTime = ReflectionAPI
					.invokeMethodWithType(nms_border,
							ReflectionAPI.getFirstMethodOfType(
									nms_border.getClass(), long.class),
					long.class);
		}
	}

	@Override
	public void send(Player player)
	{
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
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
		Object enum_init = null;
		for (Object obj : ReflectionAPI.NmsClass.getEnumWorldBorderActionClass()
				.getEnumConstants())
		{
			if (obj.toString().equals("INITIALIZE"))
			{
				enum_init = obj;
				break;
			}
		}
		Object worldBorder = ReflectionAPI.newInstance(packetConstructor,
				this.getWorldBorderHandle(this.world.getWorldBorder()),
				enum_init);
		ReflectionAPI.setValue(worldBorder,
				ReflectionAPI.getField(worldBorder.getClass(), "e", true),
				this.radius);
		ReflectionAPI.setValue(worldBorder,
				ReflectionAPI.getField(worldBorder.getClass(), "c", true),
				this.center.getX());
		ReflectionAPI.setValue(worldBorder,
				ReflectionAPI.getField(worldBorder.getClass(), "d", true),
				this.center.getZ());
		if (this.radiusTimeChanged)
		{
			ReflectionAPI.setValue(worldBorder,
					ReflectionAPI.getField(worldBorder.getClass(), "g", true),
					this.radiusTime);
		}
		ReflectionAPI.setValue(worldBorder,
				ReflectionAPI.getField(worldBorder.getClass(), "i", true),
				this.warningDistance);
		ReflectionAPI.setValue(worldBorder,
				ReflectionAPI.getField(worldBorder.getClass(), "h", true),
				this.warningTime);
		return worldBorder;
	}

	// Not used for the moment
	private Object constructPacket_Glowstone()
	{
		Object enum_init = ReflectionAPI.invokeMethod(null,
				ReflectionAPI.getMethod(
						ReflectionAPI.getClass(
								"net.glowstone.net.message.play.game.WorldBorderMessage$Action"),
						"getAction", int.class),
				3);
		Object packet = ReflectionAPI.newInstance(packetConstructor, enum_init,
				this.center.getX(), this.center.getZ(), 0.0D, this.radius,
				this.radiusTime, 5, this.warningTime, this.warningDistance);
		return packet;
	}

	private Object getWorldBorderHandle(WorldBorder border)
	{
		return ReflectionAPI.getValue(border,
				ReflectionAPI.getField(border.getClass(), "handle", true));
	}
}
