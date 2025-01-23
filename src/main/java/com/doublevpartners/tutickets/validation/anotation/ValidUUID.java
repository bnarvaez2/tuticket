package com.doublevpartners.tutickets.validation.anotation;

import com.doublevpartners.tutickets.validation.validator.UUIDValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UUIDValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
  String message() default "Invalid UUID";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
