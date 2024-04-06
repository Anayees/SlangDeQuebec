package controller;

import model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.WordService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/words")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping
    public List<Word> getAllWords() {

        return wordService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Word> getWordById(@PathVariable String id) {
        return wordService.findById(id);
    }

    @GetMapping("/origin/{origin}")
    public List<Word> findWordByOrigin(@PathVariable String origin) {
        return wordService.findWordByOrigin(origin);
    }

//    @GetMapping("/{word}")
//    public ResponseEntity<Word> getByWord(@PathVariable String word) {
//        try{
//            System.out.println("Word: ");
//            Word foundWord = wordService.findByWord(word);
//            System.out.println(word);
//            return ResponseEntity.ok(foundWord);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }

    @PostMapping
    public ResponseEntity<Word> addWord(@RequestBody Word word) {
        Word savedWord = wordService.save(word);
        return new ResponseEntity<>(savedWord, HttpStatus.CREATED);
    }
}
