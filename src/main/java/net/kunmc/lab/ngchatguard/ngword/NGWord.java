package net.kunmc.lab.ngchatguard.ngword;

import java.util.ArrayList;
import net.kunmc.lab.ngchatguard.StringUtil;

public class NGWord {

  private String word;
  private boolean isStrict;

  public NGWord(String word, boolean isStrict) {
    if (!isStrict) {
      word = StringUtil.shape(word);
    }
    this.word = word;
    this.isStrict = isStrict;
  }

  public String text() {
    return this.word;
  }

  public boolean test(String text) {
    if (!this.isStrict) {
      text = StringUtil.shape(text);
    }

    return text.contains(this.word);
  }

  public int length() {
    return this.word.length();
  }

  public ArrayList<Integer> indexOf(String text) {
    if (!this.isStrict) {
      text = StringUtil.shape(text);
    }
    ArrayList<Integer> result = new ArrayList<>();
    int i = text.indexOf(this.word);
    if (i >= 0) {
      result.add(i);
    }
    while (i >= 0) {
      i = text.indexOf(this.word, i + 1);
      if (i >= 0) {
        result.add(i);
      }
    }

    return result;
  }
}
