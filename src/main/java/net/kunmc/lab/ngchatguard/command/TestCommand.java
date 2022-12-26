package net.kunmc.lab.ngchatguard.command;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.commandlib.CommandContext;
import net.kunmc.lab.commandlib.argument.StringArgument.Type;
import net.kunmc.lab.ngchatapi.NGWordStore;
import net.kunmc.lab.ngchatapi.TestResult;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends Command {

  public TestCommand(@NotNull String name) {
    super(name);
    this.setPermission(PermissionDefault.TRUE);

    argument(argumentBuilder -> {
      argumentBuilder.stringArgument("text", Type.PHRASE).execute(ctx -> {
        String text = ctx.getParsedArg("text", String.class);
//        Store.ngWordList.test(text).sendResultMessage(ctx);
        sendResultMessage(ctx, NGWordStore.ngWordList.test(text));
      });
    });
  }

  private void sendResultMessage(CommandContext ctx, TestResult result) {
    if (result.isSucceed()) {
      ctx.sendSuccess(result.resultMessage());
    } else {
      ctx.sendFailure(result.resultMessage());
    }
  }
}
