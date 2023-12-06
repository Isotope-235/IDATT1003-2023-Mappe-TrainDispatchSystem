package edu.ntnu.stud.commands;

import edu.ntnu.stud.*;

import java.util.Optional;

public class ListCommands implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    app.getUserInterface().printCommands(app.getCommands());

    return Optional.empty();
  }

  @Override
  public String identifier() {
    return "help";
  }

  @Override
  public String description() {
    return "List all available commands";
  }
}