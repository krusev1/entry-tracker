package com.example.SHORT.TERM.TRADING.APP.repository;

import com.example.SHORT.TERM.TRADING.APP.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findAllByIsOpen(boolean flag);
}
