package com.aol.w67clement.mineapi.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.MineAPI;

/**
 * An Reflection API class make by 67clement
 * 
 * @author 67clement
 * 
 */
public class ReflectionAPI {

	/**
	 * Get an Field
	 * 
	 * @param clazz
	 *            Class where is the Field.
	 * @param fieldName
	 *            Name of the Field.
	 * @param declared
	 *            Field is declared?
	 * @return The field.
	 */
	public static Field getField(Class<?> clazz, String fieldName,
			boolean declared) {
		Field field = null;
		try {
			field = declared ? clazz.getDeclaredField(fieldName) : clazz
					.getField(fieldName);
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			MineAPI.console
					.sendMessage(MineAPI.PREFIX
							+ ChatColor.DARK_RED
							+ "[ERROR]"
							+ ChatColor.RED
							+ " Error in com.aol.w67clement.mineapi.api.ReflectionAPI:getField(Class<?>, String, boolean)");
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Value: clazz: "
					+ clazz.getSimpleName() + " fieldName: " + fieldName
					+ " declared: " + declared);
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED
					+ " Exception: NoSuchFieldException, Message: "
					+ ChatColor.DARK_RED + e.getMessage());
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Stacktrace: ");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return field;
	}

	/**
	 * Change the value of the Field.
	 * 
	 * @param obj
	 *            Object where is the Field.
	 * @param field
	 *            The field
	 * @param value
	 *            The new value of the Field.
	 */
	public static void setValue(Object obj, Field field, Object value) {
		if (field != null) {
			if (!field.isAccessible())
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

	/*
	 * METHODS
	 */

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

	/*
	 * CONSTRUCTOR
	 */

	public static Constructor<?> getConstructor(Class<?> clazz,
			Class<?>... arguments) {
		try {
			return clazz.getConstructor(arguments);
		} catch (NoSuchMethodException e) {
			MineAPI.console
					.sendMessage(MineAPI.PREFIX
							+ ChatColor.DARK_RED
							+ "[ERROR]"
							+ ChatColor.RED
							+ " Error in com.aol.w67clement.mineapi.api.ReflectionAPI:getContructor(Class<?>, Class<?> ...)");
			String argumentsType = "";
			for (int i = 0; i < arguments.length; i++) {
				if (i + 1 == arguments.length) {
					argumentsType = argumentsType
							+ arguments[i].getSimpleName();
				} else {
					argumentsType = argumentsType
							+ arguments[i].getSimpleName() + ", ";
				}
			}
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Value: clazz: "
					+ clazz.getSimpleName() + " argumentsType: "
					+ argumentsType);
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED
					+ " Exception: NoSuchMethodException, Message: "
					+ ChatColor.DARK_RED + e.getMessage());
			MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.DARK_RED
					+ "[ERROR]" + ChatColor.RED + " Stacktrace: ");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return clazz.getConstructors()[0];
	}

	public static Object newInstance(Constructor<?> constructor,
			Object... arguments) {
		try {
			return constructor.newInstance(arguments);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * NMS CLASS
	 */

	/**
	 * Gets an Class in Nms' packages.
	 * 
	 * @param name
	 *            Name of the class.
	 * @return The class!
	 */
	public static Class<?> getNmsClass(String name) {
		try {
			return Class.forName("net.minecraft.server."
					+ MineAPI.getServerVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String checkVersionClass(Class<?> clazz) {
		Package clazzPackage = clazz.getPackage();
		if (clazzPackage.getName().startsWith("org.bukkit.craftbukkit.")) {
			return clazzPackage.getName().substring(23, 30);
		} else if (clazzPackage.getName().startsWith("net.minecraft.server.")) {
			return clazzPackage.getName().substring(21, 28);
		} else {
			return "Unknown";
		}
	}

	public static class NmsClass {

		public static Class<?> getChatSerializerClass() {
			return MineAPI.getServerVersion().equals("v1_8_R1") ? getNmsClass("ChatSerializer")
					: getNmsClass("IChatBaseComponent.ChatSerializer");
		}

		public static Class<?> getIChatBaseComponentClass() {
			return getNmsClass("IChatBaseComponent");
		}

		public static Class<?> getMinecraftServerClass() {
			return getNmsClass("MinecraftServer");
		}

		public static Object getMinecraftServerObject() {
			return getValue(Bukkit.getServer(),
					getField(Bukkit.getServer().getClass(), "console", true));
		}

		public static Object getEntityPlayerByPlayer(Player player) {
			return MineAPI.getNmsManager().getMCPlayer(player).getMC_Handle();
		}

		public static Object getPlayerConnectionByPlayer(Player player) {
			Object nmsPlayer = getEntityPlayerByPlayer(player);
			return ReflectionAPI.getValue(nmsPlayer, ReflectionAPI.getField(
					nmsPlayer.getClass(), "playerConnection", false));
		}

		public static void sendPacket(Player player, Object obj) {
			Object playerConnection = getPlayerConnectionByPlayer(player);
			ReflectionAPI.invokeMethod(playerConnection, ReflectionAPI
					.getMethod(playerConnection, "sendPacket",
							new Class<?>[] { getNmsClass("Packet") }),
					new Object[] { obj });
		}
	}
}
