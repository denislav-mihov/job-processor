package com.mihov.jobprocessor;

import com.mihov.jobprocessor.beans.Task;
import com.mihov.jobprocessor.beans.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    Map<String, String> map = getRegister(list.getTasks());

    List<String> sortedNames = taskService.getSortedTaskNames(list.getTasks());
    logger.debug("Sorted task names: {}", sortedNames);

    return sortedNames.stream()
      .map(name -> new Task(name, map.get(name)))
      .collect(Collectors.toList());
  }

  @PostMapping("/build")
  @ResponseStatus(HttpStatus.OK)
  public String build(@RequestBody @Validated TaskList list) {

    logger.debug("Input list: {}", list);

    Map<String, String> map = getRegister(list.getTasks());

    List<String> sortedNames = taskService.getSortedTaskNames(list.getTasks());
    logger.debug("Sorted task names: {}", sortedNames);

    StringBuilder sb = new StringBuilder();
    for (String name : sortedNames) {
      sb.append(map.get(name));
      sb.append(NEW_LINE);
    }
    return sb.toString();
  }

  /**
   * Prepare a mapping between the task names and commands.
   * The dependencies are not included.
   * @param list List of tasks
   * @return HashMap(name, command)
   */
  private Map<String, String> getRegister(List<? extends Task> list) {
    Map<String, String> map = new HashMap<>();
    for (Task task : list) {
      if (!map.containsKey(task.getName())) {
        map.put(task.getName(), task.getCommand());
      }
      else if (!map.get(task.getName()).equals(task.getCommand())) {
        throw new IllegalArgumentException("List contains duplicated task names!");
      }
    }
    return map;
  }
}
