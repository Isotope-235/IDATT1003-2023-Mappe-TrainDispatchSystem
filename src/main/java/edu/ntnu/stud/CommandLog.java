package edu.ntnu.stud;

public interface CommandLog {
  void undo(TrainDispatchApp app);
  String display();
}
