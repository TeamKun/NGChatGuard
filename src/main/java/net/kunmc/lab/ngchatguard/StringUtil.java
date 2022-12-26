package net.kunmc.lab.ngchatguard;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Locale;
import net.kunmc.lab.ngchatapi.NGWord;

public class StringUtil {

  /**
   * 全角 => 半角 カタカナ => ひらがな 大文字 => 小文字
   */
  public static String shape(String string) {
    return kanaTohira(Normalizer.normalize(string, Form.NFKC)).toLowerCase(Locale.ROOT);
  }

  private static String kanaTohira(String str) {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      char code = str.charAt(i);
      if ((code >= 0x30a1) && (code <= 0x30f3)) {
        buf.append((char) (code - 0x60));
      } else {
        buf.append(code);
      }
    }

    return buf.toString();
  }

  public static String insertColorCode(String text, ArrayList<NGWord> ngWords) {
    StringBuilder sb = new StringBuilder("§7" + text);
    for (NGWord ngWord : ngWords) {
      String target = sb.toString();
      ArrayList<Integer> indexList = ngWord.indexOf(target);
      int insertCount = 0;
      int ngLength = ngWord.length();
      for (Integer index : indexList) {
        sb.insert(index + insertCount, "§c").insert(index + ngLength + insertCount + 2, "§7");
        insertCount += 4;
      }
    }

    return sb.toString();
  }
}
