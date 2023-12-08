package edu.ntnu.stud.commands;

import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.time.LocalTime;

public class SetDelayLog implements CommandLog {
  private final int departure;
  private final LocalTime previousDelay;

  public SetDelayLog(int departure, LocalTime previousDelay) {
    this.departure = departure;
    this.previousDelay = previousDelay;
  }

  @Override
  public void undo(TrainDispatchApp app) {
    var departure = app.getRegistry().getDeparture(this.departure).get();
    // assuming the order of undos is correct...
    departure.setDelay(previousDelay);
  }
}
