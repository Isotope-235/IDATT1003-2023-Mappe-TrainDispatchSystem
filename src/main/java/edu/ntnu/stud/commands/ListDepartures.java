package edu.ntnu.stud.commands;

import edu.ntnu.stud.*;

import java.util.Optional;

public class ListDepartures implements Command {
  @Override
  public Optional<CommandLog> execute(TrainDispatchApp app) {
    app.getUserInterface().printDepartureList(app.getRegistry(), app.getClock());

    return Optional.empty();
  }

  @Override
  public String identifier() {
    return "list";
  }

  @Override
  public String description() {
    return "List all departures currently entered in the registry";
  }
}