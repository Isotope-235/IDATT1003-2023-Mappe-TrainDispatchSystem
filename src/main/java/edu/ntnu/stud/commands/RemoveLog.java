package edu.ntnu.stud.commands;

import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.Registry;
import edu.ntnu.stud.TrainDispatchApp;

public class RemoveLog implements CommandLog {
  private final Registry.Entry entry;

  public RemoveLog(Registry.Entry departure) {
    this.entry = departure;
  }

  @Override
  public void undo(TrainDispatchApp app) {
    app.getRegistry().addWithNumber(entry.number(), entry.departure());
  }

  @Override
  public String display() {
      return "removed departure " + entry.number();
  }
}
