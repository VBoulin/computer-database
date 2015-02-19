package com.excilys.formation.java.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Create a custom annotation for validation
 * @author Vincent
 */
@Documented
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConstraintDateValidator.class)
public @interface DateValidator {

  String message() default "Invalid Date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
