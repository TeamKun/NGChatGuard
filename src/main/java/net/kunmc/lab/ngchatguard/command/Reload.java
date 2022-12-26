package net.kunmc.lab.ngchatguard.command;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.commandlib.CommandContext;
import net.kunmc.lab.ngchatapi.ExecuteResult;
import net.kunmc.lab.ngchatguard.Store;
import org.jetbrains.annotations.NotNull;

public class Reload extends Command {

  public Reload(@NotNull String name) {
    super(name);
  }

  @Override
  protected void execute(@NotNull CommandContext ctx) {
    ExecuteResult result = Store.reader.read(Store.config.APIId.value(),
        Store.config.APIToken.value());
    if (result.isSucceed()) {
      ctx.sendSuccess(result.message());
    } else {
      ctx.sendFailure(result.message());
    }
  }
}
