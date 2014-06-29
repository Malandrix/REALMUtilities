package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.MainClass;
import net.bunnehrealm.utilities.tools.GUIManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteStartCommand implements CommandExecutor {
	MainClass plugin = MainClass.plugin;

	public VoteStartCommand(MainClass instance) {
		this.plugin = instance;
	}

	GUIManager gui = new GUIManager(plugin);

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {

		if (label.equalsIgnoreCase("votestart")) {
			if (!(cs instanceof Player)) {
				cs.sendMessage("This is a player only command.");
			}

			else {
				Player p = (Player) cs;
				if (p.hasPermission("realmutilities.vote.start") || p.isOp()) {
					
					gui.showStartGUI(p);
				}
				else{
					p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
					return false;
				}
			}

			return false;
		}
		return false;

	}
}
