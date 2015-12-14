package com.w67clement.mineapi.entity;

import org.bukkit.entity.Entity;

/**
 * Represents a base entity in the world
 * 
 * @author 67clement
 */
public interface MC_Entity {

	/**
	 *  Returns a unique id for this entity
	 * @return Entity's id.
	 */
	public int getEntityId();
	
	/**
     * Sets a custom name on a mob. This name will be used in death messages
     * and can be sent to the client as a nameplate over the mob.
     * <p>
     * Setting the name to null or an empty string will clear it.
     * <p>
     * This value has no effect on players, they will always use their real
     * name.
     *
     * @param customName The name to set.
     */
	public void setCustomName(String customName);
	
	/**
     * Gets the custom name on a mob. If there is no name this method will
     * return null.
     * <p>
     * This value has no effect on players, they will always use their real
     * name.
     *
     * @return Name of the mob or null
     */
	public String getCustomName();
	
	/**
	 *  Gets if the mob has custom name.
	 * @return Has custom name or not.
	 */
	public boolean hasCustomName();
	
	/**
     * Sets whether or not to display the mob's custom name client side. The
     * name will be displayed above the mob similarly to a player.
     * <p>
     * This value has no effect on players, they will always display their
     * name.
     *
     * @param visible Custom name or not
     */
	public void setCustomNameVisible(boolean visible);
	
	/**
     * Gets whether or not the mob's custom name is displayed client side.
     * <p>
     * This value has no effect on players, they will always display their
     * name.
     *
     * @return If the custom name is displayed
     */
	public boolean getCustomNameVisible();
	
	/**
     * Sets whether the mob should be
     * invisible or not
     *
     * @param visible Whether the mob is invisible or not
     */
	public void setInvisible(boolean invisible);
	
	/**
     * Returns whether the mob should be
     * invisible or not
     *
     * @return Whether the mob is invisible or not
     */
	public boolean isInvisible();
	
	/**
	 * Sets whether remove the hitbox and the collisions.
	 * @param noClip Remove or add.
	 */
	public void setNoClip(boolean noClip);
	
	/**
	 * Gets if entity has hitbox and collisions activated.
	 * @return Entity has no clip.
	 */
	public boolean hasNoClip();
	
	/**
	 * Sets the size of the bounding box.
	 * @param width Width of the bounding box.
	 * @param length Length of the bounding box.
	 */
	public void setSize(float width, float length);
	
	/**
	 *  Gets the bukkit's entity.
	 * @return The bukkit's entity.
	 */
	public Entity getEntityHandle();
	
	/**
	 *  Gets the nms' handle of the mob.
	 * @return An nms' entity object.
	 */
	public Object getMC_Handle();
	
}
