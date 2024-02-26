package com.example.SHORT.TERM.TRADING.APP.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String name;

    @Column (nullable = false, unique = true)
    private String address;

    @Column (nullable = false)
    private String graphUrl;

    @OneToMany(mappedBy = "token")
    private List<Entry> entries;
}
