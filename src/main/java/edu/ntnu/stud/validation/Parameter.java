package edu.ntnu.stud.validation;

public class Parameter {
  public static void notBlank(String param, String name) {
    if (param.isBlank()) {
      throw new IllegalArgumentException(name + " cannot be blank");
    }
  }

  public static void positive(int param, String name) {
    if (param < 1) {
      throw new IllegalArgumentException(name + " must be positive");
    }
  }
}
