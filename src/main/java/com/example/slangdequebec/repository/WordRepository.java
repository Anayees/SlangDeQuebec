package com.example.slangdequebec.repository;

import com.example.slangdequebec.model.Word;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WordRepository extends MongoRepository <Word, String> {

    @Query("{word: '?0'}")
    Word findByWord(String word);
    Word findByWordIgnoreCase(String word);

    List<Word> findWordByOrigin(String origin);
}
