package com.w67clement.mineapi.system;

import com.w67clement.mineapi.system.modules.InvalidModuleException;
import com.w67clement.mineapi.system.modules.Module;
import com.w67clement.mineapi.system.modules.ModuleLoader;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager
{

    private ModuleLoader loader;

    public ModuleManager(ModuleLoader loader)
    {
        this.loader = loader;
    }

    /**
     * Gets an MineAPI's module.
     *
     * @param name Name of the module.
     *
     * @return Object of the module.
     */
    public Module getModule(String name)
    {
        return this.loader.getModules().get(name);
    }

    /**
     * Check is module enabled by name.
     *
     * @param name Name of the module.
     *
     * @return Module Enable value.
     */
    public boolean isModuleEnabled(String name)
    {
        return this.isModuleEnabled(getModule(name));
    }

    /**
     * Check is module enabled by module Object.
     *
     * @param module Object of the module.
     *
     * @return Module enable value.
     */
    public boolean isModuleEnabled(Module module)
    {
        return module != null && module.isEnabled();
    }

    public Module loadModule(File file)
    {
        try
        {
            return this.loader.loadModule(file);
        }
        catch (InvalidModuleException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void enableModule(Module module)
    {
        this.loader.enableModule(module);
    }

    public void enableModules()
    {
        this.getModules().stream().filter(module -> !this.isModuleEnabled(module)).forEach(this::enableModule);
    }

    public void disableModule(Module module)
    {
        this.loader.disableModule(module);
    }

    public void disableModules()
    {
        this.getModules().stream().filter(this::isModuleEnabled).forEach(this::disableModule);
    }

    /**
     * Lists the modules.
     *
     * @return All loaded modules.
     */
    public List<Module> getModules()
    {
        return this.loader.getModules().keySet().stream().map(this::getModule).collect(Collectors.toList());
    }
}
