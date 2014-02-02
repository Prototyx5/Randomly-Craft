package randomlycraft.fr.prototyx5;

/*
 * Copyright 2014-2015 Dylan Benincasa. All rights reserved.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RandomlyCraft_Main extends JavaPlugin implements Listener{
	public static RandomlyCraft_Main plugin;
	Logger log = Logger.getLogger("Randomly-Craft");
	
	public void onEnable() {

		
		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		log.info("                                                              ");
		log.info("[Randomly-Craft Start] Auteur du plugin: Prototyx5.");
		log.info("[Randomly-Craft Start] Version du plugin: 7.0B ");
		log.info("                                                              ");
		log.info("[Randomly-Craft Start] Les plugins on bien ete charger et demarre !");
		log.info("[Randomly-Craft Start] Les Metrics on ete charger !");
		log.info("[Randomly-Craft Start] Stacker a ete charger !");
		log.info("[Randomly-Craft Start] JoinAndQuit a ete charger !");
		log.info("                                                              ");
		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		Bukkit.broadcastMessage(ChatColor.AQUA + "[Randomly-Craft] " + ChatColor.RESET + ChatColor.DARK_PURPLE + "Le Serveur c'est bien lancé !");

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e1) {
			// Failed to submit the stats :-(
		}
	}

	public void onDisable() {

		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		log.info("                                                              ");
		log.info("[Randomly-Craft Stop] Auteur du plugin: Prototyx5.");
		log.info("                                                              ");
		log.info("[Randomly-Craft Stop] Les plugins on bien ete arrete !");
		log.info("[Randomly-Craft Stop] Les Metrics on ete arrete !");
		log.info("[Randomly-Craft Stop] Stacker a ete arrete !");
		log.info("[Randomly-Craft Stop] JoinAndQuit a ete arreter !");
		log.info("                                                              ");
		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	}

	public enum Items {
		Gun;
	}

	public ItemStack getCustomItem(Items item) {
		ItemStack is = null;
		ItemMeta im;
		ArrayList<String> lore;
		switch (item) {
		case Gun:
			is = new ItemStack(Material.LAVA, 1);
			im = is.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + "Legendary Wither Gun");
			lore = new ArrayList<String>();
			lore.add(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.GREEN + " Custom Items");
			lore.add(ChatColor.GOLD + "Faire un clique droit dans le vide");
			lore.add(ChatColor.GOLD + "avec cette arme fais tiré des tète de wither !");
			im.setLore(lore);
			is.setItemMeta(im);

		}
		return is;
	}


	@EventHandler
	public void onPlayerConnect(PlayerJoinEvent j) {
		Player player = j.getPlayer();
		j.setJoinMessage("");
		Server serv = Bukkit.getServer();
		serv.broadcastMessage(ChatColor.AQUA + "[Randomly-Craft ConnectGestion] " + ChatColor.RESET + ChatColor.ITALIC + ChatColor.YELLOW + player.getName() + " a rejoin le serveur !");

	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent l) {
		Player player = l.getPlayer();
		l.setQuitMessage("");
		Server serv = Bukkit.getServer();
		serv.broadcastMessage(ChatColor.AQUA + "[Randomly-Craft DisconnectGestion] " + ChatColor.RESET + ChatColor.ITALIC + ChatColor.YELLOW + player.getName() + " a quitté le serveur !");

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR)) return;
		if(!(e.getItem().getType() == Material.LAVA)) return;
		Arrow w = e.getPlayer().launchProjectile(Arrow.class);
		w.getFireTicks();
		w.getMaxFireTicks();
	}

	@EventHandler
	public void onPlayerPlaceWater(BlockPlaceEvent place) {
		Block block = place.getBlockPlaced();	
		if(!(place.getBlock().getType() == Material.LAVA)) return;
		place.isCancelled();
		block.breakNaturally();
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		entity.getLocation().getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Arrow) {
			Arrow w = (Arrow) e.getDamager();
			if (w.getShooter() instanceof Player) {
				Player shooter = (Player) w.getShooter();
				if(shooter.getItemInHand().getType() == Material.LAVA);
				e.setDamage(2000000000);
				

			}
		}
	}



	@SuppressWarnings("unused")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		Player player = (Player) sender;

		if(sender instanceof Player) {


			if(label.equalsIgnoreCase("ender")) {
				Location lp = player.getLocation();
				Location location = new Location(lp.getWorld(), lp.getX() + 5 , lp.getY(), lp.getZ());
				player.getWorld().spawnEntity(location, EntityType.ENDERMAN);
				player.sendMessage(ChatColor.AQUA + "[Randomly-Craft EntitySpawnGestion]" + ChatColor.RESET + " Un Enderman a ete spawn !");
			}
			else if(label.equalsIgnoreCase("signal")) {
				Location lp = player.getLocation();
				Location location = new Location(lp.getWorld(), lp.getX(), lp.getY() + 3, lp.getZ());
				player.getWorld().spawnEntity(location, EntityType.ENDER_SIGNAL);
				player.sendMessage(ChatColor.AQUA + "[Randomly-Craft EntitySpawnGestion]" + ChatColor.RESET + " Un Ender_Signal a ete spawn !");
			}
			else if(label.equalsIgnoreCase("wither")) {
				Player player1 = ((Player) sender).getPlayer();
				if(player1.isOp()){
					player.sendMessage(ChatColor.AQUA + "[Randomly-Craft CustomItemGestion]" + ChatColor.RESET + " Vous avez reçus le" + ChatColor.GOLD + ChatColor.ITALIC + " Legendary Wither Gun" + ChatColor.RESET + " !");
					player.getInventory().addItem(getCustomItem(Items.Gun));
					ItemStack is = null;
					is = new ItemStack(Material.LAVA , 1);

				}
				else 
					player.sendMessage(ChatColor.AQUA + "[Randomly-Craft OpGestion]" + ChatColor.RED + " Vous devez être Op !");
			}
			else if(label.equalsIgnoreCase("randomly-craft")) {
				player.sendMessage(ChatColor.AQUA + "[Randomly-Craft TestGestion]" + ChatColor.RESET + " Le plugin fonctionne correctement !");
			}
			else if (cmd.getName().equalsIgnoreCase("fakejoin")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Il faut mettre un pseudo.");
					return true;
				}
				if (args.length == 1) {
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + args[0] + " has joined the game");
					return true;
				}
				if (args.length == 1) {
					if (args[1].contains("fakeleave")) {
						Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + args[0] + " has left the game");
						return true;
					}
					sender.sendMessage(ChatColor.RED + "/fj <username> [leave]");
					return true;
				} else {
					((CommandSender) cmd).sendMessage(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.RESET + " Il faut etre un joueur pour effectuer cette commande");
				}


				return true;

			}
		}
		return false;
	}
}
