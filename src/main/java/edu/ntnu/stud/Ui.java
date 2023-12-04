package edu.ntnu.stud;

import edu.ntnu.stud.commands.Add;

import java.util.Scanner;

public final class Ui {
  private final Scanner scanner;

  public Ui() {
    this.scanner = new Scanner(System.in);
  }

  public Command promptCommand(Command[] commands) {
    return new Add();
  }

  public void printExitMessage() {

  }
}
