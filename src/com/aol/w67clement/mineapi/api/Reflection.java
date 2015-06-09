package com.aol.w67clement.mineapi.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.aol.w67clement.mineapi.enums.MinecraftVersion;
import com.aol.w67clement.mineapi.enums.ParticleType;

public class Reflection {

	public static Field getField(Class<?> clazz, String fieldName,
			boolean declared) {
		return ReflectionAPI.getField(clazz, fieldName, declared);
	}

	public static void setValue(Object obj, Field field, Object value) {
		ReflectionAPI.setValue(obj, field, value);
	}

	public static Object getValue(Object obj, Field field) {
		return ReflectionAPI.getValue(obj, field);
	}

	public static String getStringValue(Object obj, Field field) {
		return ReflectionAPI.getStringValue(obj, field);
	}

	public static String getStringValue(Object obj, String field,
			boolean declared) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		return ReflectionAPI.getStringValue(obj, field, declared);
	}

	@Deprecated
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
		return ReflectionAPI.getMethod(obj, name, parameterTypes);
	}

	public static Object invokeMethod(Object obj, Method method,
			Object... arguments) {
		return ReflectionAPI.invokeMethod(obj, method, arguments);
	}
}
