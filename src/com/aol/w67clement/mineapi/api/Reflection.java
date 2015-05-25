package com.aol.w67clement.mineapi.api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.ChatColor;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.enums.MinecraftVersion;
import com.aol.w67clement.mineapi.enums.ParticleType;

public class Reflection {

	public static Field getField(Class<?> clazz, String fieldName,
			boolean declared) {
		Field field = null;
		try {
			field = declared ? clazz.getDeclaredField(fieldName) : clazz
					.getField(fieldName);
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return field;
	}

	public static void setValue(Object obj, Field field, Object value) {
		field.setAccessible(true);
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException e) {
			MineAPI.console
					.sendMessage(MineAPI.PREFIX
							+ ChatColor.DARK_RED
							+ "[ERROR]"
							+ ChatColor.RED
							+ " Error in com.aol.w67clement.mineapi.api.Reflection:setValue(Object, Field, Object)");
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED
					+ " Exception: IllegalArgumentException, Message: "
					+ ChatColor.DARK_RED + e.getMessage());
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Stacktrace: ");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			MineAPI.console
					.sendMessage(MineAPI.PREFIX
							+ ChatColor.DARK_RED
							+ "[ERROR]"
							+ ChatColor.RED
							+ " Error in com.aol.w67clement.mineapi.api.Reflection:setValue(Object, Field, Object)");
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED
					+ " Exception: IllegalAccessException, Message: "
					+ ChatColor.DARK_RED + e.getMessage());
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Stacktrace: ");
			e.printStackTrace();
		}
	}

	public static Object getValue(Object obj, Field field) {
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			MineAPI.console
					.sendMessage(MineAPI.PREFIX
							+ ChatColor.DARK_RED
							+ "[ERROR]"
							+ ChatColor.RED
							+ " Error in com.aol.w67clement.mineapi.api.Reflection:getValue(Object, Field)");
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED
					+ " Exception: IllegalArgumentException, Message: "
					+ ChatColor.DARK_RED + e.getMessage());
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Stacktrace: ");
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			MineAPI.console
					.sendMessage(MineAPI.PREFIX
							+ ChatColor.DARK_RED
							+ "[ERROR]"
							+ ChatColor.RED
							+ " Error in com.aol.w67clement.mineapi.api.Reflection:getValue(Object, Field)");
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED
					+ " Exception: IllegalAccessException, Message: "
					+ ChatColor.DARK_RED + e.getMessage());
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Stacktrace: ");
			e.printStackTrace();
			return null;
		}
	}

	public static String getStringValue(Object obj, Field field) {
		if (getValue(obj, field) == null)
			return "null";
		return String.valueOf(getValue(obj, field));
	}

	public static String getStringValue(Object obj, String field,
			boolean declared) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f = getField(obj.getClass(), field, declared);
		String str = (String) f.get(obj);
		return str;
	}

	public static ParticleType getParticleValue(Object obj, String fieldName,
			boolean declared) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getField(obj.getClass(), fieldName, declared);
		for (ParticleType type : ParticleType.values()) {
			if (MinecraftVersion.getServerVersion().equals(
					MinecraftVersion.v1_8_R1)) {
				for (net.minecraft.server.v1_8_R1.EnumParticle particle : net.minecraft.server.v1_8_R1.EnumParticle
						.values()) {
					if (field.get(obj).equals(particle)) {
						if (type.getParticleNmsName().equals(
								particle.toString())) {
							return type;
						}
					}
				}
			} else if (MinecraftVersion.getServerVersion().equals(
					MinecraftVersion.v1_8_R2)) {
				for (net.minecraft.server.v1_8_R2.EnumParticle particle : net.minecraft.server.v1_8_R2.EnumParticle
						.values()) {
					if (field.get(obj).equals(particle)) {
						if (type.getParticleNmsName().equals(
								particle.toString())) {
							return type;
						}
					}
				}
			}
		}
		return null;
	}

	public static Method getMethod(Object obj, String name,
			Class<?>... parameterTypes) {
		try {
			return obj.getClass().getMethod(name, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object invokeMethod(Object obj, Method method,
			Object... arguments) {
		try {
			return method.invoke(obj, arguments);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
}
