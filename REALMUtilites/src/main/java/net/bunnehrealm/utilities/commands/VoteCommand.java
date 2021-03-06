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
import net.bunnehrealm.utilities.managers.GUIManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
	RealmUtilities plugin = RealmUtilities.plugin;

	public VoteCommand(RealmUtilities instance) {
		this.plugin = instance;
	}

	GUIManager gm = new GUIManager(plugin);

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {

		if (label.equalsIgnoreCase("vote")) {
			if (!(cs instanceof Player)) {
				cs.sendMessage("That command is for players only");
			} else {
				Player p = (Player) cs;
				if (p.hasPermission("realmutilities.vote") || p.isOp()) {
					if (!(RealmUtilities.plugin.inventory.getBoolean("started"))) {
						p.sendMessage(ChatColor.RED
								+ "No vote has started yet.");
					} else {
						if (RealmUtilities.plugin.inventory.getStringList("players")
								.contains(p.getUniqueId().toString())) {
							p.sendMessage(ChatColor.RED
									+ "You have already participated in this vote!");
						} else {
							gm.showGUI(p);
						}
					}
					return false;
				} else {
					

				}
			}
		}

		return false;
	}
}
