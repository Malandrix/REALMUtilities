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
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.SignChangeEvent;

public class SignPlaceListener implements Listener {
	public RealmUtilities plugin;

	public SignPlaceListener(RealmUtilities instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {

		Block b = e.getBlock();
		Player p = e.getPlayer();
		if (p.hasPermission("realmutilities.sign.color") || p.isOp()) {

			if (b.getType() == Material.SIGN
					|| b.getType() == Material.WALL_SIGN
					|| b.getType() == Material.SIGN_POST) {

				e.setLine(0, ChatColor.translateAlternateColorCodes('&', e
						.getLine(0).toString()));
				e.setLine(1, ChatColor.translateAlternateColorCodes('&', e
						.getLine(1).toString()));
				e.setLine(2, ChatColor.translateAlternateColorCodes('&', e
						.getLine(2).toString()));
				e.setLine(3, ChatColor.translateAlternateColorCodes('&', e
						.getLine(3).toString()));
			}
		}
	}
}
