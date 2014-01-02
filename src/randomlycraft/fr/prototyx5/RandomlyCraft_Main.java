package randomlycraft.fr.prototyx5;

/*
 * Copyright 2014-2015 Dylan Benincasa. All rights reserved.
 */

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class RandomlyCraft_Main extends JavaPlugin implements Listener{
	public static RandomlyCraft_Main plugin;
	private Stacker playerListener;

	public Logger log = Logger.getLogger("Randomly-Craft");

	public void onEnable() {

		new Stacker(this);
		this.playerListener = new Stacker(this);
		log.info("[Randomly-Craft] Les plugins on bien ete charger et demarre !");

		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.playerListener, this);

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e1) {
			// Failed to submit the stats :-(
		}
	}

	public void onDisable() {

		log.info("[Randomly-Craft] Les plugins on bien ete arrete !");

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR)) return;
		if(!(e.getItem().getType() == Material.WATER)) return;
		for(@SuppressWarnings("unused") Player player : Bukkit.getOnlinePlayers()) {
		}
		WitherSkull w = e.getPlayer().launchProjectile(WitherSkull.class);
		w.getFireTicks();
		w.getMaxFireTicks();
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof WitherSkull) {
			WitherSkull w = (WitherSkull) e.getDamager();
			if (w.getShooter() instanceof Player) {
				Player shooter = (Player) w.getShooter();
				if(shooter.getItemInHand().getType() == Material.WATER);
				e.setDamage(200);
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		Player player = (Player) sender;


		if(sender instanceof Player) {
			ItemStack item = new ItemStack(Material.WATER, 1);

			if(label.equalsIgnoreCase("ender")) {
				Location lp = player.getLocation();
				Location location = new Location(lp.getWorld(), lp.getX() + 5 , lp.getY(), lp.getZ());
				player.getWorld().spawnEntity(location, EntityType.ENDERMAN);
				player.sendMessage(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.RESET + " Un Enderman a ete spawn !");
			}
			else if(label.equalsIgnoreCase("signal")) {
				Location lp = player.getLocation();
				Location location = new Location(lp.getWorld(), lp.getX(), lp.getY(), lp.getZ());
				player.getWorld().spawnEntity(location, EntityType.ENDER_SIGNAL);
				player.sendMessage(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.RESET + " Un Ender_Signal a ete spawn !");
			}
			else if(label.equalsIgnoreCase("wither")) {
				Player player1 = ((Player) sender).getPlayer();
				if(player1.isOp()){
					player.sendMessage(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.RESET + " Vous avez reçus le" + ChatColor.GOLD + ChatColor.ITALIC + " Légendary_Wither_Gun !");
					player.getInventory().addItem(item);
				}
				else 
					player.sendMessage(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.RESET + " Vous devez être Op !");
			}
			else if(label.equalsIgnoreCase("randomly-craft")) {
				player.sendMessage(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.RESET + " Le plugin fonctionne correctement !");
			}
			if (cmd.getName().equalsIgnoreCase("fakejoin")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Please specify a name.");
					return true;
				}
				if (args.length == 1) {
					Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + args[0] + " has joined the game");
					return true;
				}
				if (args.length == 2) {
					if (args[1].contains("leave")) {
						Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + args[0] + " has left the game");
						return true;
					}
					sender.sendMessage(ChatColor.RED + "/fj <username> [leave]");
					return true;
				}

			}

			if (cmd.getName().equalsIgnoreCase("talkas")) {
				if (args.length <= 0) {
					sender.sendMessage(ChatColor.RED + "/ta <username> <message>");
					return true;
				}
				if (args.length == 1) {
					sender.sendMessage(ChatColor.RED + "You need a message to send!");
					return true;
				}
				if (args.length >= 2) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						if (i != 0) {
							sb.append(" ");
							sb.append(args[i]);
						}
					}

					String stringToSend = sb.toString();
					Bukkit.getServer().broadcastMessage("<" + args[0] + ">" + stringToSend);
					return true;
				}
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Usable commands: /fj, or /ta");
			}
		} else {
			sender.sendMessage(ChatColor.AQUA + "[Randomly-Craft]" + ChatColor.RESET + " Il faut etre un joueur pour effectuer cette commande");
		}


		return true;
	}
}
