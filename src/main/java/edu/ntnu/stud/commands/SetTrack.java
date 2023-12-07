package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class SetTrack implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var ui = app.getUserInterface();
    var reg = app.getRegistry();
    var depNum = ui.promptDepartureNumber(reg.numbersInUse());
    var newTrack = ui.promptTrack();

    // ui promises to check this for us
    var departure = reg.getDeparture(depNum).get();
    // if it throws, ui isn't working properly

    var previousTrack = departure.getTrack();

    if (newTrack.isEmpty()) {
      departure.resetTrack();
    } else {
      departure.setTrack(newTrack.get());
    }

    var log = previousTrack.map(t -> new SetTrackLog(depNum, t)).orElseGet(() -> new SetTrackLog(depNum));
    return Optional.of(log);
  }

  @Override
  public String identifier() {
    return "track";
  }

  @Override
  public String description() {
    return "Set the track for a departure, or remove it if no track is specified";
  }
}
