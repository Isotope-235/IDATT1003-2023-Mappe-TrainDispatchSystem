package edu.ntnu.stud;

import edu.ntnu.stud.validation.Parameter;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;


/**
 * This class stores information about a departure. It is responsible for validating its fields.
 * Time of departure, line, and destination are required (and immutable) fields.
 * Track and delay are optional, and may be changed.
 *
 * @author Jonathan Jensen
 */
public class Departure {
  private final LocalTime time;
  private final String line;
  private final String destination;
  private int track;
  private LocalTime delay;

  /**
   * Constructs a departure from its parts, setting default values for track and delay.
   *
   * @param time Time of departure, not including delays
   * @param line Line, e.g. "L1"
   * @param destination Destination, e.g. "Oslo S"
   * @throws IllegalArgumentException If the line is blank, or if the destination is blank
   */
  public Departure(LocalTime time, String line, String destination) {
    Parameter.notBlank(line, "Line"); // propagate exception
    Parameter.notBlank(destination, "Destination"); // propagate exception

    this.time = time;
    this.line = line;
    this.destination = destination;

    this.track = -1;
    this.delay = LocalTime.of(0, 0);
  }

  /**
   * Gets the time of departure, not including delays.
   *
   * @return Time of departure, not including delays
   */
  public LocalTime getTime() {
    return time;
  }

  /**
   * Gets the time of departure, including delays.
   *
   * @return Time of departure, including delays
   */
  public LocalTime getRealTime() {
    return time.plusHours(delay.getHour()).plusMinutes(delay.getMinute());
  }

  /**
   * Gets the line for the departure.
   *
   * @return The line for the departure
   */
  public String getLine() {
    return line;
  }

  /**
   * Gets the destination for the departure.
   *
   * @return The destination for the departure
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Gets the track number for the departure, if one has been assigned.
   *
   * @return An {@link Optional} containing the track number, or an empty {@link Optional}
   *     if no track has been assigned
   */
  public Optional<Integer> getTrack() {
    return track == -1 ? Optional.empty() : Optional.of(track);
  }

  /**
   * Assigns a track number to the departure.
   *
   * @param track The track number to assign to the departure
   * @throws IllegalArgumentException If the given track number is negative
   */
  public void setTrack(int track) {
    Parameter.positive(track, "Track number"); // propagate exception
    this.track = track;
  }

  /**
   * Gets the delay for the departure.
   *
   * @return The delay for the departure
   */
  public LocalTime getDelay() {
    return delay;
  }

  /**
   * Assigns a delay to the departure.
   *
   * @param delay The delay to be assigned to the departure
   */
  public void setDelay(LocalTime delay) {
    this.delay = delay;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Departure departure = (Departure) o;
    return track == departure.track
            && Objects.equals(time, departure.time)
            && Objects.equals(line, departure.line)
            && Objects.equals(destination, departure.destination)
            && Objects.equals(delay, departure.delay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(time, line, destination, track, delay);
  }
}
