package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class FindByLine implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var ui = app.getUserInterface();
    var reg = app.getRegistry();

    var line = ui.promptLine();
    var deps = reg.withLine(line);

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
    return "find-line";
  }

  @Override
  public String description() {
    return "Find departures by line";
  }
}
