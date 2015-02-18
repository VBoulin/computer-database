package com.excilys.formation.java.util;

import org.apache.commons.validator.GenericValidator;


public class Validator {

  /**
   * Check if a String is a valid date
   * @param input String to check
   * @return true if the format is correct
   */
  public static boolean isDate(String input, String format) {
    if (input == null) {
      return false;
    }
    if (input.trim().isEmpty()) {
      return true;
    }
    
    return GenericValidator.isDate(input, format, false);
      
  }

  /**
   * Check if a String is a valid ID
   * @param string String to check
   * @return true if the format is correct
   */
  public static boolean isID(String input) {

    if (input == null || input.trim().isEmpty()) {
      return false;
    }

    return GenericValidator.isLong(input);
  }

  /**
   * Check if a String is a valid name
   * @param string String to check
   * @return true if the format is correct
   */
  public static boolean isName(String input) {

    if (input == null || input.trim().isEmpty()) {
      return false;
    }

    return true;
  }

  public static boolean isInt(String input) {
    if (input == null || input.trim().isEmpty()) {
      return false;
    }
    
    return GenericValidator.isInt(input);
  }
}
