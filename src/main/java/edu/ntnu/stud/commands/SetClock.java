package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class SetClock implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var ui = app.getUserInterface();

    var prev = app.getClock();
    var time = ui.promptTime();
    app.setClock(time);

    var log = new SetClockLog(prev);

    return Optional.of(log);
  }

  @Override
  public String identifier() {
    return "clock";
  }

  @Override
  public String description() {
    return "Set the time of the application clock";
  }
}
