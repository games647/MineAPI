package com.aol.w67clement.mineapi.api.wrappers;

import java.lang.reflect.Field;

import org.apache.commons.lang.Validate;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.api.Reflection;
import com.aol.w67clement.mineapi.enums.ParticleType;

public class PacketWrapper {

	private Object packet;

	public PacketWrapper(Object packet) {
		this.packet = packet;
	}

	public void setValue(String fieldName, boolean declared, Object value) {
		Validate.notNull(fieldName, "Field name cannot be null!");
		Field field = null;
		try {
			field = Reflection.getField(this.packet.getClass(), fieldName, declared);
			field.setAccessible(true);
			for (ParticleType type : ParticleType.values()) {
				if (value.equals(type)) {
					if (MineAPI.getServerVersion().equals("v1_8_R1")) {
						field.set(this.packet,
								net.minecraft.server.v1_8_R1.EnumParticle
										.valueOf(type.getParticleNmsName()));
						return;
					} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
						field.set(this.packet,
								net.minecraft.server.v1_8_R2.EnumParticle
										.valueOf(type.getParticleNmsName()));
						return;
					}
				}
			}
			field.set(this.packet, value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Object getValue(String fieldName, boolean declared)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Validate.notNull(fieldName, "Field name cannot be null!");
		return Reflection.getField(packet.getClass(), fieldName, declared).get(
				packet);
	}

	public Field getField(String fieldname, boolean declared)
			throws NoSuchFieldException, SecurityException {
		return Reflection.getField(this.packet.getClass(), fieldname, declared);
	}

	public String getStringValue(String fieldName, boolean declared)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		Validate.notNull(fieldName, "Field name cannot be null!");
		return Reflection.getStringValue(packet, fieldName, declared);
	}

	public ParticleType getParticleValue(String fieldName, boolean declared)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Validate.notNull(fieldName, "Field name cannot be null!");
		return Reflection.getParticleValue(packet, fieldName, declared);
	}

	public Object getPacket() {
		return this.packet;
	}

	public Class<?> getPacketClass() {
		return this.packet.getClass();
	}

	public String getPacketName() {
		return this.packet.getClass().getSimpleName();
	}
}
