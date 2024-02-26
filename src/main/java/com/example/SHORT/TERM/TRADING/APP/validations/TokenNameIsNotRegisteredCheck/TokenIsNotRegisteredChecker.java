package com.example.SHORT.TERM.TRADING.APP.validations.TokenNameIsNotRegisteredCheck;

import com.example.SHORT.TERM.TRADING.APP.repository.TokenRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TokenIsNotRegisteredChecker implements ConstraintValidator<TokenIsNotRegisteredCheck, String> {

    private final TokenRepository tokenRepository;

    public TokenIsNotRegisteredChecker(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void initialize(TokenIsNotRegisteredCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return tokenRepository.findTokenByName(name).isEmpty();
    }

}
