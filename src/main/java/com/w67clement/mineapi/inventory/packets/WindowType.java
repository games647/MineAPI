package com.w67clement.mineapi.inventory.packets;

import java.util.HashMap;
import java.util.Map.Entry;
import org.bukkit.event.inventory.InventoryType;

/**
 * Different types of Inventory in Minecraft. You can register a new Inventory
 * Type used for mods.
 *
 * @author w67clement
 */
public class WindowType
{

    public static final WindowType ANVIL = new WindowType("anvil", "minecraft:anvil");
    public static final WindowType BEACON = new WindowType("beacon", "minecraft:beacon");
    public static final WindowType BREWING_STAND = new WindowType("brewing_stand", "minecraft:brewing_stand");
    public static final WindowType CHEST = new WindowType("chest", "minecraft:chest");
    public static final WindowType CRAFTING_TABLE = new WindowType("crafting_table", "minecraft:crafting_table");
    public static final WindowType DISPENSER = new WindowType("dispenser", "minecraft:dispenser");
    public static final WindowType DROPPER = new WindowType("dropper", "minecraft:dropper");
    public static final WindowType ENCHANTMENT_TABLE = new WindowType("enchantment_table", "minecraft:enchanting_table");
    public static final WindowType FURNACE = new WindowType("furnace", "minecraft:furnace");
    public static final WindowType HOPPER = new WindowType("hopper", "minecraft:hopper");
    public static final WindowType HORSE = new WindowType("horse", "EntityHorse");
    public static final WindowType VILLAGER = new WindowType("villager", "minecraft:villager");

    private static HashMap<String, WindowType> windowTypes = new HashMap<>();

    static
    {
        registerWindowType(ANVIL);
        registerWindowType(BEACON);
        registerWindowType(BREWING_STAND);
        registerWindowType(CHEST);
        registerWindowType(CRAFTING_TABLE);
        registerWindowType(DISPENSER);
        registerWindowType(DROPPER);
        registerWindowType(ENCHANTMENT_TABLE);
        registerWindowType(FURNACE);
        registerWindowType(HOPPER);
        registerWindowType(HORSE);
        registerWindowType(VILLAGER);
    }

    private String name;
    private String mcValue;

    private WindowType(String name, String mcValue)
    {
        this.name = name;
        this.mcValue = mcValue;
    }

    public static WindowType registerWindowType(String name, String mc_value)
    {
        if (windowTypes.containsKey(name))
            return windowTypes.get(name);
        else
        {
            WindowType type = new WindowType(name, mc_value);
            windowTypes.put(name, type);
            return type;
        }
    }

    private static void registerWindowType(WindowType type)
    {
        if (windowTypes.containsKey(type.getName()))
            throw new IllegalArgumentException("The type has already registered!");
        else
            windowTypes.put(type.getName(), type);
    }

    public static WindowType getByMCValue(String mcValue)
    {
        for (Entry<String, WindowType> entry : windowTypes.entrySet())
        {
            if (entry.getValue().getMCValue().equals(mcValue))
            {
                return entry.getValue();
            }
        }
        return null;
    }

    public static WindowType getByName(String name)
    {
        if (windowTypes.containsKey(name))
            return windowTypes.get(name);
        else
            return null;
    }

    public static WindowType getByInventory(InventoryType inventoryType)
    {
        switch (inventoryType)
        {
            case ANVIL:
                return WindowType.ANVIL;
            case BEACON:
                return WindowType.BEACON;
            case BREWING:
                return WindowType.BREWING_STAND;
            case CHEST:
                return WindowType.CHEST;
            case DISPENSER:
                return WindowType.DISPENSER;
            case DROPPER:
                return WindowType.DROPPER;
            case ENCHANTING:
                return WindowType.ENCHANTMENT_TABLE;
            case FURNACE:
                return WindowType.FURNACE;
            case HOPPER:
                return WindowType.HOPPER;
            case MERCHANT:
                return WindowType.VILLAGER;
            case WORKBENCH:
                return WindowType.CRAFTING_TABLE;
            default:
                return null;
        }
    }

    /**
     * Gets the name of the type.
     *
     * @return Name of the type.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the name for the Minecraft system or mods.
     *
     * @return Name with the MC format.
     */
    public String getMCValue()
    {
        return this.mcValue;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof WindowType)
        {
            WindowType type = (WindowType) object;
            if (!type.getName().equals(this.getName()))
                return false;
            if (!type.getMCValue().equals(this.getMCValue()))
                return false;
            return true;
        }
        else
            return false;
    }

}
