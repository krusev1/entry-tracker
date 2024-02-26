package com.example.SHORT.TERM.TRADING.APP.entity.dto.binding;

import com.example.SHORT.TERM.TRADING.APP.entity.Token;
import com.example.SHORT.TERM.TRADING.APP.validations.TokenIsRegisteredCheck.TokenIsRegisteredCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AddEntryDTO {

    @NotBlank (message = "you must enter a token")
    @TokenIsRegisteredCheck
    private String name;

    @NotNull(message = "the amount cannot be null")
    @Positive(message = "the amount has to be positive")
    private BigDecimal tokenAmount;

    @NotBlank (message = "you must enter a token")
    private String boughtWith;

    @NotNull (message = "the total entry cannot be null")
    @Positive (message = "the total entry has to be positive")
    private Float totalEntryAmount;

//    @NotNull (message = "the entry price cannot be null")
    @Positive (message = "the entry price has to be positive")
    @NumberFormat
    private Float entryPrice;
    private Float exitPrice;
    private String tokenTx;

    private String exchangeUrl;
}
