package net.kunmc.lab.ngchatguard;

import net.kunmc.lab.commandlib.CommandLib;
import net.kunmc.lab.ngchatapi.NGWordReader;
import net.kunmc.lab.ngchatguard.command.MainCommand;
import net.kunmc.lab.ngchatguard.command.TestCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NGChatGuard extends JavaPlugin {

  @Override
  public void onEnable() {
    Store.plugin = this;
    Store.config = new Config(this);
    CommandLib.register(this,
        new MainCommand("ngchat"),
        new TestCommand("ngtest")
    );
    Bukkit.getPluginManager().registerEvents(new Guard(), this);
    Bukkit.getPluginManager().registerEvents(new AutoReloader(), this);

    Store.reader = new NGWordReader(Store.plugin.getDataFolder().getAbsoluteFile());
    Bukkit.getLogger().info(
        Store.reader.read(Store.config.APIId.value(), Store.config.APIToken.value()).message());
  }

  @Override
  public void onDisable() {
  }
}
