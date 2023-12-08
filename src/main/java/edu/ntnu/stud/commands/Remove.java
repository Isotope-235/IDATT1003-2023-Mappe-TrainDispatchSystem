package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.Registry;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class Remove implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var ui = app.getUserInterface();
    var reg = app.getRegistry();

    var depNum = ui.promptDepartureNumber(reg.numbersInUse());

    var dep = reg.remove(depNum);

    var entry = new Registry.Entry(depNum, dep.get()); // TODO fix all of these

    return Optional.of(new RemoveLog(entry));
  }

  @Override
  public String identifier() {
    return "remove";
  }

  @Override
  public String description() {
    return "Remove a departure from the registry";
  }
}
