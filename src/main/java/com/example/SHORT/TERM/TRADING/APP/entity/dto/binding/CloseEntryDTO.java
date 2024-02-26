package com.example.SHORT.TERM.TRADING.APP.entity.dto.binding;

import com.example.SHORT.TERM.TRADING.APP.validations.TokenIsRegisteredCheck.TokenIsRegisteredCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@NoArgsConstructor
public class CloseEntryDTO {

    @NotNull
    private Long id;

    @NotNull(message = "the amount cannot be null")
    private Float totalExitAmount;

//    @NotNull (message = "the entry price cannot be null")
    @Positive (message = "the entry price has to be positive")
    @NumberFormat
    private Float exitPrice;

}
