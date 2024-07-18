package com.example.dictionary.repository;

import com.example.dictionary.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<Dictionary, UUID> {
}