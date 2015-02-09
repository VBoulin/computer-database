package com.excilys.formation.java.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConstraintDateValidator implements ConstraintValidator<DateValidator, String> {

  private static final String DATE_REGEX = "^[1-2][0-9]{3}-([0][1-9]|1[0-2])-([0-2][1-9]|3[0-1])$";
  
  @Override
  public void initialize(DateValidator arg0) {}

  /**
   * Check if a String is a valid date
   * @param input String to check
   * @return true if the format is correct or if the string is empty
   */
  @Override
  public boolean isValid(String input, ConstraintValidatorContext context) {
    if (input == null) {
      return false;
    }
    if (input.trim().isEmpty()) {
      return true;
    }

    Pattern regex = Pattern.compile(DATE_REGEX);
    if (regex.matcher(input).find()) {

      String[] subString = input.split("-");
      int year = Integer.parseInt(subString[0]);
      int month = Integer.parseInt(subString[1]);
      int day = Integer.parseInt(subString[2]);

      //----------------------------------------------
      //TimeStamp limit
      if (year > 2038) {
        return false;
      } else {
        if (month > 1 && year == 2038) {
          return false;
        }else{
          if (month > 1 && year == 2038 && day > 19) {
            return false;
          }
        }
      }
      //----------------------------------------------

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