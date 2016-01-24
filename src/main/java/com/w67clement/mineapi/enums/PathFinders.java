package com.w67clement.mineapi.enums;

import com.w67clement.mineapi.api.ReflectionAPI;

public enum PathFinders
{

	ArrowAttack(ReflectionAPI.getNmsClass("PathfinderGoalArrowAttack")),
	AvoidTarget(ReflectionAPI.getNmsClass("PathfinderGoalAvoidTarget")),
	Beg(ReflectionAPI.getNmsClass("PathfinderGoalBeg")),
	BreakDoor(ReflectionAPI.getNmsClass("PathfinderGoalBreakDoor")),
	Breed(ReflectionAPI.getNmsClass("PathfinderGoalBreed")),
	DefendVillage(ReflectionAPI.getNmsClass("PathfinderGoalDefendVillage")),
	DoorInteract(ReflectionAPI.getNmsClass("PathfinderGoalDoorInteract")),
	/**
	 * Used for the Sheeps, with the Sheeps eat grass.
	 */
	EatTile(ReflectionAPI.getNmsClass("PathfinderGoalEatTile")),
	FleeSun(ReflectionAPI.getNmsClass("PathfinderGoalFleeSun")),
	Float(ReflectionAPI.getNmsClass("PathfinderGoalFloat")),
	FollowOwner(ReflectionAPI.getNmsClass("PathfinderGoalFollowOwner")),
	FollowParent(ReflectionAPI.getNmsClass("PathfinderGoalFollowParent")),
	GotoTarget(ReflectionAPI.getNmsClass("PathfinderGoalGotoTarget")),
	HurtByTarget(ReflectionAPI.getNmsClass("PathfinderGoalHurtByTarget")),
	Interact(ReflectionAPI.getNmsClass("PathfinderGoalInteract")),
	InteractVillagers(
						ReflectionAPI.getNmsClass(
								"PathfinderGoalInteractVillagers")),
	JumpOnBlock(ReflectionAPI.getNmsClass("PathfinderGoalJumpOnBlock")),
	LeapAtTarget(ReflectionAPI.getNmsClass("PathfinderGoalLeapAtTarget")),
	LookAtPlayer(ReflectionAPI.getNmsClass("PathfinderGoalLookAtPlayer")),
	LookAtTradingPlayer(
						ReflectionAPI.getNmsClass(
								"PathfinderGoalLookAtTradingPlayer")),
	MakeLove(ReflectionAPI.getNmsClass("PathfinderGoalMakeLove")),
	MeleeAttack(ReflectionAPI.getNmsClass("PathfinderGoalMeleeAttack")),
	MoveIndoors(ReflectionAPI.getNmsClass("PathfinderGoalMoveIndoors")),
	MoveThroughVillage(
						ReflectionAPI.getNmsClass(
								"PathfinderGoalMoveThroughVillage")),
	MoveTowardsRestriction(
							ReflectionAPI.getNmsClass(
									"PathfinderGoalMoveTowardsRestriction")),
	MoveTowardsTarget(
						ReflectionAPI.getNmsClass(
								"PathfinderGoalMoveTowardsTarget")),
	NearestAttackableTarget(
							ReflectionAPI.getNmsClass(
									"PathfinderGoalNearestAttackableTarget")),
	NearestAttackableTargetInsentient(
										ReflectionAPI.getNmsClass(
												"PathfinderGoalNearestAttackableTargetInsentient")),
	OcelotAttack(ReflectionAPI.getNmsClass("PathfinderGoalOcelotAttack")),
	OfferFlower(ReflectionAPI.getNmsClass("PathfinderGoalOfferFlower")),
	OpenDoor(ReflectionAPI.getNmsClass("PathfinderGoalOpenDoor")),
	OwnerHurtByTarget(
						ReflectionAPI.getNmsClass(
								"PathfinderGoalOwnerHurtByTarget")),
	OwnerHurtTarget(ReflectionAPI.getNmsClass("PathfinderGoal")),
	/**
	 * Used for the animals, with the animals panic after hurt them.
	 */
	Panic(ReflectionAPI.getNmsClass("PathfinderGoalPanic")),
	PassengerCarrotStick(
							ReflectionAPI.getNmsClass(
									"PathfinderGoalPassengerCarrotStick")),
	Play(ReflectionAPI.getNmsClass("PathfinderGoalPlay")),
	RandomLookaround(
						ReflectionAPI
								.getNmsClass("PathfinderGoalRandomLookaround")),
	RandomStroll(ReflectionAPI.getNmsClass("PathfinderGoalRandomStroll")),
	RandomTargetNonTamed(
							ReflectionAPI.getNmsClass(
									"PathfinderGoalRandomTargetNonTamed")),
	RestrictOpenDoor(
						ReflectionAPI
								.getNmsClass("PathfinderGoalRestrictOpenDoor")),
	RestrictSun(ReflectionAPI.getNmsClass("PathfinderGoalRestrictSun")),
	Sit(ReflectionAPI.getNmsClass("PathfinderGoalSit")),
	Swell(ReflectionAPI.getNmsClass("PathfinderGoalSwell")),
	TakeFlower(ReflectionAPI.getNmsClass("PathfinderGoalTakeFlower")),
	Tame(ReflectionAPI.getNmsClass("PathfinderGoalTame")),
	Target(ReflectionAPI.getNmsClass("PathfinderGoalTarget")),
	TargetNearestPlayer(
						ReflectionAPI.getNmsClass(
								"PathfinderGoalTargetNearestPlayer")),
	Tempt(ReflectionAPI.getNmsClass("PathfinderGoalTempt")),
	TradeWithPlayer(ReflectionAPI.getNmsClass("PathfinderGoalTradeWithPlayer")),
	/**
	 * Used for the villagers, with the villagers farm them farmland.
	 */
	VillagerFarm(ReflectionAPI.getNmsClass("PathfinderGoalVillagerFarm"));

	private Class<?> nmsPathFinder;

	private PathFinders(Class<?> nmsPathFinder) {
		this.nmsPathFinder = nmsPathFinder;
	}

	public Class<?> getNmsPathFinder()
	{
		return this.nmsPathFinder;
	}

}
