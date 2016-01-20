package com.w67clement.mineapi.entity.villager;

import com.w67clement.mineapi.entity.MC_EntityAgeable;
import java.util.List;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

public interface MC_Villager extends MC_EntityAgeable
{

	/**
	 * Sets the profession of the villager.
	 *
	 * @param id
	 *            Id of the profession.
	 */
	public void setProfession(int id);

	/**
	 * Sets the profession of the villager.
	 * 
	 * @param profession
	 *            The new profession.
	 */
	public void setProfession(Profession profession);

	/**
	 * Gets the ID of the profession of the villager.
	 * 
	 * @return ID of villager's profession.
	 */
	public int getProfession();

	public void addMerchantRecipe(MC_MerchantRecipe recipe);

	public void setMerchantRecipe(List<MC_MerchantRecipe> recipes);

	public void setMerchantRecipe(int index, MC_MerchantRecipe recipe);

	public List<MC_MerchantRecipe> getMerchantRecipeList();

	public MC_MerchantRecipe getMerchantRecipe(int index);

	public Villager getHandle();

	public Object getMC_Handle();
}
