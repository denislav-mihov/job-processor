package com.mihov.jobprocessor;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
@Component
public class TaskProcessor{
  public List<Task> process(List<Task> input) {
    Set<Task> set = new HashSet<>();
    for (Task task: input) {
      if (task.getRequires() == null) {
        set.add(task);
      }
    }
  }
}
