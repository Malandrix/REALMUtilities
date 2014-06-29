package net.bunnehrealm.utilities.tools;

import java.util.HashMap;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AFKManager {
	MainClass plugin = MainClass.plugin;
	
	public AFKManager(MainClass instance){
		this.plugin = instance;
	}

	public void afkCheck(){
		HashMap<String, Location> hm = new HashMap<String, Location>();
		for(Player p : Bukkit.getOnlinePlayers()){
			if(hm.containsKey(p.getUniqueId())){
				if(p.getLocation() == hm.get(p.getUniqueId())){
					MainClass.plugin.players.set(p.getUniqueId() + ".afk", true);
				}
			}
		}
	}
	
}
