package edu.ntnu.stud.commands;

import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

public class UndoLog implements CommandLog {

  @Override
  public void undo(TrainDispatchApp app) {
    var log = new Undo().execute(app);
  }

  @Override
  public String display() {
    return "undid last";
  }
}
