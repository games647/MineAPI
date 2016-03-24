package com.w67clement.mineapi.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.MineAPI;
import java.lang.reflect.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sun.misc.Unsafe;

/**
 * A Reflection API class make by w67clement
 *
 * @author w67clement
 */
public class ReflectionAPI
{

    /**
     * Gets a Field.
     *
     * @param clazz     Class where is the Field.
     * @param fieldName Name of the Field.
     * @param declared  Field is declared?
     *
     * @return The field.
     */
    public static Field getField(Class<?> clazz, String fieldName, boolean declared)
    {
        Field field = null;
        try
        {
            field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            int modifiers = modifiersField.getInt(field);
            modifiers &= ~Modifier.FINAL;
            modifiersField.setInt(field, modifiers);
        }
        catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e)
        {
            e.printStackTrace();
        }
        return field;
    }

    /**
     * Changes the value of the Field.
     *
     * @param obj   Object where is the Field.
     * @param field The field
     * @param value The new value of the Field.
     */
    public static void setValue(Object obj, Field field, Object value)
    {
        if (field != null)
        {
            if (!field.isAccessible())
                field.setAccessible(true);
            try
            {
                field.set(obj, value);
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static Object getValue(Object obj, Field field)
    {
        field.setAccessible(true);
        try
        {
            return field.get(obj);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringValue(Object obj, Field field)
    {
        if (getValue(obj, field) == null)
            return "null";
        return String.valueOf(getValue(obj, field));
    }

    public static String getStringValue(Object obj, String field, boolean declared) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
    {
        Field f = getField(obj.getClass(), field, declared);
        return (String) f.get(obj);
    }

    public static int getIntValue(Object obj, Field field)
    {
        if (getValue(obj, field) == null)
            return 0;
        return Integer.valueOf(String.valueOf(getValue(obj, field)));
    }

    @SuppressWarnings("unchecked")
    public static <U> U getValueWithType(Object obj, Field field, Class<U> type)
    {
        if (!field.getType().equals(type))
        {
            if (type.equals(Number.class) || type.equals(Integer.class) || type.equals(int.class) || type.equals(Double.class) || type.equals(double.class) || type.equals(Float.class) || type.equals(float.class) || type.equals(Long.class) || type.equals(long.class) || type.equals(Short.class) || type.equals(short.class))
                return (U) new Integer(0);
            if (type.equals(Boolean.class) || type.equals(boolean.class))
                return (U) Boolean.TRUE;
            if (type.equals(Byte.class) || type.equals(byte.class))
                return (U) new Byte((byte) 0);
            return null;
        }
        Object value = getValue(obj, field);
        if (value == null)
            return null;
        return (U) value;
    }

	/*
     * METHODS
	 */

    public static Method getMethod(Object obj, String name, Class<?>... parameterTypes)
    {
        try
        {
            return obj.getClass().getMethod(name, parameterTypes);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Object obj, String name, boolean declared, Class<?>... parameterTypes)
    {
        return getMethod(obj.getClass(), name, declared, parameterTypes);
    }

    public static Method getMethod(Class<?> classOfMethod, String name, Class<?>... parameterTypes)
    {
        return getMethod(classOfMethod, name, false, parameterTypes);
    }

    public static Method getMethod(Class<?> classOfMethod, String name, boolean declared, Class<?>... parameterTypes)
    {
        try
        {
            return declared ? classOfMethod.getDeclaredMethod(name, parameterTypes) : classOfMethod.getMethod(name, parameterTypes);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeMethod(Object obj, Method method, Object... arguments)
    {
        try
        {
            return method.invoke(obj, arguments);
        }
        catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <U> U invokeMethodWithType(Object obj, Method method, Class<U> type, Object... arguments)
    {
        if (!method.getReturnType().equals(type))
        {
            if (type.equals(Number.class) || type.equals(Integer.class) || type.equals(int.class) || type.equals(Double.class) || type.equals(double.class) || type.equals(Float.class) || type.equals(float.class) || type.equals(Long.class) || type.equals(long.class) || type.equals(Short.class) || type.equals(short.class))
                return (U) new Integer(0);
            if (type.equals(Boolean.class) || type.equals(boolean.class))
                return (U) Boolean.TRUE;
            if (type.equals(Byte.class) || type.equals(byte.class))
                return (U) new Byte((byte) 0);
            return null;
        }
        return (U) invokeMethod(obj, method, arguments);
    }

	/*
     * CONSTRUCTOR
	 */

    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... arguments)
    {
        try
        {
            return clazz.getConstructor(arguments);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
        }
        return clazz.getConstructors()[0];
    }

    public static Object newInstance(Constructor<?> constructor, Object... arguments)
    {
        try
        {
            return constructor.newInstance(arguments);
        }
        catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

	/*
     * UTILS
	 */

    public static Field getFirstFieldOfType(Class<?> clazz, Class<?> type)
    {
        for (Field fields : clazz.getDeclaredFields())
        {
            if (fields.getType().equals(type))
                return getField(clazz, fields.getName(), true);
        }
        return null;
    }

    public static Field getFirstFieldOfType(Class<?> clazz, Class<?> type, boolean declared)
    {
        for (Field fields : clazz.getDeclaredFields())
        {
            if (fields.getType().equals(type))
                return getField(clazz, fields.getName(), declared);
        }
        return null;
    }

    public static Method getFirstMethodOfType(Class<?> clazz, Class<?> type)
    {
        for (Method methods : clazz.getDeclaredMethods())
        {
            if (methods.getReturnType().equals(type) && (methods.getParameterTypes().length == 0))
                return ReflectionAPI.getMethod(clazz, methods.getName());
        }
        return null;
    }

    public static Field getLastFieldOfType(Class<?> clazz, Class<?> type)
    {
        Field field = null;
        for (Field fields : clazz.getDeclaredFields())
        {
            if (fields.getType().equals(type))
                field = ReflectionAPI.getField(clazz, fields.getName(), true);
        }
        return field;
    }

    public static Field getLastFieldOfType(Class<?> clazz, Class<?> type, boolean declared)
    {
        Field field = null;
        for (Field fields : clazz.getDeclaredFields())
        {
            if (fields.getType().equals(type))
                field = ReflectionAPI.getField(clazz, fields.getName(), declared);
        }
        return field;
    }

	/*
     * NMS CLASS
	 */

    /**
     * Gets an Class in Nms' packages.
     *
     * @param name Name of the class.
     *
     * @return The nms' class!
     */
    public static Class<?> getNmsClass(String name)
    {
        try
        {
            return Class.forName("net.minecraft.server." + getServerVersion() + "." + name);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isNmsClassExist(String name)
    {
        return isClassExist("net.minecraft.server." + getServerVersion() + "." + name);
    }

    public static Class<?> getCraftClass(String name, CraftPackage packageDirectory)
    {
        try
        {
            return Class.forName(packageDirectory.getPackageDirectory() + "." + name);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Class<?> getClass(String nameWithPackage)
    {
        try
        {
            return Class.forName(nameWithPackage);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isClassExist(String nameWithPackage)
    {
        try
        {
            Class.forName(nameWithPackage);
            return true;
        }
        catch (ClassNotFoundException e)
        {
            return false;
        }
    }

    public static String checkVersionClass(Class<?> clazz)
    {
        Package clazzPackage = clazz.getPackage();
        if (clazzPackage.getName().startsWith("org.bukkit.craftbukkit."))
        {
            return clazzPackage.getName().substring(23, 30);
        }
        else if (clazzPackage.getName().startsWith("net.minecraft.server."))
        {
            return clazzPackage.getName().substring(21, 28);
        }
        else
        {
            return "Unknown";
        }
    }

    /**
     * Gets the version of your server by the packages
     *
     * @return The server version
     */
    public static String getServerVersion()
    {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);

    }

    public static boolean useNMU()
    {
        return getServerVersion().equals("v1_7_R4");
    }

    public enum CraftPackage
    {

        ORG_BUKKIT("org.bukkit"),
        ORG_BUKKIT_BLOCK("org.bukkit.block"),
        ORG_BUKKIT_BLOCK_BANNER("org.bukkit.block.banner"),
        ORG_BUKKIT_COMMAND("org.bukkit.command"),
        ORG_BUKKIT_COMMAND_DEFAULTS("org.bukkit.command.defaults"),
        ORG_BUKKIT_CONFIGURATION("org.bukkit.configuration"),
        ORG_BUKKIT_CONFIGURATION_FILE("org.bukkit.configuration.file"),
        ORG_BUKKIT_CONFIGURATION_SERIALIZATION("org.bukkit.configuration.serialization"),
        ORG_BUKKIT_CONVERSATIONS("org.bukkit.conversations"),
        ORG_BUKKIT_CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion()),
        ORG_BUKKIT_CRAFTBUKKIT_BLOCK("org.bukkit.craftbukkit." + getServerVersion() + ".block"),
        ORG_BUKKIT_CRAFTBUKKIT_CHUNKIO("org.bukkit.craftbukkit." + getServerVersion() + ".chunkio"),
        ORG_BUKKIT_CRAFTBUKKIT_COMMAND("org.bukkit.craftbukkit." + getServerVersion() + ".command"),
        ORG_BUKKIT_CRAFTBUKKIT_CONVERSATIONS("org.bukkit.craftbukkit." + getServerVersion() + ".conversations"),
        ORG_BUKKIT_CRAFTBUKKIT_ENCHANTMENTS("org.bukkit.craftbukkit." + getServerVersion() + ".enchantments"),
        ORG_BUKKIT_CRAFTBUKKIT_ENTITY("org.bukkit.craftbukkit." + getServerVersion() + ".entity"),
        ORG_BUKKIT_CRAFTBUKKIT_EVENT("org.bukkit.craftbukkit." + getServerVersion() + ".event"),
        ORG_BUKKIT_CRAFTBUKKIT_GENERATOR("org.bukkit.craftbukkit." + getServerVersion() + ".generator"),
        ORG_BUKKIT_CRAFTBUKKIT_HELP("org.bukkit.craftbukkit." + getServerVersion() + ".help"),
        ORG_BUKKIT_CRAFTBUKKIT_INVENTORY("org.bukkit.craftbukkit." + getServerVersion() + ".inventory"),
        ORG_BUKKIT_CRAFTBUKKIT_MAP("org.bukkit.craftbukkit." + getServerVersion() + ".map"),
        ORG_BUKKIT_CRAFTBUKKIT_METADATA("org.bukkit.craftbukkit." + getServerVersion() + ".metadata"),
        ORG_BUKKIT_CRAFTBUKKIT_POTION("org.bukkit.craftbukkit." + getServerVersion() + ".potion"),
        ORG_BUKKIT_CRAFTBUKKIT_PROJECTILES("org.bukkit.craftbukkit." + getServerVersion() + ".projectiles"),
        ORG_BUKKIT_CRAFTBUKKIT_SCHEDULER("org.bukkit.craftbukkit." + getServerVersion() + ".scheduler"),
        ORG_BUKKIT_CRAFTBUKKIT_SCOREBOARD("org.bukkit.craftbukkit." + getServerVersion() + ".scoreboard"),
        ORG_BUKKIT_CRAFTBUKKIT_UTIL("org.bukkit.craftbukkit." + getServerVersion() + ".util"),
        ORG_BUKKIT_CRAFTBUKKIT_UTIL_PERMISIONS("org.bukkit.craftbukkit." + getServerVersion() + ".util.permissions"),
        ORG_BUKKIT_ENCHANTMENTS("org.bukkit.enchantments"),
        ORG_BUKKIT_ENTITY("org.bukkit.entity"),
        ORG_BUKKIT_ENTITY_MINECART("org.bukkit.entity.minecart"),
        ORG_BUKKIT_EVENT("org.bukkit.event"),
        ORG_BUKKIT_EVENT_BLOCK("org.bukkit.event.block"),
        ORG_BUKKIT_EVENT_ENCHANTMENT("org.bukkit.event.enchantment"),
        ORG_BUKKIT_EVENT_ENTITY("org.bukkit.event.entity"),
        ORG_BUKKIT_EVENT_HANGING("org.bukkit.event.hanging"),
        ORG_BUKKIT_EVENT_INVENTORY("org.bukkit.event.inventory"),
        ORG_BUKKIT_EVENT_PAINTING("org.bukkit.event.painting"),
        ORG_BUKKIT_EVENT_PLAYER("org.bukkit.event.player"),
        ORG_BUKKIT_EVENT_SERVER("org.bukkit.event.server"),
        ORG_BUKKIT_EVENT_VEHICLE("org.bukkit.event.vehicle"),
        ORG_BUKKIT_EVENT_WEATHER("org.bukkit.event.weather"),
        ORG_BUKKIT_EVENT_WORLD("org.bukkit.event.world"),
        ORG_BUKKIT_GENERATOR("org.bukkit.generator"),
        ORG_BUKKIT_HELP("org.bukkit.help"),
        ORG_BUKKIT_INVENTORY("org.bukkit.inventory"),
        ORG_BUKKIT_INVENTORY_META("org.bukkit.inventory.meta"),
        ORG_BUKKIT_MAP("org.bukkit.map"),
        ORG_BUKKIT_MATERIAL("org.bukkit.material"),
        ORG_BUKKIT_METADATA("org.bukkit.metadata"),
        ORG_BUKKIT_PERMISSIONS("org.bukkit.permissions"),
        ORG_BUKKIT_PLUGIN("org.bukkit.plugin"),
        ORG_BUKKIT_PLUGIN_JAVA("org.bukkit.plugin.java"),
        ORG_BUKKIT_PLUGIN_MESSAGING("org.bukkit.plugin.messaging"),
        ORG_BUKKIT_POTION("org.bukkit.potion"),
        ORG_BUKKIT_PROJECTILES("org.bukkit.projectiles"),
        ORG_BUKKIT_SCHEDULER("org.bukkit.scheduler"),
        ORG_BUKKIT_SCOREBOARD("org.bukkit.scoreboard"),
        ORG_BUKKIT_UTIL("org.bukkit.util"),
        ORG_BUKKIT_UTIL_IO("org.bukkit.util.io"),
        ORG_BUKKIT_UTIL_NOISE("org.bukkit.util.noise"),
        ORG_BUKKIT_UTIL_PERMISSIONS("org.bukkit.util.permissions");

        private String packageDirectory;

        CraftPackage(String packageDirectory)
        {
            this.packageDirectory = packageDirectory;
        }

        public String getPackageDirectory()
        {
            return this.packageDirectory;
        }

        public Package getPackage()
        {
            return Package.getPackage(packageDirectory);
        }

    }

    public static class NmsClass
    {
        private static final Class<?> iChatBaseComponent = getNmsClass("IChatBaseComponent");;

        public static Class<?> getChatSerializerClass()
        {
            if (isNmsClassExist("IChatBaseComponent$ChatSerializer"))
                return getNmsClass("IChatBaseComponent$ChatSerializer");
            return getNmsClass("ChatSerializer");
        }

        public static Class<?> getEnumChatVisibilityClass()
        {
            if (isNmsClassExist("EntityHuman$EnumChatVisibility"))
                return getNmsClass("EntityHuman$EnumChatVisibility");
            return getNmsClass("EnumChatVisibility");
        }

        public static Class<?> getEnumClientCommandClass()
        {
            if (isNmsClassExist("PacketPlayInClientCommand$EnumClientCommand"))
                return getNmsClass("PacketPlayInClientCommand$EnumClientCommand");
            return getNmsClass("EnumClientCommand");
        }

        public static Class<?> getEnumWorldBorderActionClass()
        {
            if (isNmsClassExist("PacketPlayOutWorldBorder$EnumWorldBorderAction"))
                return getNmsClass("PacketPlayOutWorldBorder$EnumWorldBorderAction");
            return getNmsClass("EnumWorldBorderAction");
        }

        public static Class<?> getIChatBaseComponentClass()
        {
            return iChatBaseComponent;
        }

        public static Class<?> getMinecraftServerClass()
        {
            return getNmsClass("MinecraftServer");
        }

        public static Object getMinecraftServerObject()
        {
            return getValue(Bukkit.getServer(), getField(Bukkit.getServer().getClass(), "console", true));
        }

        public static Object getEntityPlayerByPlayer(Player player)
        {
            return invokeMethod(player, getMethod(player, "getHandle"));
        }

        public static Object getPlayerConnectionByPlayer(Player player)
        {
            switch (MineAPI.getServerType())
            {
                case CRAFTBUKKIT:
                    return getPlayerConnectionByPlayer_Bukkit(player);
                case GLOWSTONE:
                    return getPlayerConnectionByPlayer_Glowstone(player);
                case GLOWSTONEPLUSPLUS:
                    return getPlayerConnectionByPlayer_Glowstone(player);
                case PAPERSPIGOT:
                    return getPlayerConnectionByPlayer_Bukkit(player);
                case SPIGOT:
                    return getPlayerConnectionByPlayer_Bukkit(player);
                default:
                    throw new UnsupportedOperationException("Not recognized server type & version.");
            }
        }

        private static Object getPlayerConnectionByPlayer_Bukkit(Player player)
        {
            Object nmsPlayer = getEntityPlayerByPlayer(player);
            return getValue(nmsPlayer, getField(nmsPlayer.getClass(), "playerConnection", false));
        }

        private static Object getPlayerConnectionByPlayer_Glowstone(Player player)
        {
            return invokeMethod(player, getMethod(player, "getSession"));
        }

        public static Object getPlayerConnectionByEntityPlayer(Object player)
        {
            if (player.getClass().getSimpleName().equals("EntityPlayer"))
            {
                if (MineAPI.isRainbow())
                {
                    return getValue(player, getField(player.getClass(), "plrConnection", false));
                }
                else
                    return getValue(player, getField(player.getClass(), "playerConnection", false));
            }
            return null;
        }

        public static Class<?> getGameProfileClass()
        {
            if (useNMU())
            {
                return ReflectionAPI.getClass("net.minecraft.util.com.mojang.authlib.GameProfile");
            }
            else
            {
                return ReflectionAPI.getClass("com.mojang.authlib.GameProfile");
            }
        }

        public static Class<?> getPlayerPropertyMapClass()
        {
            if (useNMU())
            {
                return ReflectionAPI.getClass("net.minecraft.util.com.mojang.authlib.properties.PropertyMap");
            }
            else
            {
                return ReflectionAPI.getClass("com.mojang.authlib.properties.PropertyMap");
            }
        }

        public static Class<?> getPlayerPropertyMapSerializerClass()
        {
            if (useNMU())
            {
                return ReflectionAPI.getClass("net.minecraft.util.com.mojang.authlib.properties.PropertyMap$Serializer");
            }
            else
            {
                return ReflectionAPI.getClass("com.mojang.authlib.properties.PropertyMap$Serializer");
            }
        }

        public static void sendPacket(Player player, Object obj)
        {
            switch (MineAPI.getServerType())
            {
                case CRAFTBUKKIT:
                    sendPacket_Bukkit(player, obj);
                    break;
                case GLOWSTONE:
                    sendPacket_Glowstone(player, obj);
                    break;
                case GLOWSTONEPLUSPLUS:
                    sendPacket_Glowstone(player, obj);
                    break;
                case PAPERSPIGOT:
                    sendPacket_Bukkit(player, obj);
                    break;
                case RAINBOW_PROJECT:
                    break;
                case SPIGOT:
                    sendPacket_Bukkit(player, obj);
                    break;
                default:
                    throw new UnsupportedOperationException("Not recognized server type & version.");
            }
        }

        private static void sendPacket_Bukkit(Player player, Object obj)
        {
            Object playerConnection = getPlayerConnectionByPlayer(player);
            invokeMethod(playerConnection, getMethod(playerConnection, "sendPacket", getNmsClass("Packet")), obj);
        }

        private static void sendPacket_Glowstone(Player player, Object obj)
        {
            Object session = getPlayerConnectionByPlayer(player);
            invokeMethod(session, getMethod(session, "send", ReflectionAPI.getClass("com.flowpowered.networking.Message")), obj);
        }
    }

    public static class GsonClass
    {
        private static final JsonParser parser = new JsonParser();
        private static final Object nmuParser = newJsonParser();

        public static Class<?> getJsonArrayClass()
        {
            if (useNMU())
                return ReflectionAPI.getClass("net.minecraft.util.com.google.gson.JsonArray");
            else
                return ReflectionAPI.getClass("com.google.gson.JsonArray");
        }

        public static Class<?> getJsonDeserializationContextClass()
        {
            if (useNMU())
                return ReflectionAPI.getClass("net.minecraft.util.com.google.gson.JsonDeserializationContext");
            else
                return ReflectionAPI.getClass("com.google.gson.JsonDeserializationContext");

        }

        public static Class<?> getJsonElementClass()
        {
            if (useNMU())
                return ReflectionAPI.getClass("net.minecraft.util.com.google.gson.JsonElement");
            else
                return ReflectionAPI.getClass("com.google.gson.JsonElement");
        }

        public static Class<?> getJsonObjectClass()
        {
            if (useNMU())
                return ReflectionAPI.getClass("net.minecraft.util.com.google.gson.JsonObject");
            else
                return ReflectionAPI.getClass("com.google.gson.JsonObject");
        }

        public static Class<?> getJsonParserClass()
        {
            if (useNMU())
                return ReflectionAPI.getClass("net.minecraft.util.com.google.gson.JsonParser");
            else
                return ReflectionAPI.getClass("com.google.gson.JsonParser");
        }

        public static Class<?> getJsonSerializationContextClass()
        {
            if (useNMU())
                return ReflectionAPI.getClass("net.minecraft.util.com.google.gson.JsonSerializationContext");
            else
                return ReflectionAPI.getClass("com.google.gson.JsonSerializationContext");

        }

        public static Object newJsonArray()
        {
            return newInstance(getConstructor(getJsonArrayClass()));
        }

        public static Object newJsonObject()
        {
            return newInstance(getConstructor(getJsonObjectClass()));
        }

        public static Object newJsonParser()
        {
            return newInstance(getConstructor(getJsonParserClass()));
        }

        public static Object convertJsonElementToNMU(JsonElement jsonElement)
        {
            String json = jsonElement.toString();
            return invokeMethod(nmuParser, getMethod(nmuParser.getClass(), "parse", false, String.class), json);
        }

        public static JsonElement convertNMUToJsonElement(Object nmu)
        {
            String json = nmu.toString();
            return parser.parse(json);
        }
    }

    public static class CraftClass
    {

        public static Class<?> getCraftItemStackClass()
        {
            return getCraftClass("CraftItemStack", CraftPackage.ORG_BUKKIT_CRAFTBUKKIT_INVENTORY);
        }

    }

    public static class ItemStackConverter
    {

        public static Object toNms(ItemStack item)
        {
            Method asNmsCopy = getMethod(CraftClass.getCraftItemStackClass(), "asNMSCopy", item.getClass());
            return invokeMethod(null, asNmsCopy, item);
        }

        public static ItemStack fromNms(Object item)
        {
            Method asNmsCopy = getMethod(CraftClass.getCraftItemStackClass(), "asBukkitCopy", getNmsClass("ItemStack"));
            return invokeMethodWithType(null, asNmsCopy, ItemStack.class, item);
        }

    }

    public static class SunUnsafe
    {

        public static final Unsafe unsafe = getUnsafe();

        /**
         * Use the Unsafe instance creator. <br>
         * <b>WARNING:</b> This method don't call any constructors!
         *
         * @param clazz A simple Class.
         *
         * @return A new instance of the Class.
         */
        public static Object newInstance(Class<?> clazz)
        {
            try
            {
                return unsafe.allocateInstance(clazz);
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Gets the Unsafe instance and bypass the security exception. <br>
         * <b>ONLY USE IF YOU ARE A INTERMEDIATE DEVELOPER.</b>
         * @return Unsafe instance bypassing the security exception.
         */
        public static Unsafe getUnsafe() {
            if (unsafe != null)
                return unsafe;
            Field theUnsafe = getField(Unsafe.class, "theUnsafe", true);
            return getValueWithType(null, theUnsafe, Unsafe.class);
        }

    }
}
