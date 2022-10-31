package net.kunmc.lab.ngchatguard.command;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.commandlib.argument.StringArgument.Type;
import net.kunmc.lab.ngchatguard.Store;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends Command {

  public TestCommand(@NotNull String name) {
    super(name);
    this.setPermission(PermissionDefault.TRUE);

    argument(argumentBuilder -> {
      argumentBuilder.stringArgument("text", Type.PHRASE).execute(ctx -> {
        String text = ctx.getParsedArg("text", String.class);
        Store.ngWordList.test(text).sendResultMessage(ctx);
      });
    });
  }
}
