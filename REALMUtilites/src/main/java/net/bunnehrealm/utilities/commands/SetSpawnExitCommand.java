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

public class SetSpawnExitCommand implements CommandExecutor {
	RealmUtilities plugin = RealmUtilities.plugin;
	public SetSpawnExitCommand(RealmUtilities instance){
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {
		if (label.equalsIgnoreCase("setspawnexit")) {
			if (!(cs.hasPermission("realmutilities.commands.setspawnexit")) && !(cs
					.isOp())) {
				cs.sendMessage(ChatColor.RED
						+ "You do not have permission to use this command 1.");
			} else {
				if (!(cs instanceof Player)) {
					if (args.length != 0) {
						cs.sendMessage(ChatColor.RED
								+ "There are no arguments for this command!");
					}
				}
				else{
					Player p = (Player) cs;
					if(p.hasPermission("realmutilities.commands.setspawnexit") || p.isOp()){
						plugin.players.set(p.getUniqueId().toString() + ".settingexit", true);
						p.sendMessage(ChatColor.BLUE + "You have been put into setexit mode, say " + ChatColor.GREEN + "cancel" + ChatColor.BLUE + " right click to set a block.");
						plugin.savePlayers();
					}
				}
				return false;
			}

		}
		return false;
	}
}
