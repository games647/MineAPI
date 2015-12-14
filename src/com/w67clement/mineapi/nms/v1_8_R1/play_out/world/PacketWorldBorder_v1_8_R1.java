package com.w67clement.mineapi.nms.v1_8_R1.play_out.world;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.world.PacketWorldBorder;

import net.minecraft.server.v1_8_R1.EnumWorldBorderAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldBorder;

public class PacketWorldBorder_v1_8_R1 extends PacketWorldBorder
{

	public PacketWorldBorder_v1_8_R1(World world) {
		super(world);
		this.radiusTime = ((CraftWorld) this.world).getHandle().af().i();
	}

	@Override
	public void send(Player player)
	{
		PacketPlayOutWorldBorder worldBorder = new PacketPlayOutWorldBorder(
				((CraftWorld) this.world).getHandle().af(),
				EnumWorldBorderAction.INITIALIZE);
		try
		{
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
				ReflectionAPI.setValue(
						worldBorder, ReflectionAPI
								.getField(worldBorder.getClass(), "g", true),
						this.radiusTime);
			}
			ReflectionAPI.setValue(worldBorder,
					ReflectionAPI.getField(worldBorder.getClass(), "i", true),
					this.warningDistance);
			ReflectionAPI.setValue(worldBorder,
					ReflectionAPI.getField(worldBorder.getClass(), "h", true),
					this.warningTime);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		finally
		{
			((CraftPlayer) player).getHandle().playerConnection
					.sendPacket(worldBorder);
		}
	}
}