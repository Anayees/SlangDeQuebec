package com.example.slangdequebec.service;

import com.example.slangdequebec.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.slangdequebec.repository.WordRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    public Word addWord(Word word) {
        word.setId(UUID.randomUUID().toString().split("-")[0]);
        return wordRepository.save(word);
    }

    public Word findWord(String word) {
        return wordRepository.findByWord(word);
    }

    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    public Optional<Word> findById(String id) {
        return wordRepository.findById(id);
    }

    public Word findByWord(String word) {
        System.out.println(word);
        return wordRepository.findByWord(word);
    }

    public List<Word> findWordByOrigin(String origin) {
        return wordRepository.findWordByOrigin(origin);
    }

    public Word save(Word word) {
        if (word.getId() == null) {
            word.setId(UUID.randomUUID().toString().split("-")[0]);
        }
        return wordRepository.save(word);
    }

    public boolean existsById(String id) {
        return wordRepository.existsById(id);
    }

    public void deleteById(String id) {
        wordRepository.deleteById(id);
    }
}
