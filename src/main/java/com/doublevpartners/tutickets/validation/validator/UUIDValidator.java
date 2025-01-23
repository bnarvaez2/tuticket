package com.doublevpartners.tutickets.validation.validator;

import static com.doublevpartners.tutickets.util.Constants.UUID_REGEX;

import com.doublevpartners.tutickets.validation.anotation.ValidUUID;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {
  private static final Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX);

  @Override
  public void initialize(ValidUUID constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) return false;

    return UUID_PATTERN.matcher(value).matches();
  }
}

