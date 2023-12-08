package edu.ntnu.stud.commands;

import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

public class AddLog implements CommandLog {
  private final int departure;

  public AddLog(int departure) {
    this.departure = departure;
  }

  @Override
  public void undo(TrainDispatchApp app) {
    app.getRegistry().remove(departure);
  }

  @Override
  public String display() {
      return "added departure " + departure;
  }
}
