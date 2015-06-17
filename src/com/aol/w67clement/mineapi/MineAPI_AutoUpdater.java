package com.aol.w67clement.mineapi;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.aol.w67clement.mineapi.utils.MineAPIUtils;

public class MineAPI_AutoUpdater {

	private File lastestVersionFile;
	private MineAPI mineapi;

	public MineAPI_AutoUpdater(boolean allowUpdate, MineAPI mineapi) {
		this.mineapi = mineapi;
		File file = new File(this.mineapi.getDataFolder(),
				"temp/MineAPI_LatestVersion.txt");
		file.mkdirs();
		MineAPIUtils
				.download(
						"https://67clement.github.io/downloads/MineAPI_LastestVersion.txt",
						file);
		this.lastestVersionFile = file;
	}

	public boolean haveNewUpdate() {
		boolean newUpdate = false;
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(this.lastestVersionFile);
		if (config.contains("LastestVersion")) {
			if (!this.mineapi.getDescription().getVersion()
					.equals(getLastestVersion())) {
				newUpdate = true;
			}
		}
		return newUpdate;
	}

	public String getLastestVersion() {
		String version = mineapi.getDescription().getVersion();
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(this.lastestVersionFile);
		if (config.contains("LastestVersion")) {

			return String.valueOf(config.get("LatestVersion", version));
		}
		return version;
	}

	public String getLatestLink() {
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(this.lastestVersionFile);
		String webSite = null;
		if (config.contains("Download")) {

			webSite = config.getString("Download",
					"https://67clement.github.io/downloads/MineAPI/MineAPI-"
							+ getLastestVersion() + ".jar");
		} else {
			webSite = "https://67clement.github.io/downloads/MineAPI/MineAPI-"
					+ getLastestVersion() + ".jar";
		}
		return webSite;
	}
}
