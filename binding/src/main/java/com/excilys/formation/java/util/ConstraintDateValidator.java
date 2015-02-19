package com.excilys.formation.java.util;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Custom validator used by an annotation
 * @author Vincent
 */
@Component
public class ConstraintDateValidator implements ConstraintValidator<DateValidator, String> {

  @Autowired
  private MessageSource messageSource;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(DateValidator arg0) {}

  /**
   * Check if a String is a valid date
   * @param input String to check
   * @return true if the format is correct or if the string is empty
   */
  @Override
  public boolean isValid(String input, ConstraintValidatorContext context) {
    Locale locale = LocaleContextHolder.getLocale();
    String format = messageSource.getMessage("dateFormat", null, locale);
    return Validator.isDate(input, format);
  }

}