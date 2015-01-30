package com.excilys.formation.java.validator;

import java.util.Map;
import java.util.regex.Pattern;

import com.excilys.formation.java.dto.ComputerDto;

public class Validator {

  private static final String DATE_REGEX = "^[1-2][0-9]{3}-([0][1-9]|1[0-2])-([0-2][1-9]|3[0-1])$";

  private static final String ID_REGEX   = "^[1-9]+$";

  /**
   * Check if a String is a valid date
   * @param input String to check
   * @return true if the format is correct
   */
  public static boolean isDate(String input) {

    if (input == null || input.trim().isEmpty()) {
      return false;
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

  /**
   * Check if a String is a valid ID
   * @param string String to check
   * @return true if the format is correct
   */
  public static boolean isID(String input) {

    if (input == null || input.trim().isEmpty()) {
      return false;
    }

    Pattern regex = Pattern.compile(ID_REGEX);
    if (regex.matcher(input).find()) {
      return true;
    } else {
      return false;
    }
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
    Pattern regex = Pattern.compile(ID_REGEX);
    if (regex.matcher(input).find()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if the computerDto is valid
   * @param dto Dto to validate
   * @return True if valid, false otherwise
   */
  public static boolean isComputerDTO(ComputerDto dto) {
    if (dto == null) {
      return false;
    }
    if (dto.getId() < 0) {
      return false;
    }
    if (!isName(dto.getName())) {
      return false;
    }
    if (dto.getIntroduced() != null) {
      if (!isDate(dto.getIntroduced())) {
        return false;
      }
    }
    if (dto.getDiscontinued() != null) {
      if (!isDate(dto.getDiscontinued())) {
        return false;
      }
    }
    if (dto.getIdCompany() < 0) {
       return false;
    }

    return true;
  }

  /**
   * Check if the computerDto is valid and fill an error-map when not valid
   * @param dto Dto to validate
   * @param error
   * @return True if the dto is valid, false otherwise
   */
  public static boolean isComputerDTO(ComputerDto dto, Map<String, String> error) {
    if (dto == null) {
      return false;
    }
    if (dto.getId() < 0) {
      return false;
    }
    if (!isName(dto.getName())) {
      error.put("name", "Incorrect name : you must enter a name");
    }
    if (dto.getIntroduced() != null) {
      if (!isDate(dto.getIntroduced())) {
        error.put("introduced", "Incorrect date format : yyyy-mm-dd");
      }
    }
    if (dto.getDiscontinued() != null) {
      if (!isDate(dto.getDiscontinued())) {
        error.put("discontinued", "Incorrect date format : yyyy-mm-dd");
      }
    }
    if (dto.getIdCompany() <0) {
       error.put("companyId", "Incorrect Company identifier");
    }

    if (error.isEmpty())
      return true;
    else
      return false;
  }

}
