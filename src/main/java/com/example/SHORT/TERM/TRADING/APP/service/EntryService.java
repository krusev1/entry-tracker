package com.example.SHORT.TERM.TRADING.APP.service;

import com.example.SHORT.TERM.TRADING.APP.entity.Entry;
import com.example.SHORT.TERM.TRADING.APP.entity.Token;
import com.example.SHORT.TERM.TRADING.APP.entity.dto.binding.AddEntryDTO;
import com.example.SHORT.TERM.TRADING.APP.entity.dto.binding.CloseEntryDTO;
import com.example.SHORT.TERM.TRADING.APP.entity.dto.view.EntryViewDTO;
import com.example.SHORT.TERM.TRADING.APP.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntryService {

    private final EntryRepository entryRepository;

    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    public EntryService(EntryRepository entryRepository, TokenService tokenService, ModelMapper modelMapper) {
        this.entryRepository = entryRepository;
        this.tokenService = tokenService;
        this.modelMapper = modelMapper;
    }

    public void addNewEntry(AddEntryDTO addEntryDTO){

        Token tokenByName = tokenService.findTokenByName(addEntryDTO.getName());

        Entry entry = modelMapper.map(addEntryDTO, Entry.class);

        entry.setToken(tokenByName);

        entryRepository.saveAndFlush(entry);
    }

    public void closeEntry(CloseEntryDTO closeEntryDTO){

        Entry entryById = this.findEntryById(closeEntryDTO.getId());

        entryById.setExitPrice(closeEntryDTO.getExitPrice());
        entryById.setTotalExitAmount(closeEntryDTO.getTotalExitAmount());
        entryById.setOpen(false);
        entryById.setExitDate(LocalDateTime.now());

        entryRepository.saveAndFlush(entryById);
    }

    public Entry findEntryById(Long id){
        return entryRepository.findById(id).orElseThrow(() -> new RuntimeException("entry with id " + id + " was not found"));
    }

    public List<Entry> findAllOpenEntries(){
        return entryRepository.findAllByIsOpen(true);
    }

    public List<EntryViewDTO> findAllOpenEntryViewDTOs(){
        return this.findAllOpenEntries().stream().map(entry -> modelMapper.map(entry, EntryViewDTO.class)).toList();
    }

    public EntryViewDTO getEntryViewDTOById(Long id){
        return modelMapper.map(findEntryById(id), EntryViewDTO.class);
    }
}
