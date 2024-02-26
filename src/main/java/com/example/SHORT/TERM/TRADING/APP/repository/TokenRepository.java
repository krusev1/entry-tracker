package com.example.SHORT.TERM.TRADING.APP.repository;

import com.example.SHORT.TERM.TRADING.APP.entity.Entry;
import com.example.SHORT.TERM.TRADING.APP.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findTokenByName(String name);
    Optional<Token> findTokenByAddress(String address);
}
