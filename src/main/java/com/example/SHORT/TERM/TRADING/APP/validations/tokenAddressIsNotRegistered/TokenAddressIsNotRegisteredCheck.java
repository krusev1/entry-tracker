package com.example.SHORT.TERM.TRADING.APP.validations.tokenAddressIsNotRegistered;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TokenAddressIsNotRegisteredChecker.class)
public @interface TokenAddressIsNotRegisteredCheck {
    String message() default "Token address is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
