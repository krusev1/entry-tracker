package com.example.SHORT.TERM.TRADING.APP.web;

import com.example.SHORT.TERM.TRADING.APP.entity.dto.binding.AddEntryDTO;
import com.example.SHORT.TERM.TRADING.APP.entity.dto.binding.CloseEntryDTO;
import com.example.SHORT.TERM.TRADING.APP.entity.dto.view.EntryViewDTO;
import com.example.SHORT.TERM.TRADING.APP.service.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/entry")
public class EntryController {
    private final EntryService entryService;

    private final Validator validator;

    public EntryController(Validator validator, EntryService entryService) {
        this.validator = validator;
        this.entryService = entryService;
    }

    @PostMapping("/api/add-new-entry")
    public Mono<ResponseEntity<?>> postNewEntry(@RequestBody Mono<AddEntryDTO> addEntryDTO) {

        return addEntryDTO
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, "addEntryDTO");

                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        Map<String, String> map = new HashMap<>();
                        errors.getFieldErrors().forEach(objectError -> map.put(objectError.getField(), objectError.getDefaultMessage()));
                        return Mono.just(ResponseEntity.badRequest().body(map));
                    } else {
                        entryService.addNewEntry(dto);
                        return Mono.just(ResponseEntity.ok().build());
                    }
                });
    }

    @GetMapping("/api/get-all-open-entries")
    public ResponseEntity<List<EntryViewDTO>> getAllOpenViewDTOs() {

        List<EntryViewDTO> allOpenEntryViewDTOs = entryService.findAllOpenEntryViewDTOs();

        return ResponseEntity.ok(allOpenEntryViewDTOs);
    }

    @PatchMapping("/api/close-entry")
    public Mono<ResponseEntity<?>> patchCloseEntry(@RequestBody Mono<CloseEntryDTO> closeEntryDTO) {

        return closeEntryDTO
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, "closeEntryDTO");

                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        Map<String, String> map = new HashMap<>();
                        errors.getFieldErrors().forEach(objectError -> map.put(objectError.getField(), objectError.getDefaultMessage()));
                        return Mono.just(ResponseEntity.badRequest().body(map));
                    } else {
                        entryService.closeEntry(dto);
                        return Mono.just(ResponseEntity.ok().build());
                    }
                });
    }
}
