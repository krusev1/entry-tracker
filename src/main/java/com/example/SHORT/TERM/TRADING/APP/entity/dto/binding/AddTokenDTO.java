package com.example.SHORT.TERM.TRADING.APP.entity.dto.binding;

import com.example.SHORT.TERM.TRADING.APP.validations.TokenNameIsNotRegisteredCheck.TokenIsNotRegisteredCheck;
import com.example.SHORT.TERM.TRADING.APP.validations.tokenAddressIsNotRegistered.TokenAddressIsNotRegisteredCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddTokenDTO {

    @NotBlank
    @TokenIsNotRegisteredCheck
    private String name;

    @NotBlank
    @TokenAddressIsNotRegisteredCheck
    private String address;

    @NotBlank
    private String graphUrl;
}
