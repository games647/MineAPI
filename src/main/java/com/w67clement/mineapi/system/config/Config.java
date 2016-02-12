package com.w67clement.mineapi.system.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.bukkit.plugin.Plugin;

public abstract class Config
{

    protected Plugin plugin;
    private File file;

    public Config(Plugin plugin, File file)
    {
        this.plugin = plugin;
        this.file = file;
    }

    public static File getPluginsFolder()
    {
        return new File("plugins/");
    }

    public abstract void init();

    /**
     * Load the configuration file and save it if doesn't exists.
     */
    public void load()
    {
        if (!this.file.exists())
        {
            this.saveResource(this.file.getName(), true);
        }
    }

    /**
     * Save the configuration into the file.
     */
    public abstract void save();

    /**
     * Gets the file of the configuration.
     *
     * @return Configuration's file.
     */
    public File getFile()
    {
        return this.file;
    }

    /**
     * Same method of {@link #saveResource(String, File, boolean)} but data
     * folder is the data folder of the plugin.
     *
     * @param resourcePath Path of the file in plugin.
     * @param replace      Replace the file if exists.
     *
     * @see #saveResource(String, File, boolean)
     */
    public void saveResource(String resourcePath, boolean replace)
    {
        this.saveResource(resourcePath, this.plugin.getDataFolder(), replace);
    }

    /**
     * Save a resource from the plugin to data folder. <br>
     * More than optimized method that saveResource of JavaPlugin.
     *
     * @param resourcePath Path of the file in plugin.
     * @param dataFolder   Data folder
     * @param replace      Replace the file if exists.
     */
    public void saveResource(String resourcePath, File dataFolder, boolean replace)
    {
        if ((resourcePath == null) || (resourcePath.equals("")))
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        resourcePath = resourcePath.replace('\\', '/');

        InputStream input = this.plugin.getResource(resourcePath);

        if (input == null)
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.file);

        File output = new File(dataFolder, resourcePath);

        int lastIndex = resourcePath.lastIndexOf('/');

        File outputFolder = new File(dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outputFolder.exists())
            outputFolder.mkdirs();

        if ((!output.exists()) || (replace))
        {
            ReadableByteChannel rbc;

            rbc = Channels.newChannel(input);
            FileOutputStream fos;
            try
            {
                fos = new FileOutputStream(output);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

}
