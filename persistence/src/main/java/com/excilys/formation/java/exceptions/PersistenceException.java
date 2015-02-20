package com.excilys.formation.java.exceptions;

/**
 * Custom exception
 * @author Vincent
 *
 */
public class PersistenceException extends RuntimeException {

  private static final long   serialVersionUID = 164146942369822205L;
  private static final String MESSAGE          = "An error occured during the transaction.";

  public PersistenceException() {
    super(MESSAGE);
  }

  public PersistenceException(String message) {
    super(message);
  }

  public PersistenceException(Exception e) {
    super(MESSAGE, e);
  }

  public PersistenceException(String message, Exception e) {
    super(message, e);
  }

}