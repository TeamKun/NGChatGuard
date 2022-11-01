package net.kunmc.lab.ngchatguard.ngword;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import net.kunmc.lab.ngchatguard.NGChatGuard;
import net.kunmc.lab.ngchatguard.Store;
import org.bukkit.Bukkit;

public class NGWordReader {

  private URL url;
  private NGChatGuard plugin;

  public NGWordReader(NGChatGuard plugin) throws MalformedURLException {
    this.plugin = plugin;
    this.url = APIUrlBuilder.build();
  }

  public boolean read() {
    String result = "";
    String tmp = "";
    BufferedReader in;
    try {
      HttpURLConnection con = (HttpURLConnection) this.url.openConnection();

      con.connect();

      in = new BufferedReader(new InputStreamReader(con.getInputStream(),
          StandardCharsets.UTF_8));

      // ファイルとして保存
      PrintWriter pw = new PrintWriter(
          new BufferedWriter(
              new FileWriter(
                  this.plugin.getDataFolder().getAbsoluteFile()
                      + "/ngwords-bk.json")));

      while ((tmp = in.readLine()) != null) {
        result += tmp;
        pw.println(tmp);
      }

      pw.close();
      Bukkit.getLogger().info("[NGChatGuard] APIからのNGワード更新に成功しました");
    } catch (IOException e) {
      try {
        File file = new File(this.plugin.getDataFolder().getAbsoluteFile() + "/ngwords-bk.json");
        FileReader fr = new FileReader(file);
        in = new BufferedReader(fr);
        while ((tmp = in.readLine()) != null) {
          result += tmp;
        }
        Bukkit.getLogger().info("[NGChatGuard] APIからのNGワード取得に失敗したためngword-bk.jsonからロードしました");
      } catch (IOException f) {
        Bukkit.getLogger()
            .info("[NGChatGuard] APIからのNGワード更新に失敗し、ngword-bk.jsonの読み込みにも失敗しました。コンフィグを確認してください。");
        return false;
      }
    }

    result = result.replace("{\"rowData\":[", "")
        .replace("]}", "")
        .replace("\"", "")
        .replace("],[", "/")
        .replace("[", "")
        .replace("]", "");

    String[] tempData = result.split("/");
    ArrayList<String[]> data = new ArrayList<String[]>();
    for (String s : tempData) {
      data.add(s.split(","));
    }
    Store.ngWordList = new NGWordList(data);
    return true;
  }
}
