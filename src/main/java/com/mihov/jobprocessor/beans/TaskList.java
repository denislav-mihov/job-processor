package com.mihov.jobprocessor.beans;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
public class TaskList{
  @Valid
  List<InputTask> tasks;

  public List<InputTask> getTasks() {
    if (tasks == null) {
      return new ArrayList<>();
    }
    return tasks;
  }

  public void setTasks(List<InputTask> tasks) {
    this.tasks = tasks;
  }

  @Override
  public String toString() {
    return "TaskList{" +
      "tasks=" + tasks +
      '}';
  }
}
