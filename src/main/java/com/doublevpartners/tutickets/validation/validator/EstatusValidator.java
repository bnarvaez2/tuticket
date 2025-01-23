package com.doublevpartners.tutickets.validation.validator;

import com.doublevpartners.tutickets.util.EstatusEnum;
import com.doublevpartners.tutickets.validation.anotation.ValidEstatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstatusValidator implements ConstraintValidator<ValidEstatus, String> {

  @Override
  public void initialize(ValidEstatus constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) return false;

    for (EstatusEnum estatus : EstatusEnum.values()) {
      if (estatus.name().equals(value)) return true;
    }

    return false;
  }
}

