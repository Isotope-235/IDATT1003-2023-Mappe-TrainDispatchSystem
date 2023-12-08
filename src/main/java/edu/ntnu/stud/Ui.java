package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.*;

public final class Ui {
  private final Scanner scanner;

  public Ui() {
    this.scanner = new Scanner(System.in);
  }

  public void printCommands(Command[] commands) {
    System.out.println("Available commands:\n");
    for (var command : commands) {
      System.out.println("\t'" + command.identifier() + "': " + command.description());
    }
  }

  public Command promptCommand(Command[] commands) {
    while (true) {
      var prompt = scanner.nextLine();
      for (var command : commands) {
        if (command.identifier().equals(prompt)) {
          return command;
        }
      }
      System.out.println("Invalid command: " + prompt);
    }
  }

  public void printDeparture(int number, Departure departure) {
    var track = departure.getTrack();
    var delay = departure.getDelay().toString();
    System.out.println("\nDeparture " + number + ":" +
      "\n\tTime: " + departure.getTime() +
      "\n\tLine: " + departure.getLine() +
      "\n\tDestination: " + departure.getDestination() +
      "\n\t" + (track.map(integer -> "Track: " + integer).orElse("No track set")) +
      "\n\t" + (delay.equals("00:00") ? "No delay" : "Delay: " + delay)
    );
  }

  public void printDepartureList(Registry registry, LocalTime time) {
    var departures = registry.afterOrAt(time);

    var fields = new ArrayList<String[]>();
    fields.add(new String[] {"Time", "Line", "Destination", "Track", "Delay", "#"});
    Arrays.stream(departures).map(entry -> {
      var dep = entry.departure();
      var delay = dep.getDelay().toString();
      var noDelay = delay.equals("00:00");
      return new String[] {
              dep.getTime().toString() + (noDelay ? "" : " (" + dep.getRealTime() + ")" ),
              dep.getLine(),
              dep.getDestination(),
              dep.getTrack().map(Object::toString).orElse(""),
              delay.equals("00:00") ? "" : delay,
              Integer.toString(entry.number())
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
    System.out.println("\nCurrent time: " + time);
    System.out.println(table);
  }

  public void printExitMessage() {
    System.out.println("Exiting...");
  }

  public void printWelcomeMessage() {
    System.out.println("\nWelcome to the train dispatch system.\n");
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

  public Optional<Integer> promptOptionalDepartureNumber(Set<Integer> taken) {
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

  public int promptDepartureNumber(Set<Integer> departures) {
    while (true) {
      var given = promptOptionalInt("Enter departure number:", "departure number");
      if (given.isPresent()) {
        var number = given.get();
        if (departures.contains(number)) {
          return number;
        }
        System.out.println("Departure " + given.get() + " does not exist");
        continue;
      }
      System.out.println("Departure number is required");
    }
  }

  public String promptLine() {
    return promptNonBlank("Enter a rail line:", "line");
  }

  public String promptDestination() {
    return promptNonBlank("Enter destination:", "destination");
  }

  public Optional<Integer> promptTrack() {
    return promptOptionalInt("Enter track number (or leave blank):", "track");
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

  public void printNoMatches() {
    System.out.println("No matches found");
  }

  public void printCommandLog(ArrayList<CommandLog> commandHistory) {
    if (commandHistory.isEmpty()) {
      System.out.println("Command history is empty");
      return;
    }
    var out = new StringBuilder();
    for (var i = 0; i < commandHistory.size(); i++) {
      out.append((i + 1)).append(". ").append(commandHistory.get(i).display()).append("\n");
    }
    System.out.println(out);
  }

  public void printNoUndoFeedback() {
    System.out.println("No commands to undo");
  }
}
