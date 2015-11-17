package com.w67clement.mineapi.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.util.EulerAngle;

import com.w67clement.mineapi.api.ReflectionAPI;

public class MC_Utils
{

	public static class EulerAngleUtils
	{

		public static EulerAngle fromNms(Object vector3f)
		{
			if (vector3f.getClass().equals(ReflectionAPI.getNmsClass("Vector3f")))
			{
				Method getX = ReflectionAPI.getMethod(vector3f, "getX", new Class<?>[] {});
				Method getY = ReflectionAPI.getMethod(vector3f, "getY", new Class<?>[] {});
				Method getZ = ReflectionAPI.getMethod(vector3f, "getZ", new Class<?>[] {});
				double x = Math.toRadians((double) ReflectionAPI.invokeMethod(vector3f, getX, new Object[] {}));
				double y = Math.toRadians((double) ReflectionAPI.invokeMethod(vector3f, getY, new Object[] {}));
				double z = Math.toRadians((double) ReflectionAPI.invokeMethod(vector3f, getZ, new Object[] {}));
				return new EulerAngle(x, y, z);
			}
			return null;
		}

		public static Object toNms(EulerAngle eulerAngle)
		{
			Class<?> clazz = ReflectionAPI.getNmsClass("Vector3f");
			Constructor<?> constructor = ReflectionAPI.getConstructor(clazz, Float.class, Float.class, Float.class);
			return ReflectionAPI.newInstance(constructor, (float) Math.toDegrees(eulerAngle.getX()),
					(float) Math.toDegrees(eulerAngle.getY()), (float) Math.toDegrees(eulerAngle.getZ()));
		}

	}

}
