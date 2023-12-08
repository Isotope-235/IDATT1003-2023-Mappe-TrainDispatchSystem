package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class SetDelay implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var ui = app.getUserInterface();
    var reg = app.getRegistry();

    var depNum = ui.promptDepartureNumber(reg.numbersInUse());
    var delay = ui.promptTime();

    var departure = reg.getDeparture(depNum).get(); // ui has checked

    var previousDelay = departure.getDelay();
    departure.setDelay(delay);

    return Optional.of(new SetDelayLog(depNum, previousDelay));
  }

  @Override
  public String identifier() {
    return "delay";
  }

  @Override
  public String description() {
    return "Set the delay for a departure";
  }
}
