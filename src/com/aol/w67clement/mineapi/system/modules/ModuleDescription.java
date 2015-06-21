package com.aol.w67clement.mineapi.system.modules;

import java.io.InputStream;
import java.util.Map;

import org.bukkit.plugin.InvalidDescriptionException;
import org.yaml.snakeyaml.Yaml;

public class ModuleDescription {

	private String name;
	private String main;
	private String description;
	private String version;
	private String authors;
	private String website;

	public ModuleDescription(InputStream stream)
			throws InvalidDescriptionException {
		loadMap(asMap(new Yaml().load(stream)));
	}

	public String getName() {
		return this.name;
	}

	public String getMain() {
		return this.main;
	}

	public String getDescription() {
		return this.description;
	}

	public String getVersion() {
		return this.version;
	}

	public String getAuthorsInLine() {
		return this.authors;
	}

	public String[] getAuthors() {
		return this.authors.split(",");
	}

	public String getWebSite() {
		return this.website;
	}

	private void loadMap(Map<?, ?> map) throws InvalidDescriptionException {
		try {
			this.name = map.get("name").toString();

			if (!this.name.matches("^[A-Za-z0-9 _.-]+$")) {
				throw new InvalidDescriptionException("name '" + this.name
						+ "' contains invalid characters.");
			}
			this.name = this.name.replace(' ', '_');
		} catch (NullPointerException ex) {
			throw new InvalidDescriptionException(ex, "name is not defined");
		} catch (ClassCastException ex) {
			throw new InvalidDescriptionException(ex, "name is of wrong type");
		}
		try {
			this.version = map.get("version").toString();
		} catch (NullPointerException ex) {
			throw new InvalidDescriptionException(ex, "version is not defined");
		} catch (ClassCastException ex) {
			throw new InvalidDescriptionException(ex,
					"version is of wrong type");
		}
		try {
			this.main = map.get("main").toString();
			if (this.main.startsWith("org.bukkit.")
					&& this.main.startsWith("org.spigotmc.")) {
				throw new InvalidDescriptionException(
						"main may not be within the org.bukkit namespace");
			}
		} catch (NullPointerException ex) {
			throw new InvalidDescriptionException(ex, "main is not defined");
		} catch (ClassCastException ex) {
			throw new InvalidDescriptionException(ex, "main is of wrong type");
		}

		if (map.get("description") != null) {
			this.description = map.get("description").toString();
		} else {
			this.description = "A simple module.";
		}

		if (map.get("website") != null) {
			this.website = map.get("website").toString();
		}

		if (map.get("authors") != null) {
			this.authors = map.get("authors").toString();
		}
	}

	@SuppressWarnings("rawtypes")
	private Map<?, ?> asMap(Object object) throws InvalidDescriptionException {
		if ((object instanceof Map)) {
			return (Map) object;
		}
		throw new InvalidDescriptionException(object
				+ " is not properly structured.");
	}
}
