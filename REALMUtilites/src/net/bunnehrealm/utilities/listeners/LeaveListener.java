package net.bunnehrealm.utilities.listeners;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {
	MainClass plugin;

	public LeaveListener(MainClass instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String[] playergroups = MainClass.chat.getPlayerGroups(p);
		if (MainClass.chat.getPlayerPrefix(p) != null) {
			String prefix = MainClass.chat.getPlayerPrefix(p);
			e.setQuitMessage(ChatColor.translateAlternateColorCodes(
					'&',
					plugin.getConfig().getString("Messages.leave")
							.replace("{player}", p.getName())
							.replace("{prefix}", prefix)));
		} else if (MainClass.chat.getGroupPrefix(p.getWorld(), playergroups[0]) != null) {
			String prefix = MainClass.chat.getGroupPrefix(p.getWorld(),
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
