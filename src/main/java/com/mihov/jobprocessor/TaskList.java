package com.mihov.jobprocessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
public class TaskList{
  List<Task> tasks;

  public List<Task> getTasks() {
    if (tasks == null) {
      return new ArrayList<>();
    }
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }
}
