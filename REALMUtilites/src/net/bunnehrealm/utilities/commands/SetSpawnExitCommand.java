package net.bunnehrealm.utilities.commands;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnExitCommand implements CommandExecutor {
	MainClass plugin = MainClass.plugin;
	public SetSpawnExitCommand(MainClass instance){
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label,
			String[] args) {
		if (label.equalsIgnoreCase("setspawnexit")) {
			if (!(cs.hasPermission("realmutilities.commands.setspawnexit")) && !(cs
					.isOp())) {
				cs.sendMessage(ChatColor.RED
						+ "You do not have permission to use this command 1.");
			} else {
				if (!(cs instanceof Player)) {
					if (args.length != 0) {
						cs.sendMessage(ChatColor.RED
								+ "There are no arguments for this command!");
					}
				}
				else{
					Player p = (Player) cs;
					if(p.hasPermission("realmutilities.commands.setspawnexit") || p.isOp()){
						plugin.players.set(p.getUniqueId().toString() + ".settingexit", true);
						p.sendMessage(ChatColor.BLUE + "You have been put into setexit mode, say " + ChatColor.GREEN + "cancel" + ChatColor.BLUE + " right click to set a block.");
						plugin.savePlayers();
					}
				}
				return false;
			}

		}
		return false;
	}
}
