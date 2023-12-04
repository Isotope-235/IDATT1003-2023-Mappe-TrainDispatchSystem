package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class Add implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    return Optional.empty();
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
