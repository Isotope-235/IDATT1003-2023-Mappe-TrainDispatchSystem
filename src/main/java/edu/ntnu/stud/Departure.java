package edu.ntnu.stud;

import edu.ntnu.stud.validation.Parameter;

import java.time.LocalTime;
import java.util.Optional;

public class Departure {
  private final LocalTime time;
  private final String line;
  private final String destination;
  private int track;
  private LocalTime delay;

  public Departure(LocalTime time, String line, String destination) {

    Parameter.notBlank(line, "Line"); // propagate exceptions
    Parameter.notBlank(destination, "Destination"); // propagate exceptions

    this.time = time;
    this.line = line;
    this.destination = destination;

    this.track = -1;
    this.delay = LocalTime.of(0, 0);
  }

  public LocalTime getTime() {
    return time;
  }

  public LocalTime getRealTime() {
    return time.plusHours(delay.getHour()).plusMinutes(delay.getMinute());
  }

  public String getLine() {
    return line;
  }

  public String getDestination() {
    return destination;
  }

  public Optional<Integer> getTrack() {
    return track == -1 ? Optional.empty() : Optional.of(track);
  }

  public void setTrack(int track) {
    if (track < 1) {
      throw new IllegalArgumentException("Track number must be positive");
    }
    this.track = track;
  }

  public LocalTime getDelay() {
    return delay;
  }

  public void setDelay(LocalTime delay) {
    this.delay = delay;
  }
}
