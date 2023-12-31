package edu.ntnu.stud;

import edu.ntnu.stud.commands.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {
  private Registry registry;
  private Ui userInterface;
  private ArrayList<CommandLog> commandHistory;
  private Command[] commands;
  private boolean running;
  private LocalTime clock;

  public static void main(String[] args) { // TODO
    TrainDispatchApp.init().run();
  }

  private TrainDispatchApp() {
    setRegistry(new Registry());
    setUserInterface(new Ui());

    setCommandHistory(new ArrayList<>());
    setCommands(new Command[]{
      new ListCommands(),
      new ListDepartures(),
      new FindByNumber(),
      new FindByDest(),
      new FindByLine(),
      new Add(),
      new Remove(),
      new SetTrack(),
      new SetDelay(),
      new SetClock(),

      new Undo(),
      new Tree(),
      new Exit()
    });

    setRunning(false);
    setClock(LocalTime.of(0, 0));
  }

  private static TrainDispatchApp init() {
    return new TrainDispatchApp();
  }

  private void run() {
    registry.add(new Departure(LocalTime.of(12, 30), "F1", "Oslo"));
    registry.add(new Departure(LocalTime.of(12, 35), "F2", "Trondheim"));
    running = true;
    userInterface.printWelcomeMessage();
    userInterface.printCommands(commands);
    while (running) {
      var command = userInterface.promptCommand(commands);
      var maybeLog = command.execute(this);
      maybeLog.ifPresent(log -> {
        commandHistory.add(log);
        userInterface.printDepartureList(registry, clock);
      });
    }
    userInterface.printExitMessage();
  }

  public Registry getRegistry() {
    return registry;
  }

  public void setRegistry(Registry registry) {
    this.registry = registry;
  }

  public Ui getUserInterface() {
    return userInterface;
  }

  public void setUserInterface(Ui userInterface) {
    this.userInterface = userInterface;
  }

  public ArrayList<CommandLog> getCommandHistory() {
    return commandHistory;
  }

  public void setCommandHistory(ArrayList<CommandLog> commandHistory) {
    this.commandHistory = commandHistory;
  }

  public Command[] getCommands() {
    return commands;
  }

  private void setCommands(Command[] commands) {
    this.commands = commands;
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running = running;
  }

  public LocalTime getClock() {
    return clock;
  }

  public void setClock(LocalTime clock) {
    this.clock = clock;
  }

  public Optional<CommandLog> popCommandHistory() {
    if (commandHistory.isEmpty()) {
      return Optional.empty();
    }
    var log = commandHistory.remove(commandHistory.size() - 1);
    return Optional.of(log);
  }
}
