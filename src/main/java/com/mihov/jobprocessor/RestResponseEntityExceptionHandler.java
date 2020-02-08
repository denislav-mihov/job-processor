package com.mihov.jobprocessor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Denis on 08-Feb-20.
 *
 * @author Denis
 */
@ControllerAdvice(assignableTypes = TaskController.class)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {GraphContainsCycleException.class, IllegalArgumentException.class})
  protected @ResponseBody String handleConflict(RuntimeException e) {
    logger.warn("Warn: ", e);
    if (e instanceof GraphContainsCycleException) {
      return "Incorrect input. There are cyclic dependencies in the provided task list!";
    } else {
      return e.getMessage();
    }
  }
}
