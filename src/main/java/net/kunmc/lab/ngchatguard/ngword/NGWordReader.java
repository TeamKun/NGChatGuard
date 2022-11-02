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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import net.kunmc.lab.ngchatguard.NGChatGuard;
import net.kunmc.lab.ngchatguard.Store;
import net.kunmc.lab.ngchatguard.command.ExecuteResult;
import org.bukkit.Bukkit;

public class NGWordReader {

  private NGChatGuard plugin;

  public NGWordReader(NGChatGuard plugin) {
    this.plugin = plugin;
  }

  public ExecuteResult read(boolean showInfo) {
    String resultMessage = "";
    String result = "";
    try {
      // APIからデータをフェッチ
      result = fetchFromAPI();
      // ファイルとして保存
      saveFile(result);
      resultMessage = "[NGChatGuard] APIからのNGワード更新に成功しました";

    } catch (IOException e) {
      try {
        result = readFile(this.plugin.getDataFolder().getAbsoluteFile() + "/ngwords-bk.json");
        resultMessage = "[NGChatGuard] APIからのNGワード取得に失敗したためngword-bk.jsonからロードしました";
      } catch (IOException f) {
        resultMessage = "[NGChatGuard] APIからのNGワード更新に失敗し、ngword-bk.jsonの読み込みにも失敗しました。コンフィグを確認してください。";
        if (showInfo) {
          Bukkit.getLogger().info(resultMessage);
        }
        return new ExecuteResult(resultMessage, false);
      }
    }

    Store.ngWordList = parseFromJsonString(result);
    if (showInfo) {
      Bukkit.getLogger().info(resultMessage);
    }
    return new ExecuteResult(resultMessage, true);
  }

  private String fetchFromAPI() throws IOException {
    String result = "";
    String tmp;
    BufferedReader in;
    URL url = APIUrlBuilder.build();
    HttpURLConnection con = (HttpURLConnection) url.openConnection();

    con.connect();

    in = new BufferedReader(new InputStreamReader(con.getInputStream(),
        StandardCharsets.UTF_8));

    while ((tmp = in.readLine()) != null) {
      result += tmp;
    }

    if (result.equals("{\"error\":\"error\"}")) {
      throw new IOException();
    }

    return result;
  }

  private void saveFile(String text) throws IOException {
    PrintWriter pw = new PrintWriter(
        new BufferedWriter(
            new FileWriter(
                this.plugin.getDataFolder().getAbsoluteFile()
                    + "/ngwords-bk.json")));
    pw.println(text);
    pw.close();
  }

  private String readFile(String fileName) throws IOException {
    String result = "";
    String tmp = "";
    BufferedReader in;
    File file = new File(fileName);
    FileReader fr = new FileReader(file);
    in = new BufferedReader(fr);
    while ((tmp = in.readLine()) != null) {
      result += tmp;
    }

    return result;
  }

  private NGWordList parseFromJsonString(String text) {
    text = text.replace("{\"rowData\":[", "")
        .replace("]}", "")
        .replace("\"", "")
        .replace("],[", "/")
        .replace("[", "")
        .replace("]", "");

    String[] tempData = text.split("/");
    ArrayList<String[]> data = new ArrayList<String[]>();
    for (String s : tempData) {
      data.add(s.split(","));
    }

    return new NGWordList(data);
  }
}
