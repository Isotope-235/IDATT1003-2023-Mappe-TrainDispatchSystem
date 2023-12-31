package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class Exit implements Command {

  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    app.setRunning(false);

    return Optional.empty();
  }

  @Override
  public String identifier() {
    return "exit";
  }

  @Override
  public String description() {
    return "Exit the program";
  }
}
