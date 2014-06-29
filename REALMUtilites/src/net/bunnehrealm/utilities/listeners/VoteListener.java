package net.bunnehrealm.utilities.listeners;

import java.util.ArrayList;
import java.util.List;

import net.bunnehrealm.realmstats.REALMStats;
import net.bunnehrealm.utilities.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteListener implements Listener {
	MainClass plugin = MainClass.plugin;

	public VoteListener(MainClass instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onVote(VotifierEvent e) {
		Player p = Bukkit.getPlayer(e.getVote().getUsername());
		List<String> list = MainClass.plugin.getConfig().getStringList(
				"Votifier.message");
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (String s : list) {
				s = s.replace("{player}", p.getName());
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						s));
			}
		}

		if (p.isOnline()) {
			String itemstring = MainClass.plugin.getConfig().getString(
					"Votifier.item");
			String[] parts = itemstring.split(";");
			String mat = parts[0];
			String amount = parts[1];
			int i = Integer.parseInt(amount);
			
			ItemStack is = new ItemStack(Material.getMaterial(mat), i);
			p.getInventory().addItem(is);
			if(MainClass.plugin.getConfig().getBoolean("REALMStats.enabled")){
				REALMStats.plugin.players.set(p.getUniqueId().toString() + ".votes", REALMStats.plugin.players.getInt(p.getUniqueId().toString() + ".votes" ) + 1);
			}
			
		}
	}
}
