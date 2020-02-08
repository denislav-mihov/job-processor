package com.mihov.jobprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihov.jobprocessor.beans.InputTask;
import com.mihov.jobprocessor.beans.TaskList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@SpringBootTest
public class TaskControllerTest{
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testSort_OK() throws Exception {

    TaskList list = new TaskList();
    List<InputTask> tasks = new ArrayList<>();
    tasks.add(new InputTask("task-1", "touch /tmp/file1"));
    tasks.add(new InputTask("task-2", "cat /tmp/file1", Collections.singletonList("task-3")));
    tasks.add(new InputTask("task-3", "echo 'Hello World!' > /tmp/file1", Collections.singletonList("task-1")));
    tasks.add(new InputTask("task-4", "rm /tmp/file1", Arrays.asList("task-2", "task-3")));
    list.setTasks(tasks);

    mockMvc.perform(
      post("/tasks/sort")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(list))
    ).andExpect(status().isOk());
  }

  @Test
  public void testSort_NOK() throws Exception {

    TaskList list = new TaskList();
    List<InputTask> tasks = new ArrayList<>();
    tasks.add(new InputTask("task-1", "touch /tmp/file1"));
    tasks.add(new InputTask("task-2", "cat /tmp/file1", Collections.singletonList("task-4")));
    tasks.add(new InputTask("task-3", "echo 'Hello World!' > /tmp/file1", Collections.singletonList("task-1")));
    tasks.add(new InputTask("task-4", "rm /tmp/file1", Arrays.asList("task-2", "task-3")));
    list.setTasks(tasks);

    mockMvc.perform(
      post("/tasks/sort")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(list))
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void testBuild_OK() throws Exception {

    TaskList list = new TaskList();
    List<InputTask> tasks = new ArrayList<>();
    tasks.add(new InputTask("task-1", "touch /tmp/file1"));
    tasks.add(new InputTask("task-2", "cat /tmp/file1", Collections.singletonList("task-3")));
    tasks.add(new InputTask("task-3", "echo 'Hello World!' > /tmp/file1", Collections.singletonList("task-1")));
    tasks.add(new InputTask("task-4", "rm /tmp/file1", Arrays.asList("task-2", "task-3")));
    list.setTasks(tasks);

    mockMvc.perform(
      post("/tasks/build")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(list))
    ).andExpect(status().isOk());
  }

  @Test
  public void testBuild_NOK() throws Exception {

    TaskList list = new TaskList();
    List<InputTask> tasks = new ArrayList<>();
    tasks.add(new InputTask("task-1", "touch /tmp/file1", Collections.singletonList("task-2")));
    tasks.add(new InputTask("task-2", "cat /tmp/file1", Collections.singletonList("task-1")));
    list.setTasks(tasks);

    mockMvc.perform(
      post("/tasks/build")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(list))
    ).andExpect(status().isBadRequest());
  }
}
