package com.aol.w67clement.mineapi.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.ChatColor;

import com.aol.w67clement.mineapi.MineAPI;

public class MineAPIUtils {

	private static final String MINEAPIUTILS_PREFIX = ChatColor.GRAY + "["
			+ ChatColor.DARK_AQUA + "MineAPI" + ChatColor.DARK_GREEN + "Utils"
			+ ChatColor.GRAY + "]" + ChatColor.RESET + " ";

	public static final boolean isOnline(final String... urls)
			throws MalformedURLException {
		for (final String url : urls) {
			if (isOnline(new URL(url))) {
				return true;
			}
		}
		return false;
	}

	public static final boolean isOnline(final URL url) {

		try {
			url.openConnection().getContent();
			return true;
		} catch (IOException e) {
			// e.printStackTrace();
		}

		return false;
	}

	public static boolean download(final String webSite, final File dest) {
		try {
			MineAPI.console.sendMessage(MINEAPIUTILS_PREFIX + ChatColor.GREEN
					+ "Starting download from " + webSite + "...");
			if (!isOnline(webSite)) {
				MineAPI.console.sendMessage(MINEAPIUTILS_PREFIX
						+ ChatColor.DARK_RED + "[Error]" + ChatColor.RED
						+ " Can't download from: " + webSite
						+ " Reason: NoConnection");
				return false;
			}
			final URLConnection connection = new URL(webSite).openConnection();
			connection.addRequestProperty("User-Agent", "MineAPI");
			final BufferedInputStream in = new BufferedInputStream(
					connection.getInputStream());
			final FileOutputStream out = new FileOutputStream(dest);
			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				out.write(data, 0, count);
			}
			out.close();
			in.close();
			MineAPI.console.sendMessage(MINEAPIUTILS_PREFIX + ChatColor.GREEN
					+ "The download from " + webSite + " has finished success");
			return true;
		} catch (final Exception ex) {
			if (dest.exists()) {
				dest.delete();
			}
			ex.printStackTrace();
		}
		return false;
	}
}
