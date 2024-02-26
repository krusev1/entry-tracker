package com.example.SHORT.TERM.TRADING.APP.validations.TokenIsRegisteredCheck;

import com.example.SHORT.TERM.TRADING.APP.repository.TokenRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TokenIsRegisteredChecker implements ConstraintValidator<TokenIsRegisteredCheck, String> {

    private final TokenRepository tokenRepository;

    public TokenIsRegisteredChecker(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void initialize(TokenIsRegisteredCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return tokenRepository.findTokenByName(name).isPresent();
    }

}
