package com.w67clement.mineapi.entity;

import java.util.List;

/**
 * Represents a living entity, such as a monster or player
 */
public interface MC_EntityLiving extends MC_Entity {
	
	/**
	 *  Sets if entity has disabled IA.
	 * @param ai Disable IA or enable it.
	 */
	public void setAIDisabled(boolean ai);
	
	/**
	 *  Gets if entity has disabled IA.
	 * @return Disabled IA or not
	 */
	public boolean isAIDisabled();
	
	/**
	 * Sets the entity's health from 0 to getMaxHealth(), where 0 is dead.
	 * @param health New health represented from 0 to max.
	 */
	public void setHealth(double health);
	
	/**
	 * Gets the entity's health from 0 to getMaxHealth(), where 0 is dead.
	 * @return Health represented from 0 to max.
	 */
	public double getHealth();
	
	/**
	 * Sets the maximum health this entity can have.
     * If the health of the entity is above the value provided it will be set to that value.
	 * @param health Amount of health to set the maximum to.
	 */
	public void setMaxHealth(double health);
	
	/**
	 * Gets the maximum health this entity has.
	 * @return Maximum health
	 */
	public double getMaxHealth();
	
	/**
     * Gets the entity that the living entity has targeted.
     *
     * @return Entity that the living entity has targeted.
     */
	public List<MC_Entity> getTargetEntities();

}
