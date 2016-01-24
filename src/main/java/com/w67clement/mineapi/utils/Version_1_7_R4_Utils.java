package com.w67clement.mineapi.utils;

import com.w67clement.mineapi.MineAPI;

public class Version_1_7_R4_Utils
{

	public static boolean isSpigotProtocolHack()
	{
		if (!MineAPI.isSpigot()) return false;
		try
		{
			Class.forName("org.spigotmc.ProtocolInjector");
		}
		catch (Throwable e)
		{
			return false;
		}
		return true;
	}

}
