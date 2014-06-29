package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.tools.NameManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.bunnehrealm.utilities.MainClass;

public class NickCommand implements CommandExecutor {
	public MainClass MainClass;

	public NickCommand(MainClass MainClass) {
		this.MainClass = MainClass;
	}

	public NickCommand(NameManager NameManager) {
		this.nm = NameManager;
	}

	public NameManager nm = new NameManager(MainClass);

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {

		if (label.equalsIgnoreCase("nick")) {
			if (!(cs.hasPermission("realmutilities.commands.nick") || !(cs
					.hasPermission("realmutilities.commands.nick.others") || !(cs
					.isOp())))) {
				cs.sendMessage(ChatColor.RED
						+ "You do not have permission to do this.");
			} else {
				if (args.length != 2) {
					cs.sendMessage("Correct usage is /nick playername displayname");
				} else {
					if (args[1].length() > 15) {
						cs.sendMessage(ChatColor.RED
								+ "The name cannot be larger than 15 characters!");
						return false;
					} else {
						if (!(cs instanceof Player)) {
							MainClass.loadPlayers();
							Player p = Bukkit.getPlayer(args[0]);
							nm.setDisplayName(p, args[1]);
							nm.setTabName(p, args[1]);
							MainClass.savePlayers();
						} else {
							Player p = Bukkit.getPlayer(args[0]);
							if (((args[0].equalsIgnoreCase(p.getName()) || args[0].equalsIgnoreCase(p.getName())) && cs
									.hasPermission("realmutilities.commands.nick"))
									|| (args[0].equalsIgnoreCase(p.getName()) && cs
											.isOp())) {
								MainClass.loadPlayers();
								nm.setDisplayName(p, args[1]);
								nm.setTabName(p, args[1]);
								MainClass.savePlayers();
								cs.sendMessage(ChatColor.GOLD + "The player: " + ChatColor.RESET + Bukkit.getPlayer(args[0]).getName() + ChatColor.GOLD + " has been renamed to:");
								cs.sendMessage(p.getDisplayName());
							}
							else if(cs.hasPermission("realmutilities.commands.nick.others") || cs.isOp()){
								MainClass.loadPlayers();
								nm.setDisplayName(p, args[1]);
								nm.setTabName(p, args[1]);
								MainClass.savePlayers();
								cs.sendMessage(ChatColor.GOLD + "The player: " + ChatColor.RESET + Bukkit.getPlayer(args[0]).getName() + ChatColor.GOLD + " has been renamed to:");
								cs.sendMessage(p.getDisplayName());
							}
						}
					}
				}
			}
		}
		return false;
	}

}
