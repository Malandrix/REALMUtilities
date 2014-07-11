package net.bunnehrealm.utilities.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.bunnehrealm.utilities.RealmUtilities;

public class ToolManager {
	public RealmUtilities plugin;

	public ToolManager(RealmUtilities instance) {
		this.plugin = instance;
	}

	public void setupTool() {
		if (!(RealmUtilities.plugin.tool.contains("frames"))) {
			List<String> frames = new ArrayList<String>();
			frames.add("main");
			RealmUtilities.plugin.tool.set("frames", frames);
		}
		if (!(RealmUtilities.plugin.tool.contains("frame.main.name"))) {
			RealmUtilities.plugin.tool.set("frame.main.name", "&5Main");
		}
		if (!(RealmUtilities.plugin.tool.contains("frame.main.tiles"))) {
			RealmUtilities.plugin.tool.set("frame.main.tiles", 9);
		}
		if (!(RealmUtilities.plugin.tool.contains("frame.main.modules"))) {
			List<String> modules = new ArrayList<String>();
			modules.add("main_01");
			RealmUtilities.plugin.tool.set("frame.main.modules", modules);
		}
		if (!(RealmUtilities.plugin.tool.contains("frame.main.definedmodules"))) {
			RealmUtilities.plugin.tool.set("frame.main.definedmodules",
					"frame.main.definedmodules.main_01");
			RealmUtilities.plugin.tool.set("frame.main.definedmodules.main_01.slot",
					0);
			RealmUtilities.plugin.tool.set("frame.main.definedmodules.main_01.type",
					"command");
			List<String> arguments = new ArrayList<String>();
			arguments.add("im a pirate");
			RealmUtilities.plugin.tool.set(
					"frame.main.definedmodules.main_01.argument", arguments);
			List<String> permission = new ArrayList<String>();
			permission.add("realmutilities.tool.main.mod.main_01");
			RealmUtilities.plugin.tool.set(
					"frame.main.definedmodules.main_01.permission", permission);
			RealmUtilities.plugin.tool
					.set("frame.main.definedmodules.main_01.item.material",
							"BEACON");
			RealmUtilities.plugin.tool.set(
					"frame.main.definedmodules.main_01.item.name",
					"&6Go to Spawn");
			List<String> lore = new ArrayList<String>();
			lore.add(0, "Click to go to");
			lore.add(1, "Spawn");
			RealmUtilities.plugin.tool.set(
					"frame.main.definedmodules.main_01.item.lore", lore);
		}
		RealmUtilities.plugin.saveTool();
	}

	public void setMainMenu(final Player p) {
		RealmUtilities.plugin.loadTool();
		Inventory inv = Bukkit.createInventory(p, RealmUtilities.plugin.tool
				.getInt("frame.main.tiles"), ChatColor
				.translateAlternateColorCodes('&',
						RealmUtilities.plugin.tool.getString("frame.main.name")));
		for (String s : RealmUtilities.plugin.tool
				.getStringList("frame.main.modules")) {
			if (p.hasPermission("realmutilities.tool.main.mod." + s)
					|| p.isOp()) {
				ItemStack is = new ItemStack(
						Material.getMaterial(RealmUtilities.plugin.tool
								.getString("frame.main.definedmodules." + s
										+ ".item.material")));
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(ChatColor.translateAlternateColorCodes('&',
						RealmUtilities.plugin.tool
								.getString("frame.main.definedmodules." + s
										+ ".item.name")));
				List<String> ls = RealmUtilities.plugin.tool
						.getStringList("frame.main.definedmodules." + s
								+ ".item.lore");
				for (String stringle : ls) {
					stringle = stringle.replaceAll("(?i)&([a-f0-9])",
							"\u00A7$1");
				}
				im.setLore(ls);
				is.setItemMeta(im);

				inv.setItem(RealmUtilities.plugin.tool
						.getInt("frame.main.definedmodules." + s + ".slot"), is);
			}
		}
		p.openInventory(inv);
	}
	
}
