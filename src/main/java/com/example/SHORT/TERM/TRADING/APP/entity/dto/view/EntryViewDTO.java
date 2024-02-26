package com.example.SHORT.TERM.TRADING.APP.entity.dto.view;

import com.example.SHORT.TERM.TRADING.APP.entity.Token;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class EntryViewDTO {

    private Long id;

    private String tokenName;
    private BigDecimal tokenAmount;

    private String boughtWith;

    private Float totalEntryAmount;

    private Float entryPrice;

    private Float exitPrice;

    private Float totalExitAmount;

    private String tokenTx;

    private String exchangeUrl;

    private String tokengraphUrl;

    private boolean open = true;
}
