package edu.ntnu.stud;

import java.util.Optional;

public interface Command {
  Optional<CommandLog> execute(TrainDispatchApp app);
  String identifier();
  String description();
}
