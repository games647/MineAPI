package com.aol.w67clement.mineapi;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.system.modules.Module;

public class MineAPICommand implements TabExecutor {

	private MineAPI plugin;

	public MineAPICommand(MineAPI plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String label, String[] args) {
		List<String> suggests = new ArrayList<String>();
		if (label.equalsIgnoreCase("MineAPI")) {
			if (args.length == 1) {
				suggests.add("modules");
				suggests.add("version");
				suggests.add("module");
				suggests.add("help");
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("module")) {
					for (Module module : MineAPI.getModuleManager()
							.getModules()) {
						suggests.add(module.getName());
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("MineAPI")) {
			if (sender.hasPermission("mineapi.cmd.mineapi")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.DARK_AQUA + "MineAPI "
							+ ChatColor.GRAY + "v" + ChatColor.GREEN
							+ this.plugin.getDescription().getVersion());
					if (sender instanceof Player) {
						Player player = (Player) sender;
						MineAPI.getNmsManager()
								.getFancyMessage("Author: ")
								.color(ChatColor.GRAY)
								.then("67clement")
								.color(ChatColor.RED)
								.addHoverMessage(
										ChatColor.GRAY
												+ "Website: "
												+ ChatColor.GREEN
												+ "https://67clement.github.io/"
												+ "\n"
												+ ChatColor.GRAY
												+ "My others plugins: "
												+ "\n"
												+ ChatColor.GRAY
												+ " - "
												+ ChatColor.DARK_AQUA
												+ "Advanced"
												+ ChatColor.GREEN
												+ "Sign"
												+ ChatColor.DARK_GREEN
												+ "Edit"
												+ "\n"
												+ ChatColor.GRAY
												+ " - "
												+ ChatColor.DARK_AQUA
												+ "Advanced"
												+ ChatColor.DARK_GREEN
												+ "Motd"
												+ "\n"
												+ ChatColor.GRAY
												+ "And more..."
												+ "\n"
												+ ChatColor.GRAY
												+ "You can visit my github: https://github.com/67clement/")
								.send(player);
						MineAPI.getNmsManager()
								.getFancyMessage(
										"Click to open the MineAPI website.")
								.color(ChatColor.GRAY)
								.addLink(
										"https://67clement.github.io/plugins/mineapi/")
								.send(player);
						MineAPI.getNmsManager().getFancyMessage("Please tap: ")
								.color(ChatColor.GRAY).then("/MineAPI Help")
								.color(ChatColor.RED)
								.runCommand("/MineAPI Help")
								.then(" to view the help menu.")
								.color(ChatColor.GRAY).send(player);
					} else {
						sender.sendMessage(ChatColor.GRAY + "Author: "
								+ ChatColor.RED + "67clement");
						sender.sendMessage(ChatColor.GRAY
								+ "Website: "
								+ ChatColor.RED
								+ "https://67clement.github.io/plugins/mineapi/");
						sender.sendMessage(ChatColor.GRAY + "Please tap: "
								+ ChatColor.RED + "/MineAPI Help"
								+ ChatColor.GRAY + " to view the help menu.");
					}
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("help")) {
						// Help menu
						this.printHelp(sender);
					} else if (args[0].equalsIgnoreCase("modules")) {
						// Module list
						this.onExecuteModulesCommand(sender);
					} else if (args[0].equalsIgnoreCase("module")) {
						// Module usage
						sender.sendMessage(ChatColor.DARK_RED + "[Usage] "
								+ ChatColor.RED + "/MineAPI module <Module>");
					} else if (args[0].equalsIgnoreCase("version")) {
						// Version
						sender.sendMessage(MineAPI.PREFIX + ChatColor.GRAY
								+ "Version: " + ChatColor.RED
								+ this.plugin.getDescription().getVersion());
					} else {
						// ERROR
						sender.sendMessage(ChatColor.DARK_RED
								+ "[Usage] "
								+ ChatColor.RED
								+ "Please use /MineAPI help to view the sub command list.");
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("module")) {
						this.getModuleInformation(sender, args[1]);
					} else {
						sender.sendMessage(ChatColor.DARK_RED
								+ "[Usage] "
								+ ChatColor.RED
								+ "Please use /MineAPI help to view the sub command list.");
					}
				} else {
					sender.sendMessage(ChatColor.DARK_RED
							+ "[Usage] "
							+ ChatColor.RED
							+ "Please use /MineAPI help to view the sub command list.");
				}
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "[Permissions]"
						+ ChatColor.RED + " You don't have permissions.");
			}
			return true;
		}
		return false;
	}

	private void printHelp(CommandSender sender) {
		// Header
		sender.sendMessage(ChatColor.DARK_AQUA + "\u2726" + ChatColor.AQUA
				+ " >================[ " + ChatColor.DARK_AQUA + "MineAPI Help"
				+ ChatColor.AQUA + " ]================< " + ChatColor.DARK_AQUA
				+ "\u2726");
		// Body
		if (sender instanceof Player) {
			Player player = (Player) sender;
			MineAPI.getNmsManager().getFancyMessage("/MineAPI Help")
					.color(ChatColor.RED).suggestCommand("/MineAPI help")
					.then(" - Shows the help menu.").color(ChatColor.GRAY)
					.send(player);
			MineAPI.getNmsManager().getFancyMessage("/MineAPI modules")
					.color(ChatColor.RED).suggestCommand("/MineAPI modules")
					.then(" - Gets a list of running modules.")
					.color(ChatColor.GRAY).send(player);
			MineAPI.getNmsManager().getFancyMessage("/MineAPI module <Module>")
					.color(ChatColor.RED).suggestCommand("/MineAPI module ")
					.then(" - Gets informations of the module.")
					.color(ChatColor.GRAY).send(player);
			MineAPI.getNmsManager().getFancyMessage("/MineAPI version")
					.color(ChatColor.RED).suggestCommand("/MineAPI version")
					.then(" - Gets version of MineAPI.").color(ChatColor.GRAY)
					.send(player);
		} else {
			sender.sendMessage(ChatColor.RED + "/MineAPI Help" + ChatColor.GRAY
					+ " - Shows the help menu.");
			sender.sendMessage(ChatColor.RED + "/MineAPI modules"
					+ ChatColor.GRAY + " - Gets a list of running modules.");
			sender.sendMessage(ChatColor.RED + "/MineAPI module <Module>"
					+ ChatColor.GRAY + " - Gets informations of the module.");
			sender.sendMessage(ChatColor.RED + "/MineAPI version"
					+ ChatColor.GRAY + " - Gets version of MineAPI.");
		}
		// Footer
		sender.sendMessage(ChatColor.DARK_AQUA + "\u2726" + ChatColor.AQUA
				+ " >================[ " + ChatColor.DARK_AQUA + "MineAPI Help"
				+ ChatColor.AQUA + " ]================< " + ChatColor.DARK_AQUA
				+ "\u2726");
	}

	private void onExecuteModulesCommand(CommandSender sender) {
		String modules = "";
		Module module = null;
		List<Module> moduleList = MineAPI.getModuleManager().getModules();
		for (int i = 0; i < moduleList.size(); i++) {
			module = moduleList.get(i);
			String value = "";
			if (module.isEnabled()) {
				value = ChatColor.GREEN + module.getName();
			} else {
				value = ChatColor.RED + module.getName();
			}

			if (i + 1 == moduleList.size()) {
				modules = modules + value;
			} else {
				modules = modules + value + ChatColor.RESET + ", ";
			}
		}

		sender.sendMessage("MineAPI modules (" + moduleList.size() + "): "
				+ modules);
	}

	private void getModuleInformation(CommandSender sender, String moduleName) {
		if (MineAPI.getModuleManager().getModule(moduleName) != null) {
			Module module = MineAPI.getModuleManager().getModule(moduleName);
			if (module.isEnabled()) {
				sender.sendMessage("Informations of the module: "
						+ ChatColor.GREEN + module.getName());
			} else {
				sender.sendMessage("Informations of the module: "
						+ ChatColor.RED + module.getName());
			}
			sender.sendMessage("Version: " + ChatColor.GREEN
					+ module.getModuleInformations().getVersion());
			sender.sendMessage("Description: " + ChatColor.GREEN
					+ module.getModuleInformations().getDescription());
			sender.sendMessage("Author(s): " + ChatColor.GREEN
					+ module.getModuleInformations().getAuthorsInLine());
			if (sender instanceof Player) {
				MineAPI.getNmsManager()
						.getFancyMessage("Website: ")
						.then("Website of the module")
						.color(ChatColor.GREEN)
						.addHoverMessage(
								ChatColor.GRAY
										+ "Please click on this text to open the link.")
						.addLink(module.getModuleInformations().getWebSite())
						.send((Player) sender);
			} else {
				sender.sendMessage("Website: " + ChatColor.GREEN
						+ module.getModuleInformations().getWebSite());
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "[Error]" + ChatColor.RED
					+ " The module '" + moduleName + "' was not found!");
		}
	}
}
