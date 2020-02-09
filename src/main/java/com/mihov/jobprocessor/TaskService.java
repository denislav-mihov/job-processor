package com.mihov.jobprocessor;

import com.mihov.jobprocessor.beans.InputTask;
import com.mihov.jobprocessor.beans.Task;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
@Component
public class TaskService{

  /**
   * The list of tasks and their dependencies is supposed to represent a Directed Acyclic Graph.
   * So we need to find the topological sort of this graph.
   * First we put the list in a map for faster lookup.
   * Then use modified DFS to implement the topological sort.
   * If the graph contains cycles then it is not a Directed Acyclic Graph and can not be topologically sorted.
   * In this case it can't be processed because we can not determine correctly the order of the tasks.
   *
   * @param input The input list of tasks. It represents a reverted adjacency list the way it is given.
   * @return The sorted tasks.
   */
  public List<Task> sort(List<InputTask> input) {

    final Map<String, InputTask> map = buildMap(input);

    Set<String> visited = new HashSet<>();
    Set<String> processed = new HashSet<>();
    List<Task> sorted = new ArrayList<>();

    for (Map.Entry<String, InputTask> entry : map.entrySet()) {
      if (!processed.contains(entry.getKey())) {
        topologicalSort(entry.getKey(), map, visited, processed, sorted);
      }
    }

    return sorted;
  }

  /**
   * Put the input data in a map for faster lookup and check for duplicated names
   */
  private Map<String, InputTask> buildMap(List<InputTask> list) {
    Map<String, InputTask> map = new HashMap<>();
    for (InputTask task : list) {
      if (!map.containsKey(task.getName())) {
        map.put(task.getName(), task);
      }
      else {
        throw new IllegalArgumentException("List contains duplicated names!");
      }
    }
    return map;
  }

  /**
   * A recursive helper method to implement the topological sort.
   * It uses 2 sets to mark the visited and processed tasks and a list to store the ordered tasks.
   * Since we use a reverted adjacency list we don't need to put the sorted elements in a stack unlike the classical algorithm.
   * We could use a straight list instead.
   */
  private void topologicalSort(String name, Map<String, InputTask> map, Set<String> visited, Set<String> processed, List<Task> sorted) {
    if (processed.contains(name)) {
      return;
    }

    if (visited.contains(name)) {
      throw new GraphContainsCycleException("This is not DAG!");
    }

    visited.add(name);

    for (String vertice : map.get(name).getRequires()) {
      topologicalSort(vertice, map, visited, processed, sorted);
    }

    visited.remove(name);
    processed.add(name);

    Task task = new Task(name, map.get(name).getCommand());
    sorted.add(task);
  }

}
