package net.kunmc.lab.ngchatguard.ngword;

import java.util.ArrayList;

public class NGWordList {

  private ArrayList<NGWord> list = new ArrayList<>();

  public NGWordList(ArrayList<String[]> data) {
    for (String[] strings : data) {
      if (strings.length == 0) {
        continue;
      }
      
      if (strings.length == 1) {
        this.list.add(
            new NGWord(
                strings[0],
                false
            )
        );
        continue;
      }
      this.list.add(
          new NGWord(
              strings[0],
              Boolean.parseBoolean(strings[1])
          )
      );
    }
  }

  public TestResult test(String text) {
    ArrayList<NGWord> result = new ArrayList<>();
    for (NGWord ngWord : this.list) {
      if (ngWord.test(text)) {
        result.add(ngWord);
      }
    }
    return new TestResult(text, result);
  }
}
