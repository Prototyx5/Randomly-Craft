package randomlycraft.fr.prototyx5;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginAndQuit
  implements Listener
{
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    Player player = event.getPlayer();

    event.setJoinMessage(RandomlyCraft_Main.JoinMessage.replaceAll("&([a-f0-9])", "§$1").replace("{PLAYER}", player.getName()));
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();

    event.setQuitMessage(RandomlyCraft_Main.QuitMessage.replaceAll("&([a-f0-9])", "§$1").replace("{PLAYER}", player.getName()));
  }
}