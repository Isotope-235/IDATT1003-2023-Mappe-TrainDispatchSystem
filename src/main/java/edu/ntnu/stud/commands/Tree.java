package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class Tree implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {

    var ui = app.getUserInterface();

    ui.printCommandLog(app.getCommandHistory());

    return Optional.empty();
  }

  @Override
  public String identifier() {
    return "log";
  }

  @Override
  public String description() {
    return "Show the log of undoable commands that have modified the registry";
  }
}
