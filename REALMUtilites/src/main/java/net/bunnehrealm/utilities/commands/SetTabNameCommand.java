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
import net.bunnehrealm.utilities.managers.NameManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTabNameCommand implements CommandExecutor {
	RealmUtilities plugin = RealmUtilities.plugin;

	public SetTabNameCommand(RealmUtilities instance) {
		this.plugin = instance;
	}

	public NameManager nm = new NameManager(plugin);

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {

		if (label.equalsIgnoreCase("settabname")) {
			if (!(cs.hasPermission("realmutilities.commands.settabname") || cs
					.hasPermission("realmutilities.commands.settabname.others"))
					&& !(cs.isOp())) {
				cs.sendMessage(ChatColor.RED
						+ "You do not have permission to use this command.");
			} else {
				if (!(cs instanceof Player)) {
					if (args.length != 2) {
						cs.sendMessage(ChatColor.RED
								+ "Correct usage: /settabname <player> <displayname>");
					} else {
						Player target = Bukkit.getPlayer(args[0]);
						nm.setTabName(target, args[1]);
						cs.sendMessage(ChatColor.GOLD + "The player: "
								+ ChatColor.RESET + target.getName()
								+ ChatColor.GOLD
								+ "'s tabname has been changed to:");
						cs.sendMessage(ChatColor.translateAlternateColorCodes(
								'&',
								plugin.players.getString(target.getUniqueId()
										.toString() + ".Tab-Name")));
					}

				} else {
					if (args.length != 2) {
						cs.sendMessage(ChatColor.RED
								+ "Correct usage: /settabname <player> <tabname>");
					} else {
						Player p = (Player) cs;
						if ((args[0].equalsIgnoreCase(p.getName()) && (p
								.hasPermission("realmutilities.commands.setdisplayname") || p
								.isOp()))) {
							Player target = Bukkit.getPlayer(args[0]);

							nm.setTabName(p, args[1]);
							cs.sendMessage(ChatColor.GOLD + "The player: "
									+ ChatColor.RESET + target.getName()
									+ ChatColor.GOLD
									+ "'s tabname has been changed to:");
							cs.sendMessage(ChatColor
									.translateAlternateColorCodes('&',
											plugin.players.getString(target
													.getUniqueId().toString()
													+ ".Tab-Name")));
						} else if (p
								.hasPermission("realmutilities.commands.setdisplayname.others")
								|| p.isOp()) {
							Player target = Bukkit.getPlayer(args[0]);
							if (!(target.isOnline())) {
								p.sendMessage(ChatColor.RED
										+ "That player is offline!");
							} else {
								nm.setDisplayName(target, args[1]);
								cs.sendMessage(ChatColor.GOLD + "The player: "
										+ ChatColor.RESET + target.getName()
										+ ChatColor.GOLD
										+ "'s tabname has been changed to:");
								cs.sendMessage(ChatColor
										.translateAlternateColorCodes('&',
												plugin.players.getString(target
														.getUniqueId()
														.toString()
														+ ".Tab-Name")));
							}
						} else {
							p.sendMessage(ChatColor.RED
									+ "You do not have permission you use this command.");
						}
					}
				}
			}
		}
		return false;
	}
}
