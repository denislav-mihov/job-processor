package com.mihov.jobprocessor.beans;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Created by Denis on 07-Feb-20.
 *
 * @author Denis
 */
public class Task{
  @NotBlank(message = "\"name\" can not be empty")
  private String name;
  @NotBlank
  private String command;

  public Task() {
  }

  public Task(@NotBlank String name, @NotBlank String command) {
    this.name = name;
    this.command = command;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task1 = (Task) o;
    return Objects.equals(getName(), task1.getName()) &&
      Objects.equals(getCommand(), task1.getCommand());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getCommand());
  }

  @Override
  public String toString() {
    return "Task{" +
      "name='" + name + '\'' +
      ", command='" + command + '\'' +
      '}';
  }
}
