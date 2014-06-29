package net.bunnehrealm.utilities.listeners;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.SignChangeEvent;

public class SignPlaceListener implements Listener {
	public MainClass plugin;

	public SignPlaceListener(MainClass instance) {
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
