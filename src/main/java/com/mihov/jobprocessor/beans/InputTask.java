package com.mihov.jobprocessor.beans;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 08-Feb-20.
 *
 * @author Denis
 */
public class InputTask extends Task{
  private List<String> requires;

  public InputTask() {
  }

  public InputTask(@NotBlank String name, @NotBlank String command) {
    super(name, command);
  }

  public InputTask(@NotBlank String name, @NotBlank String command, List<String> requires) {
    super(name, command);
    this.requires = requires;
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
  public String toString() {
    return "InputTask{" +
      ", name='" + super.getName() + '\'' +
      ", command='" + super.getCommand() + '\'' +
      "requires=" + requires +
      '}';
  }
}
