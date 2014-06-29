package net.bunnehrealm.utilities.listeners;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ExitCancelListener implements Listener {
	MainClass plugin = MainClass.plugin;

	public ExitCancelListener(MainClass instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onCancel(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().equalsIgnoreCase("cancel")) {
			if (plugin.players.getBoolean(p.getUniqueId().toString()
					+ ".settingexit")) {
				plugin.players.set(p.getUniqueId().toString()
					+ ".settingexit", false);
				plugin.savePlayers();
				p.sendMessage(ChatColor.BLUE + "You have exited the exit setting mode.");
				e.setCancelled(true);
			}
		}

	}
}
