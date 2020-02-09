package com.mihov.jobprocessor;

import com.mihov.jobprocessor.beans.Task;
import com.mihov.jobprocessor.beans.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
@RestController
@RequestMapping("/tasks")
public class TaskController{

  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  private static final String NEW_LINE = System.lineSeparator();

  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping("/sort")
  @ResponseStatus(HttpStatus.OK)
  public List<Task> sort(@RequestBody @Validated TaskList list) {

    logger.debug("Input list: {}", list);

    return taskService.sort(list.getTasks());
  }

  @PostMapping("/build")
  @ResponseStatus(HttpStatus.OK)
  public String build(@RequestBody @Validated TaskList list) {

    logger.debug("Input list: {}", list);

    return taskService.sort(list.getTasks()).stream()
      .map(Task::getCommand)
      .collect(Collectors.joining(NEW_LINE, "#!/usr/bin/env bash\n\n", ""));
  }
}
