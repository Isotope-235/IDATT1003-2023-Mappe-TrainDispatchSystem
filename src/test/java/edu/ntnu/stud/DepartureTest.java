package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import java.time.LocalTime;

public class DepartureTest {
  @Test
  void constructor() {
    assertThrows(IllegalArgumentException.class, () -> new Departure(LocalTime.of(0, 0), "", "Trondheim"));
    assertThrows(IllegalArgumentException.class, () -> new Departure(LocalTime.of(0, 0), "F1", ""));
    assertThrows(IllegalArgumentException.class, () -> new Departure(LocalTime.of(0, 0), "", ""));
    assertDoesNotThrow(() -> new Departure(LocalTime.of(0, 0), "F1", "Trondheim"));
  }

  @Test
  void setTrack() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    assertDoesNotThrow(() -> dep.setTrack(3));
    assertThrows(IllegalArgumentException.class, () -> dep.setTrack(-3));
    assertThrows(IllegalArgumentException.class, () -> dep.setTrack(0));
  }

  @Test
  void getTime() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    assertEquals(LocalTime.of(12, 30), dep.getTime());
  }

  @Test
  void getRealTime() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    assertEquals(LocalTime.of(12, 30), dep.getRealTime());
    dep.setDelay(LocalTime.of(0, 30));
    assertEquals(LocalTime.of(13, 0), dep.getRealTime());
  }

  @Test
  void getLine() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    assertEquals("F1", dep.getLine());
  }

  @Test
  void getDestination() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    assertEquals("Trondheim", dep.getDestination());
  }

  @Test
  void getTrack() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    assertEquals(Optional.empty(), dep.getTrack());
    dep.setTrack(3);
    assertEquals(Optional.of(3), dep.getTrack());
  }

  @Test
  void getDelay() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    assertEquals(LocalTime.of(0, 0), dep.getDelay());
    dep.setDelay(LocalTime.of(0, 30));
    assertEquals(LocalTime.of(0, 30), dep.getDelay());
  }

  @Test
  void setDelay() {
    var dep = new Departure(LocalTime.of(12, 30), "F1", "Trondheim");
    dep.setDelay(LocalTime.of(0, 30));
    assertEquals(LocalTime.of(0, 30), dep.getDelay());
  }
}
