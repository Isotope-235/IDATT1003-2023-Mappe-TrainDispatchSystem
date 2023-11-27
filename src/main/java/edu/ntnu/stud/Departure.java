package edu.ntnu.stud;

import java.time.LocalTime;

public class Departure {
  private final LocalTime time;
  private final String line;
  private final String destination;
  private int track;
  private LocalTime delay;

  public Departure(LocalTime time, String line, String destination) {
    this.time = time;
    this.line = line;
    this.destination = destination;

    this.track = -1;
    this.delay = LocalTime.of(0, 0);
  }

  private void setTrack(int track) {
    if (track < 1) {
      throw new IllegalArgumentException("Track number must be positive");
    }
    this.track = track;
  }
}
