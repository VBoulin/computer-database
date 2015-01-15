package com.excilys.formation.java.validator.impl;

import java.util.regex.Pattern;

import com.excilys.formation.java.validator.Validator;

public class IdValidator implements Validator{

  private Pattern             regex;

  private static final String ID_REGEX = "^[0-9]+$";

  public IdValidator() {
    regex = Pattern.compile(ID_REGEX);
  }

  /**
   * Validate Id format with regular expression
   * @param input : id
   * @return true : correct format, false : incorrect format 
   */
  public boolean validate(String input) {

    if (regex.matcher(input).find()) {
      return true;
    } else {
      return false;
    }
  }
}
