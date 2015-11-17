package com.w67clement.mineapi.enums.mc;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.MC_Entity;
import com.w67clement.mineapi.entity.animals.MC_Pig;
import com.w67clement.mineapi.entity.monster.MC_EntityEnderman;
import com.w67clement.mineapi.entity.others.MC_ArmorStand;
import com.w67clement.mineapi.entity.player.MC_Player;
import com.w67clement.mineapi.entity.villager.MC_Villager;

public enum MC_EntityType {

	ENTITY(-1, "Entity", ReflectionAPI.getNmsClass("Entity"), Entity.class,
			MC_Entity.class),
	PLAYER(-1, null, ReflectionAPI.getNmsClass("EntityPlayer"), Player.class,
			MC_Player.class),
	ARMORSTAND(30, "AmorStand", ReflectionAPI.getNmsClass("EntityArmorStand"), ArmorStand.class,
			MC_ArmorStand.class),
	ENDERMAN(58, "Enderman", ReflectionAPI.getNmsClass("EntityEnderman"), Enderman.class,
			MC_EntityEnderman.class),
	PIG(90, "Pig", ReflectionAPI.getNmsClass("EntityPig"), Pig.class,
			MC_Pig.class),
	VILLAGER(120, "Villager", ReflectionAPI.getNmsClass("EntityVillager"), Villager.class,
			MC_Villager.class);

	private int id;
	private String entityName;
	private Class<?> nmsClass;
	private Class<?> bukkitClass;
	private Class<?> mineapiClass;

	private MC_EntityType(int id, String entityName, Class<?> nmsClass,
			Class<?> bukkitClass, Class<?> mineapiClass) {
		this.id = id;
		this.entityName = entityName;
		this.nmsClass = nmsClass;
		this.bukkitClass = bukkitClass;
		this.mineapiClass = mineapiClass;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getEntityName() {
		return this.entityName;
	}
	
	public Class<?> getNmsClass() {
		return this.nmsClass;
	}
	
	public Class<?> getBukkitClass() {
		return this.bukkitClass;
	}
	
	public Class<?> getMineAPIClass() {
		return this.mineapiClass;
	}

}

//End of EntityType enumeration.