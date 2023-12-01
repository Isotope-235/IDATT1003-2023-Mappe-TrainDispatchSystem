package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import java.time.LocalTime;

public class DepartureTest {
  private Departure dep;
  @Test
  void constructor() {
    assertThrows(IllegalArgumentException.class, () -> new Departure(LocalTime.of(0, 0), "", "Trondheim"));
    assertThrows(IllegalArgumentException.class, () -> new Departure(LocalTime.of(0, 0), "F1", ""));
    assertThrows(IllegalArgumentException.class, () -> new Departure(LocalTime.of(0, 0), "", ""));
    assertDoesNotThrow(() -> new Departure(LocalTime.of(0, 0), "F1", "Trondheim"));
  }

  @BeforeEach
  void initTestData() {
    dep = new Departure(LocalTime.of(12, 30), "A4", "Oslo S");
  }

  @Test
  void setTrack() {
    assertDoesNotThrow(() -> dep.setTrack(3));
    assertThrows(IllegalArgumentException.class, () -> dep.setTrack(-3));
    assertThrows(IllegalArgumentException.class, () -> dep.setTrack(0));
  }

  @Test
  void getTime() {
    assertEquals(LocalTime.of(12, 30), dep.getTime());
  }

  @Test
  void getRealTime() {
    assertEquals(LocalTime.of(12, 30), dep.getRealTime());
    dep.setDelay(LocalTime.of(0, 30));
    assertEquals(LocalTime.of(13, 0), dep.getRealTime());
  }

  @Test
  void getLine() {
    assertEquals("A4", dep.getLine());
  }

  @Test
  void getDestination() {
    assertEquals("Oslo S", dep.getDestination());
  }

  @Test
  void getTrack() {
    assertEquals(Optional.empty(), dep.getTrack());
    dep.setTrack(3);
    assertEquals(Optional.of(3), dep.getTrack());
  }

  @Test
  void getDelay() {
    assertEquals(LocalTime.of(0, 0), dep.getDelay());
    dep.setDelay(LocalTime.of(0, 30));
    assertEquals(LocalTime.of(0, 30), dep.getDelay());
  }

  @Test
  void setDelay() {
    dep.setDelay(LocalTime.of(0, 30));
    assertEquals(LocalTime.of(0, 30), dep.getDelay());
  }
}
