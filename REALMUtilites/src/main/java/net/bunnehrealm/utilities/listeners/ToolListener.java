package net.bunnehrealm.utilities.listeners;

import java.util.ArrayList;
import java.util.List;

import net.bunnehrealm.utilities.RealmUtilities;
import net.bunnehrealm.utilities.managers.ToolManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolListener implements Listener {
	RealmUtilities plugin;

	public ToolListener(RealmUtilities instance) {
		this.plugin = instance;
	}

	ToolManager tm = new ToolManager(plugin);

	
	@EventHandler
	public void onToolJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Inventory inv = p.getInventory();
		ItemStack tool = new ItemStack(Material.WATCH);
		ItemMeta im = tool.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
				"&b&lREALM &dTool"));
		List<String> lore = new ArrayList<String>();
		lore.add(0, ChatColor.LIGHT_PURPLE + "Right Click");
		im.setLore(lore);
		tool.setItemMeta(im);
		if (!(inv.contains(tool))) {
			if (p.hasPermission("realmutilities.tool.spawn") || p.isOp()) {
				inv.addItem(tool);
			}
		}
	}
	
	
	@EventHandler
	public void onToolSpawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		Inventory inv = p.getInventory();
		ItemStack tool = new ItemStack(Material.WATCH);
		ItemMeta im = tool.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
				"&b&lREALM &dTool"));
		List<String> lore = new ArrayList<String>();
		lore.add(0, ChatColor.LIGHT_PURPLE + "Right Click");
		im.setLore(lore);
		tool.setItemMeta(im);
		if (!(inv.contains(tool))) {
			if (p.hasPermission("realmutilities.tool.spawn") || p.isOp()) {
				inv.addItem(tool);
			}
		}
	}

	@EventHandler
	public void onToolDrop(PlayerDropItemEvent e) {
		ItemStack tool = new ItemStack(Material.WATCH);
		ItemMeta im = tool.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
				"&b&lREALM &dTool"));
		List<String> lore = new ArrayList<String>();
		lore.add(0, ChatColor.LIGHT_PURPLE + "Right Click");
		im.setLore(lore);
		tool.setItemMeta(im);
		if (e.getItemDrop().getItemStack().hasItemMeta()) {
			if (e.getItemDrop()
					.getItemStack()
					.getItemMeta()
					.getDisplayName()
					.equalsIgnoreCase(
							ChatColor.translateAlternateColorCodes('&',
									"&b&lREALM &dTool"))) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onToolPickup(PlayerPickupItemEvent e) {
		ItemStack tool = new ItemStack(Material.WATCH);
		ItemMeta im = tool.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
				"&b&lREALM &dTool"));
		List<String> lore = new ArrayList<String>();
		lore.add(0, ChatColor.LIGHT_PURPLE + "Right Click");
		im.setLore(lore);
		tool.setItemMeta(im);
		if(e.getItem().getItemStack() == null){
			return;
		}
		if (e.getItem().getItemStack().hasItemMeta()) {
			if (e.getItem()
					.getItemStack()
					.getItemMeta()
					.getDisplayName()
					.equalsIgnoreCase(
							ChatColor.translateAlternateColorCodes('&',
									"&b&lREALM &dTool"))) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack is = p.getItemInHand();
		if(is == null){
			return;
		}
		if (is.hasItemMeta()) {
			if (is.getItemMeta()
					.getDisplayName()
					.equalsIgnoreCase(
							ChatColor.translateAlternateColorCodes('&',
									"&b&lREALM &dTool"))) {
				if (e.getAction() == Action.RIGHT_CLICK_AIR
						|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					tm.setMainMenu(p);
				}
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();

		for (String s : RealmUtilities.plugin.tool.getStringList("frames")) {
			if (inv.getName().equalsIgnoreCase(
					ChatColor.translateAlternateColorCodes(
							'&',
							RealmUtilities.plugin.tool.getString("frame." + s
									+ ".name")))) {
				e.setCancelled(true);
				for (String module : RealmUtilities.plugin.tool
						.getStringList("frame." + s + ".modules")) {
					int i = RealmUtilities.plugin.tool.getInt("frame." + s
							+ ".definedmodules." + module + ".slot");
					if (i == e.getSlot()) {
						if (RealmUtilities.plugin.tool.getString(
								"frame." + s + ".definedmodules." + module
										+ ".type").equalsIgnoreCase("command")) {
							for (String perm : RealmUtilities.plugin.tool
									.getStringList("frame." + s
											+ ".definedmodules." + module
											+ ".permission")) {
								if (p.hasPermission(perm)) {
									for (String args : RealmUtilities.plugin.tool
											.getStringList("frame." + s
													+ ".definedmodules."
													+ module + ".argument")) {
										p.performCommand(args);
										p.closeInventory();
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
