package com.mihov.jobprocessor;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
public class Task{
  @NotBlank
  private String task;
  @NotBlank
  private String command;

  @JsonIgnore
  private List<String> requires;

  public Task() {
  }

  public Task(@NotBlank String task, @NotBlank String command) {
    this.task = task;
    this.command = command;
  }

  public Task(@NotBlank String task, @NotBlank String command, List<String> requires) {
    this(task, command);
    this.requires = requires;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public List<String> getRequires() {
    if (requires == null) {
      return new ArrayList<>();
    }
    return requires;
  }

  public void setRequires(List<String> requires) {
    this.requires = requires;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task1 = (Task) o;
    return Objects.equals(getTask(), task1.getTask()) &&
      Objects.equals(getCommand(), task1.getCommand()) &&
      Objects.equals(getRequires(), task1.getRequires());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTask(), getCommand(), getRequires());
  }
}
