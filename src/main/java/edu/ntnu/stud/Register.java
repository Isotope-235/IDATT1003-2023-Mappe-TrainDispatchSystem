package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Optional;

public class Register {

  public void add() {
    // TODO
    // throw exeption if train already exists
  }

  public Optional<Departure> get(int id) {
    // TODO
    // get departure by id
    return Optional.of(new Departure(LocalTime.of(0, 0), "", ""));
  }

  public Departure[] withDestination(String destination) {
    // TODO
    // get all departures with destination
    return new Departure[]{};
  }

  public Departure[] after(int time) {
    // TODO
    // return all departures after or at time
    return new Departure[]{};
  }

  public Departure[] byTime() {
    // TODO
    // return all departures sorted by time, without taking delays into account
    return new Departure[]{};
  }

}
