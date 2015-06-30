package com.aol.w67clement.mineapi.entity.villager;

import java.util.List;

import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import com.aol.w67clement.mineapi.entity.MC_EntityAgeable;

public interface MC_Villager extends MC_EntityAgeable {
	
	public void setProfession(int id);
	
	public void setProfession(Profession profession);

	public int getProfession();
	
	public void addMerchantRecipe(MC_MerchantRecipe recipe);
	
	public void setMerchantRecipe(List<MC_MerchantRecipe> recipes);
	
	public void setMerchantRecipe(int index, MC_MerchantRecipe recipe);
	
	public List<MC_MerchantRecipe> getMerchantRecipeList();
	
	public MC_MerchantRecipe getMerchantRecipe(int index);
	
	public Villager getHandle();
	
	public Object getMC_Handle();
}
