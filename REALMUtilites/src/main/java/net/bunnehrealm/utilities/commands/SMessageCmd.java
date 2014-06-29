package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SMessageCmd implements CommandExecutor {
	MainClass plugin = MainClass.plugin;

	public SMessageCmd(MainClass mainClass) {
		this.plugin = mainClass;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {
		if (label.equalsIgnoreCase("smessage")) {
			if (!(cs instanceof Player)) {
				if (args[0].equalsIgnoreCase("all")) {
					StringBuilder sb = new StringBuilder();
					for (int x = 1; x < args.length; x++) {
						sb.append(" ").append(args[x]);
					}

					String msg = sb.toString();
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.sendMessage(ChatColor
								.translateAlternateColorCodes('&',
										msg.substring(1)));
						return false;
					}
				}
				Player target = Bukkit.getPlayer(args[0]);
				if (!(target.isOnline())) {
					cs.sendMessage("That player is not online.");
					return false;
				} else {
					StringBuilder sb = new StringBuilder();
					for (int x = 1; x < args.length; x++) {
						sb.append(" ").append(args[x]);
					}

					String msg = sb.toString();
					target.sendMessage(ChatColor.translateAlternateColorCodes(
							'&', msg.substring(1)));
				}
			} else {
				Player p = (Player) cs;
				if (p.hasPermission("realmutilities.commands.smessage")
						|| p.isOp()) {
					if (args[0].equalsIgnoreCase("all")) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							StringBuilder sb = new StringBuilder();
							for (int x = 1; x < args.length; x++) {
								sb.append(" ").append(args[x]);
							}

							String msg = sb.toString();
							player.sendMessage(ChatColor
									.translateAlternateColorCodes('&',
											msg.substring(1)));
						}
					} else {
						Player target = Bukkit.getPlayer(args[0]);
						if (!(target.isOnline())) {
							cs.sendMessage("That player is not online.");
							return false;
						} else {
							StringBuilder sb = new StringBuilder();
							for (int x = 1; x < args.length; x++) {
								sb.append(" ").append(args[x]);
							}

							String msg = sb.toString();
							target.sendMessage(ChatColor
									.translateAlternateColorCodes('&',
											msg.substring(1)));
						}
					}
				} else {
					p.sendMessage(ChatColor.RED
							+ "You do not have permission to use that command!");
				}
			}
		}
		return false;
	}

}
