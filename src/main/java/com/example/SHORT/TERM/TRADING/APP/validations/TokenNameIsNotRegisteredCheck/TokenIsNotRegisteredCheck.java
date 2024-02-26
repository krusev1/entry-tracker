package com.example.SHORT.TERM.TRADING.APP.validations.TokenNameIsNotRegisteredCheck;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TokenIsNotRegisteredChecker.class)
public @interface TokenIsNotRegisteredCheck {
    String message() default "Token is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
