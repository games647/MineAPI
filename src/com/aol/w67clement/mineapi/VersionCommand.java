package com.aol.w67clement.mineapi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.enums.MinecraftVersion;

public class VersionCommand implements CommandExecutor {

	private MineAPI mineAPI;

	public VersionCommand(MineAPI api) {
		this.mineAPI = api;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("AdvancedVersion")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("mineapi.cmd.advancedversion")) {
					MineAPI.getNmsManager()
							.getFancyMessage("Version: ")
							.then(ChatColor.GREEN
									+ mineAPI.getServer().getBukkitVersion())
							.addHoverMessage(
									ChatColor.GRAY
											+ "Version: "
											+ ChatColor.GREEN
											+ MinecraftVersion
													.getServerVersion()
											+ "\n"
											+ ChatColor.GRAY
											+ "Protocol: "
											+ ChatColor.GREEN
											+ MinecraftVersion
													.getServerVersion()
													.getProtocolVersion())
							.then("\n" + ChatColor.RESET + "Bukkit/Spigot: "
									+ ChatColor.GREEN
									+ this.mineAPI.getServer().getVersion())
							.send(player);
				} else {
					MineAPI.getNmsManager()
							.getActionBarMessage(
									"§4[Permissions] §cYou don't have permissions!")
							.send(player);
				}
			} else if (sender instanceof ConsoleCommandSender) {
				MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.WHITE
						+ "Version: " + ChatColor.GREEN
						+ this.mineAPI.getServer().getBukkitVersion());
				MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.WHITE
						+ "Bukkit/Spigot: " + ChatColor.GREEN
						+ this.mineAPI.getServer().getVersion());
				MineAPI.console.sendMessage(MineAPI.PREFIX + ChatColor.WHITE
						+ "MC Version: " + ChatColor.GREEN
						+ MinecraftVersion.getServerVersion());
				MineAPI.console.sendMessage(MineAPI.PREFIX
						+ ChatColor.WHITE
						+ "Protocol Version: "
						+ ChatColor.GREEN
						+ MinecraftVersion.getServerVersion()
								.getProtocolVersion());
			}
			return true;
		}
		return false;
	}
}
