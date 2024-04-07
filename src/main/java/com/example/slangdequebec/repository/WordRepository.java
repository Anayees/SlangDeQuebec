package repository;

import model.Word;
import org.bson.internal.BsonUtil;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WordRepository extends MongoRepository <Word, String> {

    @Query("{word: '?0'}")
    Word findByWord(String word);
    Word findByWordIgnoreCase(String word);

    List<Word> findWordByOrigin(String origin);
}
