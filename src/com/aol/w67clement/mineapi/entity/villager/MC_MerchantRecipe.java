package com.aol.w67clement.mineapi.entity.villager;

import net.minecraft.server.v1_8_R1.MerchantRecipe;

import org.bukkit.inventory.ItemStack;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.api.ReflectionAPI;

public class MC_MerchantRecipe {

	private ItemStack buyingItem1;
	private ItemStack buyingItem2;
	private ItemStack sellingItem;

	private int uses;
	private int maxUses;

	private boolean rewardExp;

	/**
	 * Constructor of MC_MerchantRecipe
	 * 
	 * @param obj
	 *            MerchantRecipe nms' Object.
	 */
	public MC_MerchantRecipe(Object obj) throws Exception {
		if (obj != null) {
			Class<?> clazz = obj.getClass();
			if (MineAPI.getServerVersion().equals("v1_8_R1")
					&& clazz.equals(ReflectionAPI.getNmsClass("MerchantRecipe"))) {
				net.minecraft.server.v1_8_R1.MerchantRecipe recipe = (MerchantRecipe) obj;
				this.buyingItem1 = org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem1());
				this.buyingItem2 = org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem2());
				this.sellingItem = org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem3());
				this.uses = recipe.e();
				this.maxUses = recipe.f();
				this.rewardExp = recipe.j();
			} else if (MineAPI.getServerVersion().equals("v1_8_R2")
					&& clazz.equals(ReflectionAPI.getNmsClass("BlockPosition"))) {
				net.minecraft.server.v1_8_R2.MerchantRecipe recipe = (net.minecraft.server.v1_8_R2.MerchantRecipe) obj;
				this.buyingItem1 = org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem1());
				this.buyingItem2 = org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem2());
				this.sellingItem = org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem3());
				this.uses = recipe.e();
				this.maxUses = recipe.f();
				this.rewardExp = recipe.j();
			} else if (MineAPI.getServerVersion().equals("v1_8_R3")
					&& clazz.equals(ReflectionAPI.getNmsClass("BlockPosition"))) {
				net.minecraft.server.v1_8_R3.MerchantRecipe recipe = (net.minecraft.server.v1_8_R3.MerchantRecipe) obj;
				this.buyingItem1 = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem1());
				this.buyingItem2 = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem2());
				this.sellingItem = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
						.asBukkitCopy(recipe.getBuyItem3());
				this.uses = recipe.e();
				this.maxUses = recipe.f();
				this.rewardExp = recipe.j();
			} else {
				throw new Exception("MerchantRecipe was invalid.");
			}
		} else {
			throw new Exception("MerchantRecipe object cannot be null.");
		}
	}

	public MC_MerchantRecipe(ItemStack buyingItem1, ItemStack sellingItem) {
		this.buyingItem1 = buyingItem1;
		this.sellingItem = sellingItem;
		this.uses = 0;
		this.maxUses = 7;
		this.rewardExp = true;
	}

	public void setBuyItem(int slot, ItemStack stack) {
		if (slot == 1) {
			this.buyingItem1 = stack;
		} else if (slot >= 2) {
			this.buyingItem2 = stack;
		}
	}

	public ItemStack getBuyItem(int slot) {
		if (slot == 1) {
			return this.buyingItem1;
		} else if (slot >= 2) {
			return this.buyingItem2;
		}
		return null;
	}

	public boolean hasSecondItem() {
		return this.buyingItem2 != null;
	}

	public void setSellingItem(ItemStack stack) {
		this.sellingItem = stack;
	}

	public ItemStack getSellingItem() {
		return this.sellingItem;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

	public void resetUses() {
		this.setUses(0);
	}

	public void addUse() {
		this.setUses(this.getUses() + 1);
	}

	public int getUses() {
		return this.uses;
	}

	public void setMaxUses(int maxUses) {
		this.maxUses = maxUses;
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public void setRewardExp(boolean flag) {
		this.rewardExp = flag;
	}

	public boolean getRewardExp() {
		return this.rewardExp;
	}

	public Object toMerchantRecipeNms() {
		// MC 1.8.R1
		if (MineAPI.getServerVersion().equals("v1_8_R1")) {
			// New Merchant recipe
			MerchantRecipe recipe = new MerchantRecipe(
					org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack
							.asNMSCopy(this.buyingItem1),
					org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack
							.asNMSCopy(this.sellingItem));
			// Modify properties
			ReflectionAPI.setValue(recipe, ReflectionAPI.getField(
					recipe.getClass(), "rewardExp", true), this.rewardExp);
			ReflectionAPI.setValue(recipe,
					ReflectionAPI.getField(recipe.getClass(), "uses", true),
					this.uses);
			ReflectionAPI.setValue(recipe,
					ReflectionAPI.getField(recipe.getClass(), "maxUses", true),
					this.maxUses);
			if (hasSecondItem())
				ReflectionAPI.setValue(recipe, ReflectionAPI.getField(
						recipe.getClass(), "buyingItem2", true),
						org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack
								.asNMSCopy(this.buyingItem2));
			// Done.
			return recipe;
			// MC 1.8.R2
		} else if (MineAPI.getServerVersion().equals("v1_8_R2")) {
			// New Merchant recipe
			net.minecraft.server.v1_8_R2.MerchantRecipe recipe = new net.minecraft.server.v1_8_R2.MerchantRecipe(
					org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack
							.asNMSCopy(this.buyingItem1),
					org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack
							.asNMSCopy(this.sellingItem));
			// Modify properties
			ReflectionAPI.setValue(recipe, ReflectionAPI.getField(
					recipe.getClass(), "rewardExp", true), this.rewardExp);
			ReflectionAPI.setValue(recipe,
					ReflectionAPI.getField(recipe.getClass(), "uses", true),
					this.uses);
			ReflectionAPI.setValue(recipe,
					ReflectionAPI.getField(recipe.getClass(), "maxUses", true),
					this.maxUses);
			if (hasSecondItem())
				ReflectionAPI.setValue(recipe, ReflectionAPI.getField(
						recipe.getClass(), "buyingItem2", true),
						org.bukkit.craftbukkit.v1_8_R2.inventory.CraftItemStack
								.asNMSCopy(this.buyingItem2));
			// Done.
			return recipe;
			// MC 1.8.R3
		} else if (MineAPI.getServerVersion().equals("v1_8_R3")) {
			// New Merchant recipe
			net.minecraft.server.v1_8_R3.MerchantRecipe recipe = new net.minecraft.server.v1_8_R3.MerchantRecipe(
					org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
							.asNMSCopy(this.buyingItem1),
					org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
							.asNMSCopy(this.sellingItem));
			// Modify properties
			ReflectionAPI.setValue(recipe, ReflectionAPI.getField(
					recipe.getClass(), "rewardExp", true), this.rewardExp);
			ReflectionAPI.setValue(recipe,
					ReflectionAPI.getField(recipe.getClass(), "uses", true),
					this.uses);
			ReflectionAPI.setValue(recipe,
					ReflectionAPI.getField(recipe.getClass(), "maxUses", true),
					this.maxUses);
			if (hasSecondItem())
				ReflectionAPI.setValue(recipe, ReflectionAPI.getField(
						recipe.getClass(), "buyingItem2", true),
						org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
								.asNMSCopy(this.buyingItem2));

			// Done.
			return recipe;
		} else {
			return null;
		}
	}
}
