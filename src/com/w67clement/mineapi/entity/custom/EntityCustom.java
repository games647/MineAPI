package com.w67clement.mineapi.entity.custom;

import org.bukkit.entity.EntityType;

public class EntityCustom
{

	private EntityType entity;
	
	public EntityCustom(EntityType entity) {
		this.entity = entity;
	}
	
	public EntityType getEntityType() {
		return this.entity;
	}
	
}
