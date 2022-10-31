package net.kunmc.lab.ngchatguard;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kunmc.lab.ngchatguard.ngword.TestResult;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Guard implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onAsyncChat(AsyncChatEvent event) {

    if (Store.ngWordList == null) {
      if (Store.config.shouldAlertInvalid.value()) {
        Bukkit.getLogger().info(
            "[NGChatGuard] NGワードがロードされていないため無効化されています。この警告を消す場合はコンフィグのshouldAlertInvalidをfalseにしてください");
      }
    }
    Player player = event.getPlayer();
    String message = ((TextComponent) event.message()).content();
    TestResult result = Store.ngWordList.test(message);
    if (!result.isSucceed()) {
      result.sendResultMessage(player);
      result.log(player);
      event.setCancelled(true);
    }
  }
}
