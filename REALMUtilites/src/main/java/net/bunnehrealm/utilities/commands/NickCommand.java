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

import net.bunnehrealm.utilities.managers.NameManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.bunnehrealm.utilities.RealmUtilities;

public class NickCommand implements CommandExecutor {
	public RealmUtilities MainClass;

	public NickCommand(RealmUtilities MainClass) {
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
