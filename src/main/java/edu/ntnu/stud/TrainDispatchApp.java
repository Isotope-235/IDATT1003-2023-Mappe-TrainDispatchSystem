package edu.ntnu.stud;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {
  private Registry registry;
  private Ui userInterface;

  public static void main(String[] args) { // TODO
    TrainDispatchApp.init().run();
  }

  private TrainDispatchApp() {
    this.registry = new Registry();
    this.userInterface = new Ui();
  }

  private static TrainDispatchApp init() {
    return new TrainDispatchApp();
  }

  private void run() {

  }
}
