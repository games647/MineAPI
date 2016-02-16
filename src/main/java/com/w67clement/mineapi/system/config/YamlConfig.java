package com.w67clement.mineapi.system.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.w67clement.mineapi.api.ReflectionAPI;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class YamlConfig extends Config
{

    protected FileConfiguration configuration;
    protected JsonParser jsonParser;

    public YamlConfig(Plugin plugin, File file)
    {
        super(plugin, file);
        this.jsonParser = new JsonParser();
    }

    @Override
    public void load()
    {
        super.load();
        this.configuration = YamlConfiguration.loadConfiguration(this.getFile());
    }

    @Override
    public void save()
    {
        try
        {
            this.configuration.save(this.getFile());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets the requested Location by path. <br></br>
     * If the Location does not exist but a default value has been specified, this will return the default value.
     * If the Location does not exist and no default value was specified, this will return null.
     *
     * @param path Path of the Location to get.
     *
     * @return Requested Location.
     */
    public Location getLocation(String path)
    {
        Object def = ReflectionAPI.invokeMethod(this.configuration, ReflectionAPI.getMethod(this.configuration, "getDefault", true, String.class), path);
        return getLocation(path, (def instanceof Location) ? (Location) def : null);
    }

    /**
     * Gets the requested Location by path, returning a default value if not found.
     * <p>If the Location does not exist then the specified default value will returned regardless of if a default has been identified in the root Configuration.</p>
     *
     * @param path         Path of the Location to get.
     * @param defaultValue The default value to return if the path is not found or is not a Location.
     *
     * @return Requested Location.
     */
    public Location getLocation(String path, Location defaultValue)
    {
        String json = this.configuration.getString(path);
        if (json == null)
            return defaultValue;
        try
        {
            JsonElement jsonElement = this.jsonParser.parse(json);
            if (jsonElement instanceof JsonObject)
            {
                JsonObject jsonObject = (JsonObject) jsonElement;
                World world = Bukkit.getWorld(jsonObject.get("world").getAsString());
                if (world == null && defaultValue != null)
                    world = defaultValue.getWorld();
                double x = jsonObject.get("x").getAsDouble();
                double y = jsonObject.get("y").getAsDouble();
                double z = jsonObject.get("z").getAsDouble();
                if (jsonObject.has("yaw") && jsonObject.has("pitch"))
                {
                    return new Location(world, x, y, z, jsonObject.get("yaw").getAsFloat(), jsonObject.get("pitch").getAsFloat());
                }
                else
                    return new Location(world, x, y, z);
            }
        }
        catch (Throwable e)
        {
            return defaultValue;
        }
        return defaultValue;
    }

    public void setLocation(String path, Location location)
    {
        JsonObject json = new JsonObject();
        if (location.getWorld() != null)
            json.addProperty("world", location.getWorld().getName());
        else
            json.addProperty("world", "world");
        json.addProperty("x", location.getX());
        json.addProperty("y", location.getY());
        json.addProperty("z", location.getZ());
        json.addProperty("yaw", location.getYaw());
        json.addProperty("pitch", location.getPitch());
        this.configuration.set(path, json.toString());
    }

}
