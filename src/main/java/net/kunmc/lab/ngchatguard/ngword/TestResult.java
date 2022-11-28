package net.kunmc.lab.ngchatguard.ngword;

import java.util.ArrayList;
import net.kunmc.lab.commandlib.CommandContext;
import net.kunmc.lab.ngchatguard.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TestResult {

  private String message;
  private boolean isSucceed;
  private ArrayList<NGWord> ngWords;
  private String originalText;

  public TestResult(String text, ArrayList<NGWord> ngWords) {
    this.originalText = text;
    if (ngWords.size() == 0) {
      this.message = "【OK】NGワードは検出されませんでした";
      this.isSucceed = true;
    } else {
      this.message = "【NG】あなたの入力したテキストにはNGワードが含まれています!\n";
      this.message = this.message + StringUtil.insertColorCode(text, ngWords);
      this.isSucceed = false;
    }

    this.ngWords = ngWords;
  }

  public boolean isSucceed() {
    return this.isSucceed;
  }

  public void sendResultMessage(CommandContext ctx) {
    if (this.isSucceed) {
      ctx.sendSuccess(this.message);
    } else {
      ctx.sendFailure(this.message);
    }
  }

  public void sendResultMessage(Player player) {
    if (!this.isSucceed) {
      player.sendMessage("§c" + this.message + "\n/ngtest <text> で事前に確認できます。");
    }
  }

  public void log(Player player) {
    if (!this.isSucceed) {
      String message = "[NG words] " + player.getName() + " send NGWord ";
      for (NGWord ngWord : this.ngWords) {
        message += ngWord.text() + " ";
      }

      message += "\n[NG original] " + this.originalText;
      Bukkit.getLogger().info(message);
    }
  }
}
