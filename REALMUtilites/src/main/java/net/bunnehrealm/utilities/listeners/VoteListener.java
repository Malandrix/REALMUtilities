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

import java.util.List;

import net.bunnehrealm.realmstats.REALMStats;
import net.bunnehrealm.utilities.RealmUtilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteListener implements Listener {
	RealmUtilities plugin = RealmUtilities.plugin;

	public VoteListener(RealmUtilities instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onVote(VotifierEvent e) {

		List<String> list = RealmUtilities.plugin.getConfig().getStringList(
				"Votifier.message");

		for (String s : list) {
			s = s.replace("{player}", e.getVote().getUsername());
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
					s));
		}
		try {
			Player p = Bukkit.getPlayer(e.getVote().getUsername());

			if (RealmUtilities.plugin.getConfig().getBoolean("REALMStats.enabled")) {
				REALMStats.plugin.players.set(
						p.getUniqueId().toString() + ".votes",
						REALMStats.plugin.players.getInt(p.getUniqueId()
								.toString() + ".votes") + 1);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		Player p = Bukkit.getPlayer(e.getVote().getUsername());
		String itemstring = RealmUtilities.plugin.getConfig().getString(
				"Votifier.item");
		String[] parts = itemstring.split(";");
		String mat = parts[0];
		String amount = parts[1];
		int i = Integer.parseInt(amount);

		ItemStack is = new ItemStack(Material.getMaterial(mat), i);
		p.getInventory().addItem(is);

	}
}
