package com.w67clement.mineapi.system.config;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public abstract class YamlConfig extends Config
{

    protected FileConfiguration configuration;

    public YamlConfig(Plugin plugin, File file)
    {
        super(plugin, file);
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

}
