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

import java.util.HashMap;

import net.bunnehrealm.utilities.RealmUtilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AFKManager {
	RealmUtilities plugin = RealmUtilities.plugin;
	
	public AFKManager(RealmUtilities instance){
		this.plugin = instance;
	}

	public void afkCheck(){
		HashMap<String, Location> hm = new HashMap<String, Location>();
		for(Player p : Bukkit.getOnlinePlayers()){
			if(hm.containsKey(p.getUniqueId())){
				if(p.getLocation() == hm.get(p.getUniqueId())){
					RealmUtilities.plugin.players.set(p.getUniqueId() + ".afk", true);
				}
			}
		}
	}
	
}
