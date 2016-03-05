package com.w67clement.mineapi.nms.v1_9_R1.entity;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.entity.villager.MC_MerchantRecipe;
import com.w67clement.mineapi.entity.villager.MC_Villager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.server.v1_9_R1.EntityVillager;
import net.minecraft.server.v1_9_R1.MerchantRecipe;
import net.minecraft.server.v1_9_R1.MerchantRecipeList;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

/**
 * Created by w67clement on 01/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class MC_Villager_v1_9_R1 extends MC_EntityAgeable_v1_9_R1 implements MC_Villager
{
    private EntityVillager villager;

    public MC_Villager_v1_9_R1(Villager entity)
    {
        super(entity);
        this.villager = ((CraftVillager) entity).getHandle();
    }

    @Override
    public void setProfession(int id)
    {
        this.villager.setProfession(id);
    }

    @Override
    public int getProfession()
    {
        return this.villager.getProfession();
    }

    @Override
    public void setProfession(Profession profession)
    {
        this.setProfession(profession.getId());
    }

    @Override
    public void addMerchantRecipe(MC_MerchantRecipe recipe)
    {
        Field field = ReflectionAPI.getField(this.villager.getClass(), "trades", true);
        MerchantRecipeList recipes = (MerchantRecipeList) ReflectionAPI.getValue(this.villager, field);
        assert recipes != null;
        recipes.add((MerchantRecipe) recipe.toMerchantRecipeNms());
        ReflectionAPI.setValue(this.villager, field, recipes);
    }

    @Override
    public void setMerchantRecipe(List<MC_MerchantRecipe> recipes)
    {
        MerchantRecipeList nms_recipes = recipes.stream().map(recipe -> (MerchantRecipe) recipe.toMerchantRecipeNms()).collect(Collectors.toCollection(MerchantRecipeList::new));
        ReflectionAPI.setValue(this.villager, ReflectionAPI.getField(this.villager.getClass(), "trades", true), nms_recipes);
    }

    @Override
    public void setMerchantRecipe(int index, MC_MerchantRecipe recipe)
    {
        Field field = ReflectionAPI.getField(this.villager.getClass(), "trades", true);
        MerchantRecipeList recipes = (MerchantRecipeList) ReflectionAPI.getValue(this.villager, field);
        assert recipes != null;
        if (index > recipes.size())
        {
            recipes.set(recipes.size(), (MerchantRecipe) recipe.toMerchantRecipeNms());
        }
        else
        {
            recipes.set(index, (MerchantRecipe) recipe.toMerchantRecipeNms());
        }
        ReflectionAPI.setValue(this.villager, field, recipes);
    }

    @Override
    public List<MC_MerchantRecipe> getMerchantRecipeList()
    {
        List<MC_MerchantRecipe> recipes = new ArrayList<>();
        MerchantRecipeList nmsRecipes = (MerchantRecipeList) ReflectionAPI.getValue(this.villager, ReflectionAPI.getField(this.villager.getClass(), "trades", true));
        assert nmsRecipes != null;
        nmsRecipes.forEach(nmsRecipe -> {
            try
            {
                recipes.add(new MC_MerchantRecipe(nmsRecipe));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        return recipes;
    }

    @Override
    public MC_MerchantRecipe getMerchantRecipe(int index)
    {
        try
        {
            return new MC_MerchantRecipe(((MerchantRecipeList) ReflectionAPI.getValue(this.villager, ReflectionAPI.getField(this.villager.getClass(), "trades", true))).get(index));
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
