package com.mihov.jobprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
@RestController
public class JobController{

  private final TaskProcessor taskProcessor;

  @Autowired
  public JobController(TaskProcessor taskProcessor) {
    this.taskProcessor = taskProcessor;
  }

  @PostMapping("/order-tasks")
  @ResponseStatus(HttpStatus.OK)
  public List<Task> orderTasks(
    @RequestParam(name = "useFile", defaultValue = "false") boolean useFile,
    @RequestBody @Validated TaskList list
  ) {
    return taskProcessor.process(list.getTasks());
  }
}
