package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.Departure;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class Add implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var registry = app.getRegistry();
    var ui = app.getUserInterface();
    var number = ui.promptOptionalDepartureNumber(registry.numbersInUse());
    var time = ui.promptTime();
    var line = ui.promptLine();
    var destination = ui.promptDestination();
    var track = ui.promptTrack();

    var departure = new Departure(time, line, destination);
    track.ifPresent(departure::setTrack);

    if (number.isPresent()) {
      registry.addWithNumber(number.get(), departure);
    } else {
      number = Optional.of(registry.add(departure));
    } // number guaranteed to be present after this

    return Optional.of(new AddLog(number.get()));
  }

  @Override
  public String identifier() {
    return "add";
  }

  @Override
  public String description() {
    return "Add a new departure to the registry";
  }
}