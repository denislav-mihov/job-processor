package com.mihov.jobprocessor;

import com.mihov.jobprocessor.beans.InputTask;
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
   * Process the input list of tasks and return a sorted list of task names.
   * First step is to build a graph
   * Then use modified DFS to implement a Topological Sort of the graph.
   * If the graph contains cycles then it is not a Directed Acyclic Graph.
   * In this case it can't be processed because we can not determine correctly the order of the tasks.
   *
   * @param input The input list of tasks
   * @return The topological sort of the task names.
   */
  public List<String> getSortedTaskNames(List<InputTask> input) {

    final Map<String, Set<String>> graph = buildGraph(input);

    Stack<String> stack = new Stack<>();
    Set<String> visited = new HashSet<>();
    Set<String> processed = new HashSet<>();

    for (Map.Entry<String, Set<String>> edge : graph.entrySet()) {
      if (!processed.contains(edge.getKey())) {
        topologicalSort(edge.getKey(), graph, visited, processed, stack);
      }
    }

    List<String> sortedNames = new ArrayList<>();
    while (!stack.empty()) {
      sortedNames.add(stack.pop());
    }
    return sortedNames;
  }

  /**
   * Build an adjacency list representing the task graph using a map data structure
   * @param list Input list of tasks
   * @return HashMap(U, Set(V)) where U is a task that should be executed before the tasks in Set(V)
   */
  private Map<String, Set<String>> buildGraph(List<InputTask> list) {
    Map<String, Set<String>> graph = new HashMap<>();
    for (InputTask task : list) {
      graph.putIfAbsent(task.getName(), new HashSet<>());
      for (String dependency : task.getRequires()) {
        graph.putIfAbsent(dependency, new HashSet<>());
        graph.get(dependency).add(task.getName());
      }
    }
    return graph;
  }

  /**
   * A recursive helper method to implement the topological sort.
   * It uses 2 sets to mark the visited and processed tasks and a stack to store the order of the tasks.
   */
  private void topologicalSort(String task, Map<String, Set<String>> graph, Set<String> visited, Set<String> processed, Stack<String> stack) {
    if (processed.contains(task)) {
      return;
    }

    if (visited.contains(task)) {
      throw new GraphContainsCycleException("This is not DAG!");
    }

    visited.add(task);

    for (String edge : graph.get(task)) {
      topologicalSort(edge, graph, visited, processed, stack);
    }

    visited.remove(task);
    processed.add(task);
    stack.push(task);
  }

}
