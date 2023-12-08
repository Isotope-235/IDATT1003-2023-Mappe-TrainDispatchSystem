package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class FindByNumber implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var ui = app.getUserInterface();
    var reg = app.getRegistry();

    var depNum = ui.promptDepartureNumber(reg.numbersInUse());
    var dep = reg.getDeparture(depNum).get(); // ui has checked

    ui.printDeparture(depNum, dep);

    return Optional.empty();
  }

  @Override
  public String identifier() {
    return "find-num";
  }

  @Override
  public String description() {
    return "Find a departure by number";
  }
}
