package com.w67clement.mineapi.nms.v1_8_R1.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.villager.MC_MerchantRecipe;
import com.w67clement.mineapi.entity.villager.MC_Villager;

import net.minecraft.server.v1_8_R1.EntityVillager;
import net.minecraft.server.v1_8_R1.MerchantRecipe;
import net.minecraft.server.v1_8_R1.MerchantRecipeList;

public class MC_Villager_v1_8_R1 extends MC_EntityAgeable_v1_8_R1 implements MC_Villager
{

	private EntityVillager villager;

	public MC_Villager_v1_8_R1(Villager entity) {
		super(entity);
		this.villager = ((CraftVillager) entity).getHandle();
	}

	@Override
	public void setProfession(int id)
	{
		this.villager.setProfession(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setProfession(Profession profession)
	{
		this.setProfession(profession.getId());
	}

	@Override
	public int getProfession()
	{
		return this.villager.getProfession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addMerchantRecipe(MC_MerchantRecipe recipe)
	{
		Field field = ReflectionAPI.getField(this.villager.getClass(), "bp", true);
		MerchantRecipeList recipes = (MerchantRecipeList) ReflectionAPI.getValue(this.villager, field);
		recipes.add(recipe.toMerchantRecipeNms());
		ReflectionAPI.setValue(this.villager, field, recipes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setMerchantRecipe(List<MC_MerchantRecipe> recipes)
	{
		MerchantRecipeList nms_recipes = new MerchantRecipeList();
		for (MC_MerchantRecipe recipe : recipes)
		{
			nms_recipes.add(recipe.toMerchantRecipeNms());
		}
		ReflectionAPI.setValue(this.villager, ReflectionAPI.getField(this.villager.getClass(), "bp", true),
				nms_recipes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setMerchantRecipe(int index, MC_MerchantRecipe recipe)
	{
		Field field = ReflectionAPI.getField(this.villager.getClass(), "bp", true);
		MerchantRecipeList recipes = (MerchantRecipeList) ReflectionAPI.getValue(this.villager, field);
		if (index > recipes.size())
		{
			recipes.set(recipes.size(), recipe.toMerchantRecipeNms());
		}
		else
		{
			recipes.set(index, recipe.toMerchantRecipeNms());
		}
		ReflectionAPI.setValue(this.villager, field, recipes);
	}

	@Override
	public List<MC_MerchantRecipe> getMerchantRecipeList()
	{
		List<MC_MerchantRecipe> recipes = new ArrayList<MC_MerchantRecipe>();
		MerchantRecipeList nms_recipes = (MerchantRecipeList) ReflectionAPI.getValue(this.villager,
				ReflectionAPI.getField(this.villager.getClass(), "bp", true));
		MerchantRecipe recipe;
		for (int i = 0; i < nms_recipes.size(); i++)
		{
			recipe = (MerchantRecipe) nms_recipes.get(i);
			try
			{
				recipes.add(new MC_MerchantRecipe(recipe));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return recipes;
	}

	@Override
	public MC_MerchantRecipe getMerchantRecipe(int index)
	{
		try
		{
			return new MC_MerchantRecipe(((MerchantRecipeList) ReflectionAPI.getValue(this.villager,
					ReflectionAPI.getField(this.villager.getClass(), "bp", true))).get(index));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Villager getHandle()
	{
		return (Villager) this.villager.getBukkitEntity();
	}

	@Override
	public Object getMC_Handle()
	{
		return this.villager;
	}
}
