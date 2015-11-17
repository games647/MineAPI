package com.w67clement.mineapi.system.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.yaml.snakeyaml.error.YAMLException;

import com.w67clement.mineapi.MineAPI;

public class ModuleLoader {

	private HashMap<String, Module> modules = new HashMap<String, Module>();

	private ClassLoader loader;

	public ModuleLoader(ClassLoader loader) {
		this.loader = loader;
	}

	public Module loadModule(File file) throws InvalidModuleException {
		if (!file.exists()) {
			throw new InvalidModuleException(new FileNotFoundException(
					file.getPath() + " does not exist"));
		}

		ModuleInformations description = null;
		try {
			description = getDescription(file);
			MineAPI.console
					.sendMessage(MineAPI.PREFIX + ChatColor.GREEN + "Loading "
							+ ChatColor.DARK_GREEN + description.getName());
		} catch (InvalidDescriptionException e) {
			throw new InvalidModuleException(e);
		}

		try {
			Method method = URLClassLoader.class.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(loader, file.toURI().toURL());
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | MalformedURLException e1) {
			e1.printStackTrace();
		}

		try {
			Class<?> moduleClass = this.loader.loadClass(description.getMain());
			Constructor<?> moduleConstructor = moduleClass
					.getConstructor(new Class<?>[] {});
			Module obj = (Module) moduleConstructor
					.newInstance(new Object[] {});
			obj.setModuleInformations(description);
			modules.put(description.getName(), obj);
			return obj;
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new InvalidModuleException(e);
		}
	}

	public void enableModule(Module module) {
		MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.GREEN
				+ "Enabling " + ChatColor.DARK_GREEN + module.getName());
		module.onEnable();
		module.setEnabled(true);
	}

	public void disableModule(Module module) {
		MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.GREEN
				+ "Disabling " + ChatColor.DARK_GREEN + module.getName());
		module.onDisable();
		module.setEnabled(false);
	}

	public ModuleInformations getDescription(File file)
			throws InvalidDescriptionException {
		Validate.notNull(file, "File cannot be null");

		JarFile jar = null;
		InputStream stream = null;

		try {
			jar = new JarFile(file);
			JarEntry entry = jar.getJarEntry("module.yml");

			if (entry == null) {
				throw new InvalidDescriptionException(
						new FileNotFoundException(
								"Jar does not contain module.yml"));
			}

			stream = jar.getInputStream(entry);

			return new ModuleInformations(stream);
		} catch (IOException ex) {
			throw new InvalidDescriptionException(ex);
		} catch (YAMLException ex) {
			throw new InvalidDescriptionException(ex);
		} finally {
			if (jar != null) {
				try {
					jar.close();
				} catch (IOException e) {
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public HashMap<String, Module> getModules() {
		return modules;
	}
}
