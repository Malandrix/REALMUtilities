/*REALMUtilities is used for adding a great amount of features to your bukkit server.
 Copyright (C) 2013  Rory Finnegan
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.RealmUtilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SMessageCmd implements CommandExecutor {
	RealmUtilities plugin = RealmUtilities.plugin;

	public SMessageCmd(RealmUtilities mainClass) {
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
