package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LinksCommand implements CommandExecutor {
	MainClass plugin = MainClass.plugin;

	public LinksCommand(MainClass instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {

		if (label.equalsIgnoreCase("links")) {
			if (cs.hasPermission("realmutilities.links") || cs.isOp())
				for (String s : MainClass.plugin.getConfig().getStringList(
						"Links")) {
					cs.sendMessage(ChatColor.translateAlternateColorCodes('&',
							s));
				}
		}

		return false;
	}

}
