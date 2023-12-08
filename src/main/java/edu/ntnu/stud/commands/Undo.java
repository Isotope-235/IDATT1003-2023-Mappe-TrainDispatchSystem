package edu.ntnu.stud.commands;

import edu.ntnu.stud.Command;
import edu.ntnu.stud.CommandLog;
import edu.ntnu.stud.TrainDispatchApp;

import java.util.Optional;

public class Undo implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    var last = app.popCommandHistory();
    if (last.isEmpty()) {
      app.getUserInterface().printNoUndoFeedback();
      return Optional.empty();
    } else {
      last.get().undo(app);
      return Optional.of(new UndoLog());
    }
  }

  @Override
  public String identifier() {
    return "undo";
  }

  @Override
  public String description() {
    return "Undo the last command that modified the registry";
  }
}
