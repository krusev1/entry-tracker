package com.example.SHORT.TERM.TRADING.APP.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Token token;
    @Column (nullable = false, precision = 38, scale = 20)
    private BigDecimal tokenAmount;

    private String boughtWith;

    @Column (nullable = false)
    private Float totalEntryAmount;

//    @Column (nullable = false)
    private Float entryPrice;

    private Float totalExitAmount;

    private Float exitPrice;

    @Column (columnDefinition = "TEXT")
    private String tokenTx;

    private String exchangeUrl;

    private LocalDateTime entryDate = LocalDateTime.now();

    private LocalDateTime exitDate;

    private boolean isOpen = true;



}
