package edu.ntnu.stud;

import edu.ntnu.stud.commands.Add;

import java.util.Scanner;

public final class Ui {
  private final Scanner scanner;

  public Ui() {
    this.scanner = new Scanner(System.in);
  }

  public Command promptCommand(Command[] commands) {
    while (true) {
      System.out.println("Enter any of the following commands:");
      for (var command : commands) {
        System.out.println(command.identifier() + ": " + command.description());
      }
      var prompt = scanner.nextLine();
      for (var command : commands) {
        if (command.identifier().equals(prompt)) {
          return command;
        }
      }
      System.out.println("Invalid command: " + prompt);
    }
  }

  public void printExitMessage() {
    System.out.println("Exiting...");
  }

  public void printWelcomeMessage() {
    System.out.println("Welcome to the train dispatch system.");
  }
}
