package edu.ntnu.stud.validation;

public class Parameter {
  public static void notBlank(String param, String name) {
    if (param.isBlank()) {
      throw new IllegalArgumentException(name + " cannot be blank");
    }
  }
}
