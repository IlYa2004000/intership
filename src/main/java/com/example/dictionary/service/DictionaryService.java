package com.example.dictionary.service;

import com.example.dictionary.model.Dictionary;
import com.example.dictionary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public Dictionary createDictionary(Dictionary dictionary) {
        return dictionaryRepository.save(dictionary);
    }

    public List<Dictionary> getAllDictionaries() {
        return dictionaryRepository.findAll();
    }

    public Dictionary getDictionaryById(UUID id) {
        return dictionaryRepository.findById(id).orElse(null);
    }

    public void deleteDictionary(UUID id) {
        dictionaryRepository.deleteById(id);
    }
}