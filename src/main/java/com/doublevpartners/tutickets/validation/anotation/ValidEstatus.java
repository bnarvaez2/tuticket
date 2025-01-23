package com.doublevpartners.tutickets.validation.anotation;

import com.doublevpartners.tutickets.validation.validator.EstatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EstatusValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEstatus {
  String message() default "Invalid estatus";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

