package net.kunmc.lab.ngchatguard;

import io.papermc.paper.event.player.AsyncChatEvent;
import java.util.List;
import java.util.UUID;
import net.kunmc.lab.ngchatapi.NGWord;
import net.kunmc.lab.ngchatapi.NGWordStore;
import net.kunmc.lab.ngchatapi.TestResult;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Guard implements Listener {

  @EventHandler(ignoreCancelled = true)
  public void onAsyncChat(AsyncChatEvent event) {
    if (isBypassPlayer(event.getPlayer().getUniqueId())) {
      return;
    }
    if (NGWordStore.ngWordList == null) {
      if (Store.config.shouldAlertInvalid.value()) {
        Bukkit.getLogger().info(
            "[NGChatGuard] NGワードがロードされていないため無効化されています。この警告を消す場合はコンフィグのshouldAlertInvalidをfalseにしてください");
      }
      return;
    }
    Player player = event.getPlayer();
    String message = ((TextComponent) event.message()).content();
    TestResult result = NGWordStore.ngWordList.test(message);
    if (!result.isSucceed()) {
      sendResultMessage(player, result);
      log(player, result);
      event.setCancelled(true);
    }
  }

  /**
   * tell,w,msgコマンド
   */
  @EventHandler(ignoreCancelled = true)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

    if (isBypassPlayer(event.getPlayer().getUniqueId())) {
      return;
    }

    String[] commands = event.getMessage().split(" ");
    /** コマンド */
    String command = commands[0];

    // tell,w,msgコマンドであるか判定
    if (!command.equalsIgnoreCase("/tell") &&
        !command.equalsIgnoreCase("/w") &&
        !command.equalsIgnoreCase("/msg") &&
        !command.equalsIgnoreCase("/tellraw")
    ) {
      return;
    }

    // 引数チェック
    if (commands.length < 3) {
      return;
    }

    /** チャット */
    String text = "";

    Player player = event.getPlayer();

    for (int i = 2; i < commands.length; i++) {
      text += commands[i];
    }

    TestResult result = NGWordStore.ngWordList.test(text);
    if (!result.isSucceed()) {
      sendResultMessage(player, result);
      log(player, result);
      event.setCancelled(true);
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onSignChange(SignChangeEvent event) {

    if (isBypassPlayer(event.getPlayer().getUniqueId())) {
      return;
    }
    /** 行ごとの入力内容 */
    List<Component> lines = event.lines();

    StringBuilder text = new StringBuilder();
    // １行づつ処理
    for (Component line : lines) {
      TextComponent textComponent = (TextComponent) line;
      text.append(textComponent.content());
    }

    Player player = event.getPlayer();

    TestResult result = NGWordStore.ngWordList.test(text.toString());
    if (!result.isSucceed()) {
      sendResultMessage(player, result);
      log(player, result);
      event.setCancelled(true);
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    if (isBypassPlayer(event.getWhoClicked().getUniqueId())) {
      return;
    }

    // インベントリが金床か判定
    Inventory inventory = event.getInventory();
    if (!(inventory instanceof AnvilInventory)) {
      return;
    }

    // インベントリスロットの位置を判定
    if (event.getRawSlot() != 2) {
      return;
    }

    // アイテムメタを取得
    ItemStack item = event.getCurrentItem();
    ItemMeta itemMeta = item.getItemMeta();

    // アイテムを掴んだときか判定
    if (itemMeta == null) {
      return;
    }

    // 変更後の名前を取得
    TextComponent renameText = (TextComponent) itemMeta.displayName();

    // 名づけされたアイテムか判定
    if (renameText == null) {
      return;
    }

    Player player = (Player) event.getWhoClicked();
    TestResult result = NGWordStore.ngWordList.test(renameText.content());

    if (!result.isSucceed()) {
      sendResultMessage(player, result);
      log(player, result);
      event.setCancelled(true);
    }
  }

  private void sendResultMessage(Player player, TestResult result) {
    if (!result.isSucceed()) {
      player.sendMessage("§c" + result.resultMessage() + "\n/ngtest <text> で事前に確認できます。");
    }
  }

  private void log(Player player, TestResult result) {
    if (!result.isSucceed()) {
      String message = "[NG words] " + player.getName() + " send NGWord ";
      for (NGWord ngWord : result.ngWords()) {
        message += ngWord.text() + " ";
      }

      message += "\n[NG original] " + result.originalText();
      Bukkit.getLogger().info(message);
    }
  }

  private boolean isBypassPlayer(UUID uuid) {
    return Store.config.bypassPlayers.contains(uuid);
  }
}
