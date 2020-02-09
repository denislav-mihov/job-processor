package com.mihov.jobprocessor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    BindingResult br = ex.getBindingResult();
    List<ObjectError> errors = br.getAllErrors();
    StringBuilder errorMessage = new StringBuilder();
    for (ObjectError oe : errors) {
      if (oe instanceof FieldError) {
        FieldError fe = (FieldError) oe;
        errorMessage.append(oe.getObjectName())
          .append(" - ")
          .append(fe.getField())
          .append(" - ")
          .append(fe.getRejectedValue())
          .append(" - ")
          .append(fe.getDefaultMessage())
          .append("; ");
      }
    }

    return handleExceptionInternal(ex, errorMessage, headers, status, request);
  }
}
