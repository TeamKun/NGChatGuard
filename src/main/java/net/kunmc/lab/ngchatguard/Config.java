package net.kunmc.lab.ngchatguard;

import net.kunmc.lab.configlib.BaseConfig;
import net.kunmc.lab.configlib.value.BooleanValue;
import net.kunmc.lab.configlib.value.StringValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Config extends BaseConfig {

  public final StringValue APIId = new StringValue("").writableByCommand(false).listable(false);
  public final StringValue APIToken = new StringValue("").writableByCommand(false).listable(false);
  public final StringValue reloadTriggerPlayer = new StringValue("roadhog_kun");
  public final BooleanValue shouldAlertInvalid = new BooleanValue(true);

  public Config(@NotNull Plugin plugin) {
    super(plugin);
    loadConfig();
  }
}
