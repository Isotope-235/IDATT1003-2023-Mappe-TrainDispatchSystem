package edu.ntnu.stud;

import edu.ntnu.stud.commands.Add;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {
  private Registry registry;
  private Ui userInterface;
  private ArrayList<CommandLog> commandHistory;
  private Command[] commands;
  private boolean running;

  public static void main(String[] args) { // TODO
    TrainDispatchApp.init().run();
  }

  private TrainDispatchApp() {
    this.registry = new Registry();
    this.userInterface = new Ui();
    this.commandHistory = new ArrayList<>();

    this.commands = new Command[] {
      new Add()
    };

    this.running = false;
  }

  private static TrainDispatchApp init() {
    return new TrainDispatchApp();
  }

  private void run() {
    registry.add(5, new Departure(LocalTime.of(12, 30), "F1", "Oslo"));
    registry.add(2, new Departure(LocalTime.of(12, 35), "F2", "Trondheim"));
    running = true;
    userInterface.printWelcomeMessage();
    while (running) {
      var command = userInterface.promptCommand(commands);
      var maybeLog = command.execute(this);
      maybeLog.ifPresent(commandHistory::add);
      userInterface.printDepartureList(registry);
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

  public void setCommands(Command[] commands) {
    this.commands = commands;
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running = running;
  }
}
