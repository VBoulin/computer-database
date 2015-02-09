package com.excilys.formation.java.util;

import java.util.Map;

import org.apache.commons.validator.GenericValidator;

import com.excilys.formation.java.dto.ComputerDto;

public class Validator {

  /**
   * Check if a String is a valid date
   * @param input String to check
   * @return true if the format is correct
   */
  public static boolean isDate(String input) {
    if (input == null) {
      return false;
    }
    if (input.trim().isEmpty()) {
      return true;
    }

    return GenericValidator.isDate(input, "yyyy-MM-dd", true);
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
    if (dto.getIdCompany() < 0) {
      error.put("companyId", "Incorrect Company identifier");
    }

    if (error.isEmpty())
      return true;
    else
      return false;
  }

}
