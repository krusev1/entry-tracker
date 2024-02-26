package com.example.SHORT.TERM.TRADING.APP.validations.tokenAddressIsNotRegistered;

import com.example.SHORT.TERM.TRADING.APP.repository.TokenRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TokenAddressIsNotRegisteredChecker implements ConstraintValidator<TokenAddressIsNotRegisteredCheck, String> {

    private final TokenRepository tokenRepository;

    public TokenAddressIsNotRegisteredChecker(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void initialize(TokenAddressIsNotRegisteredCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String address, ConstraintValidatorContext constraintValidatorContext) {
        return tokenRepository.findTokenByAddress(address).isEmpty();
    }

}
