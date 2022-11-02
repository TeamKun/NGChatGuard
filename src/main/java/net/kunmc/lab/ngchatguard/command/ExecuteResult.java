package net.kunmc.lab.ngchatguard.command;

public class ExecuteResult {

  private String message;
  private boolean isSucceed;

  public ExecuteResult(String message, boolean isSucceed) {
    this.message = message;
    this.isSucceed = isSucceed;
  }

  public String message() {
    return message;
  }

  public boolean isSucceed() {
    return isSucceed;
  }
}
