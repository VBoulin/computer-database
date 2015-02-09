package com.excilys.formation.java.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConstraintDateValidator implements ConstraintValidator<DateValidator, String> {

  @Override
  public void initialize(DateValidator arg0) {}

  /**
   * Check if a String is a valid date
   * @param input String to check
   * @return true if the format is correct or if the string is empty
   */
  @Override
  public boolean isValid(String input, ConstraintValidatorContext context) {
    return Validator.isDate(input);
  }

}