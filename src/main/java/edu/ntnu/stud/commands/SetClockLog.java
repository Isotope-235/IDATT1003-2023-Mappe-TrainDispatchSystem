package edu.ntnu.stud.commands;

import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.time.LocalTime;

public class SetClockLog implements CommandLog {
  private final LocalTime prev;

  public SetClockLog(LocalTime prev) {
      this.prev = prev;
  }

  @Override
  public void undo(TrainDispatchApp app) {
    app.setClock(prev);
  }
}
