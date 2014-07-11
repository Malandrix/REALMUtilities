/*REALMUtilities is used for adding a great amount of features to your bukkit server.
 Copyright (C) 2013  Rory Finnegan
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.bunnehrealm.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.bunnehrealm.utilities.commands.LinksCommand;
import net.bunnehrealm.utilities.commands.NickCommand;
import net.bunnehrealm.utilities.commands.SMessageCmd;
import net.bunnehrealm.utilities.commands.SetDisplayNameCommand;
import net.bunnehrealm.utilities.commands.SetFirstSpawnCommand;
import net.bunnehrealm.utilities.commands.SetSpawnExitCommand;
import net.bunnehrealm.utilities.commands.SetTabNameCommand;
import net.bunnehrealm.utilities.commands.VoteCommand;
import net.bunnehrealm.utilities.commands.VoteStartCommand;
import net.bunnehrealm.utilities.listeners.ExitCancelListener;
import net.bunnehrealm.utilities.listeners.JoinListener;
import net.bunnehrealm.utilities.listeners.LeaveListener;
import net.bunnehrealm.utilities.listeners.SignPlaceListener;
import net.bunnehrealm.utilities.listeners.ToolListener;
import net.bunnehrealm.utilities.listeners.VoteListener;
import net.bunnehrealm.utilities.managers.GUIManager;
import net.bunnehrealm.utilities.managers.NameManager;
import net.bunnehrealm.utilities.managers.ToolManager;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RealmUtilities extends JavaPlugin {
	public NameManager NameManager = new NameManager(this);
	public static RealmUtilities plugin;

	public File playersFile;
	public FileConfiguration players;
	public File inventoryFile;
	public FileConfiguration inventory;
	public File locationsFile;
	public FileConfiguration locations;
	public File toolFile;
	public FileConfiguration tool;

	public static Chat chat = null;

	public SetDisplayNameCommand sdnc = new SetDisplayNameCommand(this);
	public VoteStartCommand vsc = new VoteStartCommand(this);
	public SetTabNameCommand stnc = new SetTabNameCommand(this);
	public GUIManager guim = new GUIManager(this);
	public JoinListener jl = new JoinListener(this);
	public ExitCancelListener ecl = new ExitCancelListener(this);
	public SignPlaceListener spl = new SignPlaceListener(this);
	public NickCommand nc = new NickCommand(this);
	public SetSpawnExitCommand ssec = new SetSpawnExitCommand(this);
	public SetFirstSpawnCommand sfsc = new SetFirstSpawnCommand(this);
	public SMessageCmd smc = new SMessageCmd(this);
	public LeaveListener ll = new LeaveListener(this);
	public VoteCommand vc = new VoteCommand(this);
	public LinksCommand lc = new LinksCommand(this);
	public VoteListener vl = new VoteListener(this);
	public ToolListener tl = new ToolListener(this);
	public ToolManager tm = new ToolManager(this);

	public static ScoreboardManager manager;
	public static Scoreboard board;
	public static Objective objective;

	public void onDisable() {
		
		getLogger().info("REALMUtilities has been disabled.");

		RealmUtilities.plugin.inventory.set("started", null);
		RealmUtilities.plugin.inventory.set("votetype", null);
		RealmUtilities.plugin.inventory.set("world", null);
		RealmUtilities.plugin.inventory.set("players", null);
		RealmUtilities.plugin.inventory.set("yes", null);
		RealmUtilities.plugin.saveInventory();
		
		plugin = null;
	}

	public void onEnable() {
		plugin = this;
		this.setupChat();
		new NameManager(this);
		playersFile = new File(getDataFolder(), "Players.yml");
		players = new YamlConfiguration();
		locationsFile = new File(getDataFolder(), "Locations.yml");
		locations = new YamlConfiguration();
		inventoryFile = new File(getDataFolder(), "Inventory.yml");
		inventory = new YamlConfiguration();
		toolFile = new File(getDataFolder(), "Tool.yml");
		tool = new YamlConfiguration();
		getLogger().info("REALMUtilities has been enabled.");
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(jl, this);
		pm.registerEvents(spl, this);
		pm.registerEvents(ecl, this);
		pm.registerEvents(ll, this);
		pm.registerEvents(guim, plugin);
		pm.registerEvents(vl, this);
		pm.registerEvents(tl, this);

		getCommand("smessage").setExecutor(smc);
		getCommand("setdisplayname").setExecutor(sdnc);
		getCommand("settabname").setExecutor(stnc);
		getCommand("nick").setExecutor(nc);
		getCommand("votestart").setExecutor(vsc);
		getCommand("vote").setExecutor(vc);
		getCommand("links").setExecutor(lc);

		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (board.getTeam(p.getName()) == null) {
						Team t = board.registerNewTeam(p.getName());
						t.addPlayer(p);

						p.setScoreboard(board);
					} else {

						Team t = board.getTeam(p.getName());
						if (chat.getPlayerPrefix(p) == null) {
							String[] groups = chat.getPlayerGroups(p);
							if (chat.getGroupPrefix(p.getWorld(), groups[0]) != null) {
								if (chat.getGroupPrefix(p.getWorld(), groups[0])
										.length() > 10) {
									t.setPrefix("["
											+ ChatColor
													.translateAlternateColorCodes(
															'&',
															chat.getGroupPrefix(
																	p.getWorld(),
																	groups[0]
																			.substring(
																					0,
																					11)))
											+ ChatColor.RESET + "] ");

									p.setScoreboard(board);
								} else {
									t.setPrefix("["
											+ ChatColor
													.translateAlternateColorCodes(
															'&',
															chat.getGroupPrefix(
																	p.getWorld(),
																	groups[0]))
											+ ChatColor.RESET + "] ");

									p.setScoreboard(board);
								}
							}
						} else {
							if (chat.getPlayerPrefix(p).length() > 10) {
								t.setPrefix("["
										+ ChatColor
												.translateAlternateColorCodes(
														'&',
														chat.getPlayerPrefix(p)
																.substring(0,
																		11))
										+ ChatColor.RESET + "] ");

								p.setScoreboard(board);
							} else {
								t.setPrefix("["
										+ ChatColor.translateAlternateColorCodes(
												'&', chat.getPlayerPrefix(p))
										+ ChatColor.RESET + "] ");

								p.setScoreboard(board);
							}
						}
					}
				}
			}
		}, 0, 20);

		getConfig().options().copyDefaults(true);

		if (!(this.getConfig().contains("Messages.firstjoin"))) {
			this.getConfig().set(
					"Messages.firstjoin",
					"{player} &e has joined &b[server] &efor the first time!"
							+ "\n" + "&ePlease welcome them!");
		}
		if (!(this.getConfig().contains("Messages.join"))) {
			this.getConfig().set("Messages.join",
					"{player} &e has logged into &b[server]");
		}
		if (!(this.getConfig().contains("Messages.leave"))) {
			this.getConfig().set("Messages.leave",
					"{player} &e has left &b[server]");
		}
		if (!(this.getConfig().contains("Broadcast.time"))) {
			this.getConfig().set("Broadcast.time", 600);
		}
		if (!(this.getConfig().contains("Broadcast.messages"))) {
			List<String> list = new ArrayList<String>();
			list.add("Example");
			this.getConfig().set("Broadcast.messages", list);
		}
		if (!(this.getConfig().contains("Links"))) {
			List<String> list = new ArrayList<String>();
			list.add("Example");
			this.getConfig().set("Links", list);
		}
		if (!(this.getConfig().contains("Votifier.use"))) {
			this.getConfig().set("Votifier.use", true);
		}
		if (!(this.getConfig().contains("Votifier.message"))) {
			List<String> list = new ArrayList<String>();
			list.add("Example");
			this.getConfig().set("Votifier.message", true);
		}
		if (!(this.getConfig().contains("Votifier.stattrack"))) {
			this.getConfig().set("Votifier.stattrack", true);
		}
		if (!(this.getConfig().contains("Votifier.item"))) {
			this.getConfig().set("Votifier.item", "IRON_INGOT;4");
		}
		if (!(this.getConfig().contains("REALMStats.enabled"))) {
			this.getConfig().set("REALMStats.enabled", true);
		}

		saveConfig();

		try {
			firstPlayerRun();
		} catch (Exception e) {
			System.out.println("Could not run firstPlayerRun");
			e.printStackTrace();
		}
		loadPlayers();
		savePlayers();
		try {
			firstLocationsRun();
		} catch (Exception e) {
			System.out.println("Could not run firstLocationsRun");
			e.printStackTrace();
		}
		loadLocations();
		saveLocations();
		try {
			firstInventoryRun();
		} catch (Exception e) {
			System.out.println("Could not run firstInventoryRun");
			e.printStackTrace();
		}
		loadInventory();
		saveInventory();
		RealmUtilities.plugin.inventory.set("started", null);
		RealmUtilities.plugin.inventory.set("votetype", null);
		RealmUtilities.plugin.inventory.set("world", null);
		RealmUtilities.plugin.inventory.set("players", null);
		RealmUtilities.plugin.inventory.set("yes", null);
		RealmUtilities.plugin.saveInventory();
		try {
			firstToolRun();
		} catch (Exception e) {
			System.out.println("Could not run firstToolRun");
			e.printStackTrace();
		}
		loadTool();
		saveTool();

		tm.setupTool();

		scheduler.scheduleSyncRepeatingTask(
				this,
				new BukkitRunnable() {

					@Override
					public void run() {
						List<String> list = getConfig().getStringList(
								"Broadcast.messages");
						String[] array = new String[list.size()];
						list.toArray(array);
						int random = 0 + (int) (Math.random() * list.size());
						String msg = array[random].replace("{line}", "\n");
						Bukkit.broadcastMessage(ChatColor.GOLD
								+ "--------------------------------------------------");
						Bukkit.broadcastMessage(ChatColor
								.translateAlternateColorCodes('&', msg));
						Bukkit.broadcastMessage(ChatColor.GOLD
								+ "--------------------------------------------------");

					}

				}, 20 * getConfig().getInt("Broadcast.time"),
				20 * getConfig().getInt("Broadcast.time"));

	}

	public void firstPlayerRun() throws Exception {
		if (!playersFile.exists()) {
			getLogger().info("Creating a Players.yml file");
			playersFile.getParentFile().mkdirs();
			playersFile.createNewFile();

		}
	}

	public void loadPlayers() {
		try {
			players.load(playersFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savePlayers() {
		try {
			players.save(playersFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void firstLocationsRun() throws Exception {
		if (!locationsFile.exists()) {
			getLogger().info("Creating a Locations.yml file");
			locationsFile.getParentFile().mkdirs();
			locationsFile.createNewFile();

		}
	}

	public void loadLocations() {
		try {
			locations.load(locationsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveLocations() {
		try {
			locations.save(locationsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void firstInventoryRun() throws Exception {
		if (!inventoryFile.exists()) {
			getLogger().info("Creating a inventory.yml file");
			inventoryFile.getParentFile().mkdirs();
			inventoryFile.createNewFile();

		}
	}

	public void loadInventory() {
		try {
			inventory.load(inventoryFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveInventory() {
		try {
			inventory.save(inventoryFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void firstToolRun() throws Exception {
		if (!toolFile.exists()) {
			getLogger().info("Creating a Tool.yml file");
			toolFile.getParentFile().mkdirs();
			toolFile.createNewFile();

		}
	}

	public void loadTool() {
		try {
			tool.load(toolFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveTool() {
		try {
			tool.save(toolFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}
}
