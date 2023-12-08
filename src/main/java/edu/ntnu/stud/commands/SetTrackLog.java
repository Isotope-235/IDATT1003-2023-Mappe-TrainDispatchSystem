package edu.ntnu.stud.commands;

import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

public class SetTrackLog implements CommandLog {
  private final int departure;
  private final int previousTrack;

  public SetTrackLog(int departure, int previousTrack) {
    this.departure = departure;
    this.previousTrack = previousTrack;
  }

  public SetTrackLog(int departure) {
    this.departure = departure;
    this.previousTrack = -1;
  }

  @Override
  public void undo(TrainDispatchApp app) {
    var departure = app.getRegistry().getDeparture(this.departure).get(); // this is safe as long as
    // the order of undos is correct
    try { departure.setTrack(previousTrack); }
    catch (IllegalArgumentException e) { departure.resetTrack(); }
  }

  @Override
  public String display() {
      return "set track of departure " + departure;
  }
}
