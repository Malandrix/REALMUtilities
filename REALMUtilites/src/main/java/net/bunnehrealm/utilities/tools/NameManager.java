package net.bunnehrealm.utilities.tools;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.bunnehrealm.utilities.MainClass;
import net.bunnehrealm.utilities.commands.NickCommand;

public class NameManager {
	public MainClass plugin = MainClass.plugin;
	
	
	public NameManager(MainClass instance) {
		this.plugin = instance;
	}

	public NickCommand NickCommand;

	public NameManager(NickCommand NickCommand) {
		this.NickCommand = NickCommand;
	}

	public void setDisplayName(Player p, String displayname) {
		p.setDisplayName(ChatColor.translateAlternateColorCodes('&',
				 displayname) + ChatColor.RESET);
		MainClass.plugin.players.set(p.getUniqueId().toString()
				+ ".Display-Name", displayname);
	}

	public void setTabName(Player p, String tabname) { 
		MainClass.plugin.players.set(p.getUniqueId().toString() + ".Tab-Name",
				tabname);
	}
}
