package com.w67clement.mineapi.api.wrappers;

import org.bukkit.entity.Player;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.player.MC_Player;

public class EntityPlayerWrapper
{

	public static Object makeEntityPlayerByPlayer(Player player)
	{
		return ReflectionAPI.invokeMethod(player, ReflectionAPI.getMethod(player, "getHandle", new Class[] {}),
				new Object[] {});
	}

	public static Object makeEntityPlayerByMCPlayer(MC_Player player)
	{
		return player.getMC_Handle();
	}

	public static Player makePlayerByEntityPlayer(Object entityPlayer)
	{
		if (MineAPI.getServerVersion().equals("v1_8_R3"))
		{
			if (entityPlayer.getClass()
					.equals(net.minecraft.server.v1_8_R3.EntityPlayer.class)) { return (Player) ReflectionAPI
							.invokeMethod(entityPlayer,
									ReflectionAPI.getMethod(entityPlayer, "getBukkitEntity", new Class[] {}),
									new Object[] {}); }
			return null;
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R2"))
		{
			if (entityPlayer.getClass()
					.equals(net.minecraft.server.v1_8_R2.EntityPlayer.class)) { return (Player) ReflectionAPI
							.invokeMethod(entityPlayer,
									ReflectionAPI.getMethod(entityPlayer, "getBukkitEntity", new Class[] {}),
									new Object[] {}); }
			return null;
		}
		else if (MineAPI.getServerVersion().equals("v1_8_R1"))
		{
			if (entityPlayer.getClass()
					.equals(net.minecraft.server.v1_8_R1.EntityPlayer.class)) { return (Player) ReflectionAPI
							.invokeMethod(entityPlayer,
									ReflectionAPI.getMethod(entityPlayer, "getBukkitEntity", new Class[] {}),
									new Object[] {}); }
			return null;
		}
		else
		{
			return null;
		}
	}

	public static MC_Player makeMC_PlayerByEntityPlayer(Object entityPlayer)
	{
		return MineAPI.getNmsManager().getMCPlayer(makePlayerByEntityPlayer(entityPlayer));
	}

}