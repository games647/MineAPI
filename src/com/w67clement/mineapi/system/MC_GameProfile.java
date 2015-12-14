package com.w67clement.mineapi.system;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.UUID;

import com.w67clement.mineapi.api.ReflectionAPI;

public class MC_GameProfile
{

	private UUID uuid;
	private String name;

	public MC_GameProfile(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	/**
	 * @return The profile's UUID.
	 */
	public UUID getUuid()
	{
		return this.uuid;
	}

	/**
	 * @return The profile's name.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Convert this Object to the Nms Object (For the Reflection)
	 * 
	 * @return Nms' Object.
	 */
	public Object toNms()
	{
		Constructor<?> constructor = ReflectionAPI.getConstructor(
				ReflectionAPI.getClass("com.mojang.authlib.GameProfile"),
				UUID.class, String.class);
		return ReflectionAPI.newInstance(constructor, this.uuid, this.name);
	}

	public static MC_GameProfile getByMojangObject(Object gameprofile)
	{
		if (gameprofile.getClass().getSimpleName().equals("GameProfile")
				&& gameprofile.getClass().getPackage().getName()
						.equals("com.mojang.authlib"))
		{
			Method methodId = ReflectionAPI.getMethod(gameprofile, "getId");
			Method methodName = ReflectionAPI.getMethod(gameprofile, "getName");
			return new MC_GameProfile(
					(UUID) ReflectionAPI.invokeMethod(gameprofile, methodId),
					(String) ReflectionAPI.invokeMethod(gameprofile,
							methodName));
		}
		else
			return null;
	}

}
