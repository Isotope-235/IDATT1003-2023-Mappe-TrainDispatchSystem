package edu.ntnu.stud;

import edu.ntnu.stud.commands.Add;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

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

  public void printDepartureList(Registry registry) {
    var departures = registry.byTime();

    var fields = new ArrayList<String[]>();
    fields.add(new String[] {"Time", "Line", "Destination", "Track", "Delay"});
    Arrays.stream(departures).map(entry -> {
      var dep = entry.departure();
      var delay = dep.getDelay().toString();
      return new String[] {
              dep.getTime().toString(),
              dep.getLine(),
              dep.getDestination(),
              dep.getTrack().map(Object::toString).orElse(""),
              delay.equals("00:00") ? "" : delay
      };
    }).forEach(fields::add);

    var widest = new int[fields.get(0).length];
    for (var i = 0; i < widest.length; i++) {
      for (var field : fields) {
        widest[i] = Math.max(widest[i], field[i].length());
      }
    }

    var table = new StringBuilder();

    var width = Arrays.stream(widest).sum() + (widest.length - 1) * 3 + 4; // margins are 2 + 2 = 4

    // top row
    var topRow = new StringBuilder("┌");
    for (var i = 0; i < widest.length; i++) {
      topRow.append("─".repeat(widest[i] + 2));
      if (i != widest.length - 1) { // don't add a cross after the last column
        topRow.append("┬");
      }
    }
    topRow.append("┐");
    table.append(topRow).append("\n").append("│ ");

    // headers
    var headers = fields.get(0);

    for (var i = 0; i < headers.length; i++) {
      var header = headers[i];
      table.append(header).append(" ".repeat(widest[i] - header.length())).append(" │ ");
    }
    table.append("\n");

    // cross row
    var crossRow = topRow.toString().replace("┌", "├").replace("┐", "┤").replace("┬", "┼");
    table.append(crossRow).append("\n");

    // departures
    for (var i = 1; i < fields.size(); i++) {
      var dep = fields.get(i);
      var row = new StringBuilder("│ ");
      for (var j = 0; j < dep.length; j++) {
        var field = dep[j];
        row.append(field).append(" ".repeat(widest[j] - field.length())).append(" │ ");
      }
      table.append(row).append("\n");
    }

    // bottom row
    var bottomRow = topRow.toString().replace("┌", "└").replace("┐", "┘").replace("┬", "┴");
    table.append(bottomRow).append("\n");
    System.out.println(table);
  }

  public void printExitMessage() {
    System.out.println("Exiting...");
  }

  public void printWelcomeMessage() {
    System.out.println("Welcome to the train dispatch system.");
  }

  public LocalTime promptTime() {
    while (true) {
      System.out.println("Enter a time (format: 'HH:MM'):");
      var time = scanner.nextLine();
      try {
        return LocalTime.parse(time);
      } catch (Exception e) {
        System.out.println("invalid time: '" + time + "'");
      }
    }
  }

  public Optional<Integer> promptDepartureNumber(Set<Integer> taken) {
    var prompt = "Enter departure number (optional):";
    var name = "departure number";

    var given = promptOptionalInt(prompt, name);
    if (given.isEmpty()) {
      return given;
    }
    while (taken.contains(given.get())) {
      System.out.println("Departure number already in use");
      given = promptOptionalInt(prompt, name);
    }
    return given;
  }

  public String promptLine() {
    return promptNonBlank("Enter a rail line:", "dine");
  }

  public String promptDestination() {
    return promptNonBlank("Enter destination:", "destination");
  }

  public Optional<Integer> promptTrack() {
    return promptOptionalInt("Enter track number (optional):", "track");
  }

  public Optional<Integer> promptOptionalInt(String prompt, String propertyName) {
    while (true) {
      System.out.println(prompt);
      var input = scanner.nextLine();
      if (input.isBlank()) {
        return Optional.empty();
      } else {
        try {
          return Optional.of(Integer.parseInt(input));
        } catch (Exception e) {
          System.out.println("invalid " + propertyName + ": '" + input + "'");
        }
      }
    }
  }

  private String promptNonBlank(String prompt, String propertyName) {
    while (true) {
      System.out.println(prompt);
      var input = scanner.nextLine();
      if (input.isBlank()) {
        System.out.println(propertyName + " cannot be blank");
      } else {
        return input;
      }
    }
  }
}
