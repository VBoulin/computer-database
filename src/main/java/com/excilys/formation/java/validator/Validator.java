package com.excilys.formation.java.validator;

public interface Validator {
  /**
   * Validate the format of a String
   * @param input String that needs to be validated
   * @return True if correct, false otherwise
   */
  public boolean validate(String input);
}
