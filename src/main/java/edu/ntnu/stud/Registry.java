package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * A class representing a registry of train departures from a station.
 * It acts as an interface through which to read and modify the
 * set of departures on which a train dispatch application operates.
 *
 * @author Jonathan Jensen
 */
public class Registry {
  private final HashMap<Integer, Departure> departures;

  private Stream<Map.Entry<Integer, Departure>> departureStream() {
    return departures.entrySet().stream();
  }

  private static Entry[] mapToEntries(Stream<Map.Entry<Integer, Departure>> stream) {
    return stream.map(Entry::fromMapPair).toArray(Entry[]::new);
  }


  /**
   * Constructs a new, empty registry.
   * If you want to populate the registry with departures, call {@link #addWithNumber(int, Departure)}
   * after constructing the registry.
   */
  public Registry() {
    this.departures = new HashMap<>();
  }

  /**
   * Removes the departure with the given departure number from the registry.
   *
   * @param id The departure number of the departure to be removed
   * @return The departure which was removed
   */
  public Optional<Departure> remove(int id) {
    return Optional.ofNullable(this.departures.remove(id));
  }

  /**
   * A record which represents an entry in the departure registry.
   * It consists of a departure and its departure number, which are available
   * through their associated methods.
   *
   * @param number The departure number
   * @param departure The departure itself
   */
  public record Entry(int number, Departure departure) {
    public static Entry fromMapPair(Map.Entry<Integer, Departure> entry) {
      return new Entry(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Adds a departure to the registry, with the given departure number.
   *
   * @param number The number to be associated with the departure
   * @param departure The departure to be added to the registry
   * @throws IllegalStateException If the given number is already
   *     associated with a departure in the registry
   */
  public void addWithNumber(int number, Departure departure) {
    if (departures.containsKey(number)) {
      throw new IllegalStateException("Departure " + number + " already exists");
    }
    departures.put(number, departure);
  }

  /**
   * Adds a departure to the registry, generating a departure number automatically.
   *
   * @return The departure number associated with the added departure
   * @param departure The departure to be added to the registry
   */
  public int add(Departure departure) {
    var number = departures.keySet().stream()
            .max(Comparator.comparingInt(key -> key))
            .map(num -> num + 1)
            .orElse(1); // start at 1 if there are no departures
    departures.put(number, departure);
    return number;
  }

  /**
   * The set of all departure numbers currently in use.
   *
   * @return A set of all departure numbers currently in use
   */
  public Set<Integer> numbersInUse() {
    return departures.keySet();
  }

  /**
   * Returns the departure given by its departure number, if it exists.
   *
   * @param id The departure number of the requested departure
   * @return An {@link Optional} containing the departure associated
   *     with the given number, or an empty {@link Optional}
   *     if the number has no associated departure
   */
  public Optional<Departure> getDeparture(int id) {
    var out = departures.get(id);
    return Optional.ofNullable(out);
  }

  /**
   * Gets all registered departures to the destination specified.
   *
   * <p>Departures are in the form of a {@link Registry.Entry},
   * which contains both the departure number and the {@link Departure} itself.
   *
   * <p>The order of the returned collection is not specified.
   *
   * @param destination The destination to filter by
   * @return All registered departures to the destination specified.
   */
  public Entry[] withDestination(String destination) {
    var out = departureStream()
            .filter(entry -> entry.getValue().getDestination().equals(destination));
    return mapToEntries(out);
  }

  /**
   * Gets all registered departures which have not left according to the time specified.
   * Delays are taken into account when deciding whether a departure has left or not.
   *
   * <p>Departures are in the form of a {@link Registry.Entry},
   * which contains both the departure number and the {@link Departure} itself.
   *
   * <p>The order of the returned collection is not specified.
   *
   * @param time The time to filter by
   * @return All registered departures with a departure time after or at the specified time
   */
  public Entry[] afterOrAt(LocalTime time) {
    var out = departureStream()
            .filter(entry -> entry.getValue().getRealTime().isAfter(time.minusMinutes(1)));
    return mapToEntries(out);
  }

  /**
   * Gets all departures, sorted first by time, then destination.
   *
   * <p>Departures are in the form of a {@link Registry.Entry},
   * which contains both the departure number and the {@link Departure} itself.
   *
   * @return All departures, sorted first by time, then destination
   */
  public Entry[] byTime() {
    var comparison = Comparator.comparing(
                            (Map.Entry<Integer, Departure> entry) -> entry.getValue().getTime()
                    ).thenComparing(entry -> entry.getValue().getDestination());
    var out = departureStream()
            .sorted(comparison);
    return mapToEntries(out);
  }
}


