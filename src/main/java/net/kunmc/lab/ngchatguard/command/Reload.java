package net.kunmc.lab.ngchatguard.command;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.commandlib.CommandContext;
import net.kunmc.lab.ngchatguard.Store;
import org.jetbrains.annotations.NotNull;

public class Reload extends Command {

  public Reload(@NotNull String name) {
    super(name);
  }

  @Override
  protected void execute(@NotNull CommandContext ctx) {
    if (Store.reader.read()) {
      ctx.sendSuccess("NGワードのリロードに成功しました");
    } else {
      ctx.sendFailure("NGワードのリロードに失敗しました");
    }
  }
}
