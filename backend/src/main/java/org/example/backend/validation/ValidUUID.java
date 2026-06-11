package org.example.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}",
        message = "The id must be a valid uuid")
public @interface ValidUUID {
    String message() default "The id must be a valid uuid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}