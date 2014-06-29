package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetFirstSpawnCommand implements CommandExecutor {
	MainClass plugin = MainClass.plugin;

	public SetFirstSpawnCommand(MainClass instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {
		if (label.equalsIgnoreCase("setfirstspawn")) {
			if (!(cs instanceof Player)) {
				cs.sendMessage("This is a player only command!");
			}
			if (!(cs.isOp() || cs
					.hasPermission("realmutilities.locations.spawn.first"))) {
				cs.sendMessage(ChatColor.RED
						+ "You do not have access to this command!");
			} else {
				Player p = (Player) cs;
				if (args.length > 0) {
					p.sendMessage(ChatColor.RED
							+ "This command has no arguments!");
				} else {
					plugin.loadLocations();
					plugin.locations.set("first-spawn.world", p.getWorld()
							.getName().toString());
					plugin.locations.set("first-spawn.x", p.getLocation()
							.getX());
					plugin.locations.set("first-spawn.y", p.getLocation()
							.getY());
					plugin.locations.set("first-spawn.z", p.getLocation()
							.getZ());
					plugin.saveLocations();
				}
			}
			return false;
		}
		return false;

	}
}
