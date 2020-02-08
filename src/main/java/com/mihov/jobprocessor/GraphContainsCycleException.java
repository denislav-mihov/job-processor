package com.mihov.jobprocessor;

/**
 * Created by Denis on 08-Feb-20.
 *
 * @author Denis
 */
public class GraphContainsCycleException extends RuntimeException{
  public GraphContainsCycleException() {
  }

  public GraphContainsCycleException(String message) {
    super(message);
  }
}
