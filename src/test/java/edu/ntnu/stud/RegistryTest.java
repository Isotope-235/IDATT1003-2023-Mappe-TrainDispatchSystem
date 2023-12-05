package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Map;

public class RegistryTest {
  private Registry reg;
  private Departure testDep;

  @Test
  void constructor() {
    assertDoesNotThrow(Registry::new);
  }

  @BeforeEach
  void initTestData() {
    reg = new Registry();
    testDep = new Departure(LocalTime.of(12, 30), "A4", "Oslo S");
  }

  @Nested
  class EntryTest {
    @Test
    void fromMapPair() {
      var entry = new Registry.Entry(1, testDep);
      var fromMapPair = Registry.Entry.fromMapPair(Map.entry(1, testDep));
      assertEquals(entry.number(), fromMapPair.number());
      assertEquals(entry.departure(), fromMapPair.departure());
    }
  }

  @Test
  void remove() {
    reg.addWithNumber(1, testDep);
    assertThrows(IllegalStateException.class, () -> reg.addWithNumber(1, testDep));
    assertTrue(reg.getDeparture(1).isPresent());
    assertEquals(testDep, reg.remove(1).get());
    assertTrue(reg.getDeparture(1).isEmpty());
  }

  @Test
  void addWithNumber() {
    assertDoesNotThrow(() -> reg.addWithNumber(1, testDep));
    assertThrows(IllegalStateException.class, () -> reg.addWithNumber(1, testDep));
  }

  @Test
  void add() {
    assertDoesNotThrow(() -> reg.add(testDep));
    assertEquals(testDep, reg.getDeparture(1).get());
  }

  @Test
  void numbersInUse() {
    reg.addWithNumber(1, testDep);
    reg.addWithNumber(2, new Departure(LocalTime.of(12, 31), "A4", "Trondheim Stasjon"));
    var numbers = reg.numbersInUse();
    assertTrue(numbers.contains(1));
    assertTrue(numbers.contains(2));
    assertFalse(numbers.contains(3));
  }

  @Test
  void getDeparture() {
    var maybeDep = reg.getDeparture(1);
    assertTrue(maybeDep.isEmpty());
    reg.addWithNumber(1, testDep);
    assertTrue(reg.getDeparture(1).isPresent());
  }

  @Test
  void withDestination() {
    reg.addWithNumber(1, testDep);
    reg.addWithNumber(2, new Departure(LocalTime.of(12, 30), "A4", "Trondheim Stasjon"));
    var entries = reg.withDestination("Oslo S");
    assertEquals(1, entries.length);
    assertEquals("Oslo S", entries[0].departure().getDestination());
  }

  @Test
  void afterOrAt() {
    reg.addWithNumber(1, testDep);
    reg.addWithNumber(2, new Departure(LocalTime.of(12, 31), "A4", "Trondheim Stasjon"));
    var entries = reg.afterOrAt(LocalTime.of(12, 30));
    assertEquals(2, entries.length);
    entries = reg.afterOrAt(LocalTime.of(12, 31));
    assertEquals(1, entries.length);
    assertEquals("Trondheim Stasjon", entries[0].departure().getDestination());
  }

  @Test
  void byTime() {
    reg.addWithNumber(1, testDep);
    reg.addWithNumber(2, new Departure(LocalTime.of(12, 31), "A4", "Trondheim Stasjon"));
    reg.addWithNumber(3, new Departure(LocalTime.of(6, 30), "A2", "Bergen Stasjon"));
    var entries = reg.byTime();
    assertEquals(LocalTime.of(6, 30), entries[0].departure().getTime());
    assertEquals(LocalTime.of(12, 30), entries[1].departure().getTime());
    assertEquals(LocalTime.of(12, 31), entries[2].departure().getTime());
  }

}
