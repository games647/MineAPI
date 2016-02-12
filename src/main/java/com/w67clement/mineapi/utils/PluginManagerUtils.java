package com.w67clement.mineapi.utils;

import com.w67clement.mineapi.api.ReflectionAPI;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * An plugin manager utils class
 *
 * @author w67clement
 */
public class PluginManagerUtils
{

    /**
     * Gets the jar file of an plugin.
     *
     * @param plugin Plugin
     *
     * @return The plugin's JarFile.
     */
    public static File getPluginFile(Plugin plugin)
    {
        File file;

        file = (File) ReflectionAPI.getValue(plugin, ReflectionAPI.getField(JavaPlugin.class, "file", true));

        return file;
    }

    public static File findPluginFile(String pluginName)
    {
        File file = null;
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (getPluginFile(plugin) == null)
        {
            // Plugins folder
            File pluginsFolder = new File("plugins/");
            // Verify all files
            for (File pluginFile : pluginsFolder.listFiles())
            {
                if (pluginFile.isFile())
                {
                    if (pluginFile.getName().endsWith(".jar"))
                    {
                        // Verify plugin.yml
                        try
                        {
                            FileConfiguration config = getPluginDescription(pluginFile);
                            if (config.getString("name").equals(pluginName))
                            {
                                file = pluginFile;
                            }
                        }
                        catch (InvalidPluginException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        else
        {
            file = getPluginFile(plugin);
        }
        return file;
    }

    public static FileConfiguration getPluginDescription(File file) throws InvalidPluginException
    {
        if (file.exists())
        {
            JarFile jarFile;
            InputStream stream;
            try
            {
                jarFile = new JarFile(file);
                JarEntry entry = jarFile.getJarEntry("plugin.yml");

                if (entry == null)
                {
                    throw new InvalidPluginException();
                }

                stream = jarFile.getInputStream(entry);

                return YamlConfiguration.loadConfiguration(stream);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static boolean unloadPlugin(Plugin plugin)
    {
        PluginManager pm = Bukkit.getPluginManager();
        if (pm == null)
            return false;

        // Plugin name
        String name = plugin.getName();

        List<Plugin> plugins;
        Map<String, Plugin> lookupNames;
        SimpleCommandMap commandMap;
        Map<String, Command> knownCommands;
        Field commandMapField;
        // Gets fields.
        try
        {
            Field pluginsField = Bukkit.getPluginManager().getClass().getDeclaredField("plugins");
            pluginsField.setAccessible(true);
            plugins = (List) pluginsField.get(pm);

            Field lookupNamesField = Bukkit.getPluginManager().getClass().getDeclaredField("lookupNames");
            lookupNamesField.setAccessible(true);
            lookupNames = (Map) lookupNamesField.get(pm);

            commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (SimpleCommandMap) commandMapField.get(pm);

            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
            knownCommands = (Map) knownCommandsField.get(commandMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        // Disable plugin.
        if (plugin.isEnabled())
            pm.disablePlugin(plugin);

        // Starting unloading..

        if ((plugins != null) && (plugins.contains(plugin)))
            plugins.remove(plugin);

        if ((lookupNames != null) && (lookupNames.containsKey(name)))
            lookupNames.remove(name);

        if (commandMap != null)
        {
            HashSet<String> a = knownCommands.entrySet().stream().filter(b -> (b.getValue() instanceof PluginIdentifiableCommand) && (((PluginIdentifiableCommand) b.getValue()).getPlugin() == plugin)).map(b -> b.getKey()).collect(Collectors.toCollection(HashSet::new));

            for (String c : a)
            {
                Command cmd = knownCommands.remove(c);

                if ((cmd != null) && (cmd instanceof PluginIdentifiableCommand) && (((PluginIdentifiableCommand) cmd).getPlugin() == plugin))
                {
                    cmd.unregister(commandMap);
                }
                else if (cmd != null)
                {
                    knownCommands.put(c, cmd);
                }
            }
        }

        ClassLoader pluginClassLoader = plugin.getClass().getClassLoader();

        if (pluginClassLoader instanceof URLClassLoader)
        {
            try
            {
                ((URLClassLoader) pluginClassLoader).close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }

        System.gc();

        return true;
    }

    public static boolean reloadPlugin(Plugin plugin)
    {
        boolean reloadSuccess;
        PluginManager pm = Bukkit.getPluginManager();
        // Disable plugin
        pm.disablePlugin(plugin);
        String pluginName = plugin.getName();
        // Unload plugin
        reloadSuccess = unloadPlugin(plugin);
        // Find the file of the plugin.
        File pluginFile = findPluginFile(pluginName);
        Plugin newPlugin = null;
        if (reloadSuccess)
            try
            {
                // Load plugin
                newPlugin = pm.loadPlugin(pluginFile);
            }
            catch (UnknownDependencyException e)
            {
                System.out.println("[MineAPI] Error on the loading of the plugin: '" + plugin.getName() + "', Reason: " + e.getMessage());
            }
            catch (InvalidPluginException | InvalidDescriptionException e)
            {
                e.printStackTrace();
            }
        if (newPlugin != null)
        {
            // Enable plugin.
            pm.enablePlugin(newPlugin);
            reloadSuccess = true;
        }
        return reloadSuccess;
    }

    public static boolean uninstallPlugin(Plugin plugin)
    {
        boolean uninstall;
        File pluginFile = getPluginFile(plugin);
        uninstall = unloadPlugin(plugin);
        if (uninstall)
            uninstall = pluginFile.delete();
        return uninstall;
    }
}
