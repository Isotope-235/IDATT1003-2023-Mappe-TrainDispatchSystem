package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class FindByDest implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var ui = app.getUserInterface();
    var reg = app.getRegistry();

    var dest = ui.promptDestination();
    var deps = reg.withDestination(dest);

    for (var d : deps) {
        ui.printDeparture(d.number(), d.departure());
    }

    if (deps.length == 0) {
        ui.printNoMatches();
    }

    return Optional.empty();
  }

  @Override
  public String identifier() {
    return "find-dest";
  }

  @Override
  public String description() {
    return "Find departures with the given destination";
  }
}
