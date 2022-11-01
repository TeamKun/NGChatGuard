package net.kunmc.lab.ngchatguard;

import java.net.MalformedURLException;
import net.kunmc.lab.commandlib.CommandLib;
import net.kunmc.lab.ngchatguard.command.MainCommand;
import net.kunmc.lab.ngchatguard.command.TestCommand;
import net.kunmc.lab.ngchatguard.ngword.NGWordReader;
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

    try {
      Store.reader = new NGWordReader(Store.plugin);
      Store.reader.read();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onDisable() {
  }
}
