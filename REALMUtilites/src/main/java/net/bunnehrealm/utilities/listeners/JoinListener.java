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
import net.bunnehrealm.utilities.managers.NameManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scoreboard.Team;

public class JoinListener implements Listener {
	RealmUtilities plugin = RealmUtilities.plugin;
	public Team team;
	NameManager nm = new NameManager(plugin);

	public JoinListener(RealmUtilities instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		RealmUtilities.plugin.players.set(p.getUniqueId().toString() + ".Real-Name",
				p.getName().toString());
		if (RealmUtilities.plugin.players.contains(p.getUniqueId().toString()
				+ ".Display-Name")) {
			nm.setDisplayName(
					p,
					RealmUtilities.plugin.players.getString(p.getUniqueId()
							.toString() + ".Display-Name"));
		}
		if (RealmUtilities.plugin.players.contains(p.getUniqueId().toString()
				+ ".Tab-Name")) {
			nm.setTabName(
					p,
					RealmUtilities.plugin.players.getString(p.getUniqueId()
							.toString() + ".Tab-Name"));
		}
		if (!(RealmUtilities.plugin.players
				.contains(p.getUniqueId() + ".left-spawn"))) {
			RealmUtilities.plugin.players.set(p.getUniqueId().toString()
					+ ".left-spawn", false);
		}
		plugin.savePlayers();
	}

	@EventHandler
	public void onMessageJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!(plugin.players
				.contains(p.getUniqueId().toString() + ".hasjoined"))) {
			plugin.players.set(p.getUniqueId().toString() + ".hasjoined", true);
			e.setJoinMessage(ChatColor.translateAlternateColorCodes(
					'&',
					plugin.getConfig().getString("Messages.firstjoin")
							.replace("{player}", p.getName())));
		} else {
			String[] playergroups = RealmUtilities.chat.getPlayerGroups(p);
			if (RealmUtilities.chat.getPlayerPrefix(p) != null) {
				String prefix = RealmUtilities.chat.getPlayerPrefix(p);
				e.setJoinMessage(ChatColor.translateAlternateColorCodes(
						'&',
						plugin.getConfig().getString("Messages.join")
								.replace("{player}", p.getName())
								.replace("{prefix}", prefix)));
			} else if (RealmUtilities.chat.getGroupPrefix(p.getWorld(),
					playergroups[0]) != null) {
				String prefix = RealmUtilities.chat.getGroupPrefix(p.getWorld(),
						playergroups[0]);
				e.setJoinMessage(ChatColor.translateAlternateColorCodes(
						'&',
						plugin.getConfig().getString("Messages.join")
								.replace("{player}", p.getName())
								.replace("{prefix}", prefix)));
			} else {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes(
						'&',
						plugin.getConfig().getString("Messages.join")
								.replace("{player}", p.getName())
								.replace("{prefix}", "")));
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		e.allow();
		if(e.getResult() == PlayerLoginEvent.Result.KICK_FULL && (p.hasPermission("realmutilities.joinfull") || p.isOp())){
			e.allow();
		}
	}
	
	

}
