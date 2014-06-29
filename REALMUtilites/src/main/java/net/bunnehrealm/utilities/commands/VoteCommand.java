package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.MainClass;
import net.bunnehrealm.utilities.tools.GUIManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
	MainClass plugin = MainClass.plugin;

	public VoteCommand(MainClass instance) {
		this.plugin = instance;
	}

	GUIManager gm = new GUIManager(plugin);

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {

		if (label.equalsIgnoreCase("vote")) {
			if (!(cs instanceof Player)) {
				cs.sendMessage("That command is for players only");
			} else {
				Player p = (Player) cs;
				if (p.hasPermission("realmutilities.vote") || p.isOp()) {
					if (!(MainClass.plugin.inventory.getBoolean("started"))) {
						p.sendMessage(ChatColor.RED
								+ "No vote has started yet.");
					} else {
						if (MainClass.plugin.inventory.getStringList("players")
								.contains(p.getUniqueId().toString())) {
							p.sendMessage(ChatColor.RED
									+ "You have already participated in this vote!");
						} else {
							gm.showGUI(p);
						}
					}
					return false;
				} else {
					

				}
			}
		}

		return false;
	}
}
