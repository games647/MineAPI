package com.aol.w67clement.mineapi;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.aol.w67clement.mineapi.utils.MineAPIUtils;

public class MineAPI_AutoUpdater {

	private File latestVersionFile;
	private MineAPI mineapi;

	public MineAPI_AutoUpdater(boolean allowUpdate, MineAPI mineapi) {
		this.mineapi = mineapi;
		File file = new File(this.mineapi.getDataFolder(),
				"temp/MineAPI_LatestVersion.txt");
		file.mkdirs();
		MineAPIUtils
				.download(
						"https://67clement.github.io/downloads/MineAPI_LatestVersion.txt",
						file);
		this.latestVersionFile = file;
	}

	public boolean haveNewUpdate() {
		boolean newUpdate = false;
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(this.latestVersionFile);
		if (config.contains("LatestVersion")) {
			if (!this.mineapi.getDescription().getVersion()
					.equals(getLatestVersion())) {
				newUpdate = true;
			}
		}
		return newUpdate;
	}

	public String getLatestVersion() {
		String version = mineapi.getDescription().getVersion();
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(this.latestVersionFile);
		if (config.contains("LatestVersion")) {

			return String.valueOf(config.get("LatestVersion", version));
		}
		return version;
	}

	public String getLatestLink() {
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(this.latestVersionFile);
		String webSite = null;
		if (config.contains("Download")) {

			webSite = config.getString("Download",
					"https://67clement.github.io/downloads/MineAPI/MineAPI-"
							+ getLatestVersion() + ".jar");
		} else {
			webSite = "https://67clement.github.io/downloads/MineAPI/MineAPI-"
					+ getLatestVersion() + ".jar";
		}
		return webSite;
	}
}
