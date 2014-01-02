package randomlycraft.fr.prototyx5;

/*
 * ©Copyright 2014-2015 Dylan Benincasa. All rights reserved.
 */

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Stacker
implements Listener
{
	public RandomlyCraft_Main plugin;
	protected FileConfiguration config;

	public Stacker(RandomlyCraft_Main Instance)
	{
		this.plugin = Instance;
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if ((event.getRightClicked() instanceof Player)) {
			Player player = event.getPlayer();
			if ((player.isOp())) {
				if ((!mplEjectPassenger(player, event.getRightClicked())) && (playerCanRide(player))) {
					Player vehicle = getVehicle(player);

					if (vehicle == null) {
						vehicle = (Player)event.getRightClicked();
						Player mpl = getRootVehicle(vehicle);

						if (mpl.hasPermission("stacker.stack")) {
							getLastPassenger(player).setPassenger(vehicle);
							player.sendMessage(ChatColor.GREEN + "Vous avez stacker " + getLastPassenger(player).getName() + "!");
							getLastPassenger(player).sendMessage(ChatColor.BLUE + player.getName() + " vous a stacker");
							alertPlayers(player, mpl, "message");
						}
					}
					else {
						vehicle.eject();
					}
				}
			}
		}
	}

	private boolean playerCanRide(Player player)
	{
		return (player.hasPermission("stacker.stack")) && (player.getPassenger() == null);
	}
	private boolean mplEjectPassenger(Player mpl, Entity passenger) {
		if ((mpl.hasPermission("stacker.eject")) && 
				(passenger.equals(mpl.getPassenger()))) {
			mpl.sendMessage(ChatColor.GREEN + "Vous avez éjecter votre passagé !");
			alertPlayers((Player)passenger, mpl, "");

			return true;
		}

		return false;
	}
	private Player getRootVehicle(Player vehicle) {
		while (getVehicle(vehicle) != null) {
			vehicle = getVehicle(vehicle);
		}

		return vehicle;
	}
	private Player getLastPassenger(Player vehicle) {
		while ((vehicle.getPassenger() != null) && ((vehicle.getPassenger() instanceof Player))) {
			vehicle = (Player)vehicle.getPassenger();
		}

		return vehicle;
	}
	private Player getVehicle(Player player) {
		for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
			Entity passenger = onlinePlayer.getPassenger();

			if (((passenger instanceof Player)) && (passenger.getEntityId() == player.getEntityId())) {
				return onlinePlayer;
			}
		}

		return null;
	}
	private void alertPlayers(Player player, Player mpl, String key) {
		String message = this.config.getString(key);

		if (!message.isEmpty())
			this.plugin.getServer().broadcastMessage(message.replace("<player>", player.getName()).replace("<mpl>", mpl.getName()));
	}
}