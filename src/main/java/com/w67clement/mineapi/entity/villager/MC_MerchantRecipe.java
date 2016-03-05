package com.w67clement.mineapi.entity.villager;

import com.w67clement.mineapi.api.ReflectionAPI.*;
import java.lang.reflect.Constructor;
import org.bukkit.inventory.ItemStack;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * An Merchant recipe generator.
 *
 * @author w67clement
 */
public class MC_MerchantRecipe
{

    private ItemStack buyingItem1;
    private ItemStack buyingItem2;
    private ItemStack sellingItem;

    private int uses;
    private int maxUses;

    private boolean rewardExp;

    /**
     * It's the constructor of MC_MerchantRecipe, it using an nms' MerchantRecie
     * Object
     *
     * @param obj The nms' MerchantRecipe Object
     *
     * @throws Exception Throwing Exception whether MerchantRecipe Object are invalid
     *                   or null.
     */
    public MC_MerchantRecipe(Object obj) throws Exception
    {
        if (obj != null)
        {
            Class<?> clazz = obj.getClass();
            if (clazz.equals(getNmsClass("MerchantRecipe")))
            {
                this.buyingItem1 = ItemStackConverter.fromNms(invokeMethod(obj, getMethod(obj, "getBuyItem1")));
                this.buyingItem2 = ItemStackConverter.fromNms(invokeMethod(obj, getMethod(obj, "getBuyItem2")));
                this.sellingItem = ItemStackConverter.fromNms(invokeMethod(obj, getMethod(obj, "getBuyItem3")));
                this.uses = getIntValue(obj, getField(clazz, "uses", false));
                this.maxUses = getIntValue(obj, getField(clazz, "maxUses", false));
                this.rewardExp = getValueWithType(obj, getField(clazz, "rewardExp", false), boolean.class);
            }
            else
            {
                throw new Exception("MerchantRecipe was invalid.");
            }
        }
        else
        {
            throw new Exception("MerchantRecipe object cannot be null.");
        }
    }

    public MC_MerchantRecipe(ItemStack buyingItem1, ItemStack sellingItem)
    {
        this.buyingItem1 = buyingItem1;
        this.sellingItem = sellingItem;
        this.uses = 0;
        this.maxUses = 7;
        this.rewardExp = true;
    }

    public void setBuyItem(int slot, ItemStack stack)
    {
        if (slot == 1)
        {
            this.buyingItem1 = stack;
        }
        else if (slot >= 2)
        {
            this.buyingItem2 = stack;
        }
    }

    /**
     * Gets the buying item in slot 1 or 2
     *
     * @param slot Slot is a Integer value, represented from 1 to 2.
     *
     * @return THe buying item.
     */
    public ItemStack getBuyItem(int slot)
    {
        if (slot == 1)
        {
            return this.buyingItem1;
        }
        else if (slot >= 2)
        {
            return this.buyingItem2;
        }
        return null;
    }

    /**
     * Gets whether 2 buying items.
     *
     * @return Whether 2 buying items or not.
     */
    public boolean hasSecondItem()
    {
        return this.buyingItem2 != null;
    }

    /**
     * Gets the selling item of the Merchant recipe.
     *
     * @return Selling item.
     */
    public ItemStack getSellingItem()
    {
        return this.sellingItem;
    }

    /**
     * Sets the selling item of the Merchant recipe. <br>
     * <br>
     * <b>Note</b>: The parameter stack cannot be null.
     *
     * @param stack The new selling item.
     */
    public void setSellingItem(ItemStack stack)
    {
        this.sellingItem = stack;
    }

    /**
     * Reset the number of using to 0.
     */
    public void resetUses()
    {
        this.setUses(0);
    }

    /**
     * Add 1 use of the current number of using.
     */
    public void addUse()
    {
        this.setUses(this.getUses() + 1);
    }

    /**
     * Gets the number of using this MerchantRecipe.
     *
     * @return The current number of using.
     */
    public int getUses()
    {
        return this.uses;
    }

    /**
     * Sets the number of using this MerchantRecipe
     *
     * @param uses The new number of using
     */
    public void setUses(int uses)
    {
        this.uses = uses;
    }

    /**
     * Gets the number of maximum uses this MerchantRecipe.
     *
     * @return The current maximum uses number.
     */
    public int getMaxUses()
    {
        return this.maxUses;
    }

    /**
     * Sets the maximum uses of this MerchantRecipe.
     *
     * @param maxUses The new maximum uses value.
     */
    public void setMaxUses(int maxUses)
    {
        this.maxUses = maxUses;
    }

    /**
     * Gets whether this MerchantRecipe reward exp on use.
     *
     * @return Whether reward exp on use.
     */
    public boolean getRewardExp()
    {
        return this.rewardExp;
    }

    /**
     * Sets whether this MerchantRecipe reward exp on use.
     *
     * @param flag Whether reward exp on use.
     */
    public void setRewardExp(boolean flag)
    {
        this.rewardExp = flag;
    }

    /**
     * Generating MerchantRecipe nms' object with this class.
     *
     * @return An MerchantRecipe object.
     */
    public Object toMerchantRecipeNms()
    {
        // Gets the class
        Class<?> merchantRecipeClass = getNmsClass("MerchantRecipe");
        // Gets the nms' ItemStack
        Class<?> nmsItemStack = getNmsClass("ItemStack");
        // Gets the constructor
        Constructor<?> recipeConstructor = getConstructor(merchantRecipeClass, nmsItemStack, nmsItemStack);
        // Use constructor
        Object recipe = newInstance(recipeConstructor, ItemStackConverter.toNms(this.buyingItem1), ItemStackConverter.toNms(this.sellingItem));
        // Modify properties
        assert recipe != null : "Error: [{\"class\":\"MC_MerchantRecipe\",\"method\":\"toMerchantRecipeNms()\",\"line\":224,\"error\":\"Object recipe is null\"}], please contact author and report the bug.";
        setValue(recipe, getField(recipe.getClass(), "rewardExp", true), this.rewardExp);
        setValue(recipe, getField(recipe.getClass(), "uses", true), this.uses);
        setValue(recipe, getField(recipe.getClass(), "maxUses", true), this.maxUses);
        if (hasSecondItem())
            setValue(recipe, getField(recipe.getClass(), "buyingItem2", true), ItemStackConverter.toNms(this.buyingItem2));
        // Done.
        return recipe;
    }
}
