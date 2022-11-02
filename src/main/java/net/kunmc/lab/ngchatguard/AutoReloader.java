package net.kunmc.lab.ngchatguard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AutoReloader implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (player.getName().equals(Store.config.reloadTriggerPlayer.value())) {
      Store.reader.read(true);
    }
  }
}
