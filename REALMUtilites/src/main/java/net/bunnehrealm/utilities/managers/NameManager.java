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
package net.bunnehrealm.utilities.managers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.bunnehrealm.utilities.RealmUtilities;
import net.bunnehrealm.utilities.commands.NickCommand;

public class NameManager {
	public RealmUtilities plugin = RealmUtilities.plugin;
	
	
	public NameManager(RealmUtilities instance) {
		this.plugin = instance;
	}

	public NickCommand NickCommand;

	public NameManager(NickCommand NickCommand) {
		this.NickCommand = NickCommand;
	}

	public void setDisplayName(Player p, String displayname) {
		p.setDisplayName(ChatColor.translateAlternateColorCodes('&',
				 displayname) + ChatColor.RESET);
		RealmUtilities.plugin.players.set(p.getUniqueId().toString()
				+ ".Display-Name", displayname);
	}

	public void setTabName(Player p, String tabname) { 
		RealmUtilities.plugin.players.set(p.getUniqueId().toString() + ".Tab-Name",
				tabname);
	}
}
