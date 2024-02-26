package com.example.SHORT.TERM.TRADING.APP.service;

import com.example.SHORT.TERM.TRADING.APP.entity.Entry;
import com.example.SHORT.TERM.TRADING.APP.entity.Token;
import com.example.SHORT.TERM.TRADING.APP.entity.dto.binding.AddTokenDTO;
import com.example.SHORT.TERM.TRADING.APP.repository.TokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    private final ModelMapper modelMapper;

    public TokenService(TokenRepository tokenRepository, ModelMapper modelMapper) {
        this.tokenRepository = tokenRepository;
        this.modelMapper = modelMapper;
    }

    public void addNewToken(AddTokenDTO addTokenDTO){

        Token token = modelMapper.map(addTokenDTO, Token.class);

        tokenRepository.saveAndFlush(token);
    }

    public Token findTokenByName(String name){
        return tokenRepository.findTokenByName(name).orElseThrow(() -> new RuntimeException("Token with name " + name + " was not found"));
    }
}
