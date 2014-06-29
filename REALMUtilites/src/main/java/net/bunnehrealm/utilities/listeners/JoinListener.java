package net.bunnehrealm.utilities.listeners;

import net.bunnehrealm.utilities.MainClass;
import net.bunnehrealm.utilities.tools.NameManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Team;

public class JoinListener implements Listener {
	MainClass plugin = MainClass.plugin;
	public Team team;
	NameManager nm = new NameManager(plugin);

	public JoinListener(MainClass instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		MainClass.plugin.players.set(p.getUniqueId().toString() + ".Real-Name",
				p.getName().toString());
		if (MainClass.plugin.players.contains(p.getUniqueId().toString()
				+ ".Display-Name")) {
			nm.setDisplayName(
					p,
					MainClass.plugin.players.getString(p.getUniqueId()
							.toString() + ".Display-Name"));
		}
		if (MainClass.plugin.players.contains(p.getUniqueId().toString()
				+ ".Tab-Name")) {
			nm.setTabName(
					p,
					MainClass.plugin.players.getString(p.getUniqueId()
							.toString() + ".Tab-Name"));
		}
		if (!(MainClass.plugin.players
				.contains(p.getUniqueId() + ".left-spawn"))) {
			MainClass.plugin.players.set(p.getUniqueId().toString()
					+ ".left-spawn", false);
		}
		plugin.savePlayers();
	}

	@EventHandler
	public void onMessageJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!(plugin.players
				.contains(p.getUniqueId().toString() + ".hasjoined"))) {
			plugin.players.set(p.getUniqueId().toString() + ".hasjoined", true);
			e.setJoinMessage(ChatColor.translateAlternateColorCodes(
					'&',
					plugin.getConfig().getString("Messages.firstjoin")
							.replace("{player}", p.getName())));
		} else {
			String[] playergroups = MainClass.chat.getPlayerGroups(p);
			if (MainClass.chat.getPlayerPrefix(p) != null) {
				String prefix = MainClass.chat.getPlayerPrefix(p);
				e.setJoinMessage(ChatColor.translateAlternateColorCodes(
						'&',
						plugin.getConfig().getString("Messages.join")
								.replace("{player}", p.getName())
								.replace("{prefix}", prefix)));
			} else if (MainClass.chat.getGroupPrefix(p.getWorld(),
					playergroups[0]) != null) {
				String prefix = MainClass.chat.getGroupPrefix(p.getWorld(),
						playergroups[0]);
				e.setJoinMessage(ChatColor.translateAlternateColorCodes(
						'&',
						plugin.getConfig().getString("Messages.join")
								.replace("{player}", p.getName())
								.replace("{prefix}", prefix)));
			} else {
				e.setJoinMessage(ChatColor.translateAlternateColorCodes(
						'&',
						plugin.getConfig().getString("Messages.join")
								.replace("{player}", p.getName())
								.replace("{prefix}", "")));
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		e.allow();
		if(e.getResult() == PlayerLoginEvent.Result.KICK_FULL && (p.hasPermission("realmutilities.joinfull") || p.isOp())){
			e.allow();
		}
	}
	
	
	@EventHandler
	public void onToolJoin(PlayerLoginEvent e){
		Player p = e.getPlayer();
		Inventory inv = p.getInventory();
		ItemStack tool = new ItemStack(Material.WATCH);
		ItemMeta im = tool.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lREALM &dTool"));
	}
}
