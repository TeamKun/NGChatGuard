package net.kunmc.lab.ngchatguard.command;

import net.kunmc.lab.commandlib.Command;
import net.kunmc.lab.configlib.ConfigCommandBuilder;
import net.kunmc.lab.ngchatguard.Store;
import org.jetbrains.annotations.NotNull;

public class MainCommand extends Command {

  public MainCommand(@NotNull String name) {
    super(name);
    addChildren(new Reload("reload"), new ConfigCommandBuilder(Store.config).build());
  }
}
