package net.bunnehrealm.utilities.tools;

import java.util.ArrayList;
import java.util.List;

import net.bunnehrealm.utilities.MainClass;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class GUIManager implements Listener {
	MainClass plugin = MainClass.plugin;

	public GUIManager(MainClass instance) {
		this.plugin = instance;
	}

	public void showStartGUI(Player p) {
		Inventory invstart = Bukkit.createInventory(p, 9, "Start a vote");
		ItemStack is = new ItemStack(Material.GLOWSTONE);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Vote Day");
		List<String> list = new ArrayList<String>();
		list.add(0, ChatColor.AQUA + "Start a vote");
		list.add(1, ChatColor.AQUA + "for day.");
		meta.setLore(list);
		is.setItemMeta(meta);
		invstart.setItem(0, is);

		MainClass.plugin.inventory.set("InvName", invstart.getName());
		MainClass.plugin.saveInventory();

		p.openInventory(invstart);
	}

	public void showGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9, "Vote");
		ItemStack is = new ItemStack(Material.WOOL,
				MainClass.plugin.inventory.getInt("yes"), (byte) 5);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Yes");
		List<String> list = new ArrayList<String>();
		list.add(0, ChatColor.GREEN + "Vote yes for");
		list.add(1, ChatColor.GREEN + "the current vote.");
		meta.setLore(list);
		is.setItemMeta(meta);
		inv.setItem(3, is);

		ItemStack is2 = new ItemStack(Material.WOOL, 0, (byte) 14);
		ItemMeta meta2 = is.getItemMeta();
		meta2.setDisplayName(ChatColor.RED + "No");
		List<String> list2 = new ArrayList<String>();
		list2.add(0, ChatColor.RED + "Vote no for");
		list2.add(1, ChatColor.RED + "the current vote.");
		meta2.setLore(list2);
		is2.setItemMeta(meta2);
		inv.setItem(5, is2);

		p.openInventory(inv);
	}

	@EventHandler
	public boolean onFirstClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack choice = e.getCurrentItem();
		Inventory inv = e.getInventory();
		
		
		
		if (inv.getName().equals(
				"Start a vote")) {
			if (!(MainClass.plugin.inventory.getBoolean("started"))) {
				if (choice.getType() == Material.GLOWSTONE) {
					e.setCancelled(true);
					p.closeInventory();
					final World world = p.getWorld();
					if (world.getTime() < 13000) {
						p.sendMessage(ChatColor.RED
								+ "It is too early for that!");
						return false;
					}
					MainClass.plugin.inventory.set("started", true);
					MainClass.plugin.inventory.set("votetype", "DAY");
					MainClass.plugin.inventory.set("world", p.getWorld()
							.getName());
					MainClass.plugin.saveInventory();
					startCountdown(p);
				}
			} else {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage(ChatColor.RED
						+ "A vote has already been initiated.");
			}
		}
		return false;
	}

	public void sendStartMsg(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(ChatColor.GOLD
					+ "-------------------------------------------");
			player.sendMessage(ChatColor.AQUA + "A vote for " + ChatColor.GOLD
					+ MainClass.plugin.inventory.getString("votetype")
					+ ChatColor.AQUA + " in " + ChatColor.GOLD
					+ p.getWorld().getName() + ChatColor.AQUA
					+ " has been started.");
			player.sendMessage(ChatColor.AQUA
					+ "This vote was brought to you by: " + ChatColor.GREEN
					+ p.getName());
			player.sendMessage(ChatColor.AQUA + "Type " + ChatColor.GREEN + "/vote " + ChatColor.AQUA + "to open the vote menu!");
			player.sendMessage(ChatColor.GOLD
					+ "-------------------------------------------");
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack choice = e.getCurrentItem();
		Inventory inv = e.getInventory();
		if (inv.getName().equals("Vote")) {

			if (choice.getType() == Material.WOOL
					&& choice.getData().getData() == (byte) 5) {
				List<String> list = MainClass.plugin.inventory
						.getStringList("players");
				if (!(list.contains(p.getUniqueId().toString()))) {
					list.add(p.getUniqueId().toString());
					MainClass.plugin.inventory.set("players", list);
					MainClass.plugin.inventory.set("yes",
							MainClass.plugin.inventory.getInt("yes") + 1);
					MainClass.plugin.saveInventory();
					p.sendMessage(ChatColor.AQUA + "Thank you for voting!");
					e.setCancelled(true);
					p.closeInventory();
				}
			}
			if (choice.getType() == Material.WOOL
					&& choice.getData().getData() == (byte) 14) {
				List<String> list = MainClass.plugin.inventory
						.getStringList("players");
				if (!(list.contains(p.getUniqueId().toString()))) {
					list.add(p.getUniqueId().toString());
					MainClass.plugin.inventory.set("players", list);
					p.sendMessage(ChatColor.AQUA + "Thank you for voting!");
					e.setCancelled(true);
					p.closeInventory();
					MainClass.plugin.saveInventory();
				}
			}
		}
	}

	public boolean startCountdown(Player p) {
		final World world = Bukkit.getWorld(MainClass.plugin.inventory
				.getString("world"));
		if (world.getTime() < 13000) {
			p.sendMessage(ChatColor.RED + "It's too early for that!");
			return false;
		}
		final BukkitScheduler bs = Bukkit.getScheduler();

		sendStartMsg(p);

		bs.runTaskLater(plugin, new BukkitRunnable() {

			@Override
			public void run() {

				MainClass.plugin.inventory.set("started", false);

				if (MainClass.plugin.inventory.getString("votetype")
						.equalsIgnoreCase("DAY")) {

					if (MainClass.plugin.inventory.getInt("yes") > Bukkit
							.getOnlinePlayers().length * 0.60) {

						world.setTime(0);
						Bukkit.broadcastMessage(ChatColor.AQUA
								+ "Due to popular demand the time for " + ChatColor.GOLD
								+ world.getName() + ChatColor.AQUA
								+ " has been set to day!");
						MainClass.plugin.inventory.set("world", "");
						MainClass.plugin.inventory.set("started", false);
						MainClass.plugin.inventory.set("players", "");
						MainClass.plugin.saveInventory();

					} else {
						World world = Bukkit
								.getWorld(MainClass.plugin.inventory
										.getString("world"));
						Bukkit.broadcastMessage(ChatColor.RED
								+ "The time vote for " + ChatColor.GOLD
								+ world.getName() + ChatColor.RED
								+ " has failed!");
						MainClass.plugin.inventory.set("world", "");
						MainClass.plugin.inventory.set("started", false);
						MainClass.plugin.inventory.set("players", "");
						MainClass.plugin.inventory.set("yes",
								0);
						MainClass.plugin.saveInventory();
					}
				}

			}
		}, 600);
		return false;

	}
}
