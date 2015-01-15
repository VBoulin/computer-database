package com.excilys.formation.java.validator.impl;

import java.util.regex.Pattern;

import com.excilys.formation.java.validator.Validator;

public class DateValidator implements Validator{

  private Pattern             regex;

  private static final String DATE_REGEX = "^[1-2][0-9]{3}-([0][1-9]|1[0-2])-([0-2][1-9]|3[0-1])$";

  public DateValidator() {
    regex = Pattern.compile(DATE_REGEX);
  }

  /**
   * Validate date format with regular expression
   * @param input : date
   * @return true : correct format, false : incorrect format 
   */
  public boolean validate(String input) {

    if (regex.matcher(input).find()) {

      String[] subString = input.split("-");
      int year = Integer.parseInt(subString[0]);
      int month = Integer.parseInt(subString[1]);
      int day = Integer.parseInt(subString[2]);

      if ((day == 31) && (month == 4 || month == 6 || month == 9 || month == 11)) {
        // only 1,3,5,7,8,10,12 has 31 days
        return false;
      } else if (month == 2) {
        //leap year
        if (year % 4 == 0) {
          if (day == 30 || day == 31) {
            return false;
          } else {
            return true;
          }
        } else {
          if (day == 29 || day == 30 || day == 31) {
            return false;
          } else {
            return true;
          }
        }
      } else {
        return true;
      }
    } else {
      return false;
    }
  }
}