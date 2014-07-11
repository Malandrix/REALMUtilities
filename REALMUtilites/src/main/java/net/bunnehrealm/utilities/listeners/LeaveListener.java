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
package net.bunnehrealm.utilities.listeners;

import net.bunnehrealm.utilities.RealmUtilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
	RealmUtilities plugin;

	public LeaveListener(RealmUtilities instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String[] playergroups = RealmUtilities.chat.getPlayerGroups(p);
		if (RealmUtilities.chat.getPlayerPrefix(p) != null) {
			String prefix = RealmUtilities.chat.getPlayerPrefix(p);
			e.setQuitMessage(ChatColor.translateAlternateColorCodes(
					'&',
					plugin.getConfig().getString("Messages.leave")
							.replace("{player}", p.getName())
							.replace("{prefix}", prefix)));
		} else if (RealmUtilities.chat.getGroupPrefix(p.getWorld(), playergroups[0]) != null) {
			String prefix = RealmUtilities.chat.getGroupPrefix(p.getWorld(),
					playergroups[0]);
			e.setQuitMessage(ChatColor.translateAlternateColorCodes(
					'&',
					plugin.getConfig().getString("Messages.leave")
							.replace("{player}", p.getName())
							.replace("{prefix}", prefix)));
		} else {
			e.setQuitMessage(ChatColor.translateAlternateColorCodes(
					'&',
					plugin.getConfig().getString("Messages.leave")
							.replace("{player}", p.getName())
							.replace("{prefix}", "")));
		}
	}
}
