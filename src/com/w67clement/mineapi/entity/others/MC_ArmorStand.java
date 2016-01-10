package com.w67clement.mineapi.entity.others;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

import com.w67clement.mineapi.entity.MC_EntityLiving;

public interface MC_ArmorStand extends MC_EntityLiving
{

	/**
	 * Sets whether this armor stand is scaled down
	 *
	 * @param small
	 *            Whether this is scaled down.
	 */
	public void setSmall(boolean small);

	/**
	 * Returns whether this armor stand is scaled down
	 *
	 * @return Whether this is scaled down
	 */
	public boolean isSmall();

	/**
	 * Returns whether the armor stand has a base plate.
	 *
	 * @return Whether it has a base plate.
	 */
	public boolean hasBasePlate();

	/**
	 * Sets whether the armor stand has a base plate
	 *
	 * @param basePlate
	 *            If is has a base plate
	 */
	public void setBasePlate(boolean basePlate);

	/**
	 * Returns the armor stand's body's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @return The current body pose.
	 */
	public EulerAngle getBodyPose();

	/**
	 * Sets the armor stand's body's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @param bodyPose
	 *            The new body pose.
	 */
	public void setBodyPose(EulerAngle bodyPose);

	/**
	 * Returns the armor stand's left arm's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @return The current left arm pose.
	 */
	public EulerAngle getLeftArmPose();

	/**
	 * Sets the armor stand's left arm's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @param leftArmPose
	 *            The new left arm pose
	 */
	public void setLeftArmPose(EulerAngle leftArmPose);

	/**
	 * Returns the armor stand's right arm's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @return The current right arm pose.
	 */
	public EulerAngle getRightArmPose();

	/**
	 * Sets the armor stand's right arm's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @param rightArmPose
	 *            The new right arm pose.
	 */
	public void setRightArmPose(EulerAngle rightArmPose);

	/**
	 * Returns the armor stand's left leg's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @return The current left leg pose.
	 */
	public EulerAngle getLeftLegPose();

	/**
	 * Sets the armor stand's left leg's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @param leftLegPose
	 *            The new left leg pose.
	 */
	public void setLeftLegPose(EulerAngle leftLegPose);

	/**
	 * Returns the armor stand's right leg's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @return The current right leg pose.
	 */
	public EulerAngle getRightLegPose();

	/**
	 * Sets the armor stand's right leg's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @param rightLegPose
	 *            The new right leg pose.
	 */
	public void setRightLegPose(EulerAngle rightLegPose);

	/**
	 * Returns the armor stand's head's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @return The current pose.
	 */
	public EulerAngle getHeadPose();

	/**
	 * Sets the armor stand's head's current pose as a
	 * {@link org.bukkit.util.EulerAngle}
	 *
	 * @param headPose
	 *            The new head pose.
	 */
	public void setHeadPose(EulerAngle headPose);

	/**
	 * Returns whether gravity applies to this armor stand.
	 *
	 * @return Whether gravity applies.
	 */
	public boolean hasGravity();

	/**
	 * Sets whether gravity applies to this armor stand.
	 *
	 * @param gravity
	 *            Whether gravity should apply.
	 */
	public void setGravity(boolean gravity);

	/**
	 * Returns whether this armor stand has arms.
	 *
	 * @return Whether this has arms or not.
	 */
	public boolean hasArms();

	/**
	 * Sets whether this armor stand has arms.
	 *
	 * @param arms
	 *            Whether this has arms or not.
	 */
	public void setArms(boolean arms);

	/**
	 * Returns whether this armor stand has marker.
	 * 
	 * @return Whether this has marker or not.
	 */
	public boolean hasMarker();

	/**
	 * Sets if this armor stand has marker.
	 * 
	 * @param marker
	 *            Whether this has marker or not.
	 */
	public void setMarker(boolean marker);

	public ArmorStand getArmorHandle();

	public Object getMC_Handle();

}
