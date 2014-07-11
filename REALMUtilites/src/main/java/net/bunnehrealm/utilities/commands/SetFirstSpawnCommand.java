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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetFirstSpawnCommand implements CommandExecutor {
	RealmUtilities plugin = RealmUtilities.plugin;

	public SetFirstSpawnCommand(RealmUtilities instance) {
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
