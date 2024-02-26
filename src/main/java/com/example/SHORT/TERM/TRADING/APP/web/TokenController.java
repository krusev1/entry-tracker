package com.example.SHORT.TERM.TRADING.APP.web;

import com.example.SHORT.TERM.TRADING.APP.entity.dto.binding.AddEntryDTO;
import com.example.SHORT.TERM.TRADING.APP.entity.dto.binding.AddTokenDTO;
import com.example.SHORT.TERM.TRADING.APP.service.EntryService;
import com.example.SHORT.TERM.TRADING.APP.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    private final Validator validator;

    public TokenController(Validator validator, TokenService tokenService) {
        this.validator = validator;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/add-new-token")
    public Mono<ResponseEntity<?>> postNewToken(@RequestBody Mono<AddTokenDTO> addTokenDTO){

        return addTokenDTO
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, "addTokenDTO");

                    validator.validate(dto, errors);

                    if (errors.hasErrors()){
                        Map<String, String> map = new HashMap<>();
                        errors.getFieldErrors().forEach(objectError -> map.put(objectError.getField(), objectError.getDefaultMessage()));
                        return Mono.just(ResponseEntity.badRequest().body(map));
                    }
                    else {
                        tokenService.addNewToken(dto);
                        return Mono.just(ResponseEntity.ok().build());
                    }
                });
    }
}
