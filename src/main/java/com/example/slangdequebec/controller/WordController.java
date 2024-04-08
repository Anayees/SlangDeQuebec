package com.example.slangdequebec.controller;

import com.example.slangdequebec.model.Word;
import com.example.slangdequebec.util.Gender;
import com.example.slangdequebec.util.Origin;
import com.example.slangdequebec.util.PartsOfSpeech;
import com.example.slangdequebec.util.Usage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.slangdequebec.service.WordService;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/words")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping
    public ResponseEntity<?> getAllWords() {
        List<Word> words = wordService.findAll();

        if (words.isEmpty()) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "There are no words to be found.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.ok(words);
        }
    }

    @GetMapping("/filter/origin/{origin}")
    public ResponseEntity<?> findWordByOrigin(@PathVariable String origin) {

        try {
            Origin foundOrigin = Origin.valueOf(origin.toUpperCase());
            List<Word> words = wordService.findWordByOrigin(foundOrigin.toString());
            if (words.isEmpty()) {
                return new ResponseEntity<>("There aren't any words found with the origin: '" + origin + "'.", HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(words);
            }
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("There aren't any words found with the origin: '" + origin + ", because this origin does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/usage/{usage}")
    public ResponseEntity<?> findWordByUsage(@PathVariable String usage) {

        try {
            Usage foundUsage = Usage.valueOf(usage.toUpperCase());
            List<Word> words = wordService.findWordByUsage(foundUsage.toString());
            if (words.isEmpty()) {
                return new ResponseEntity<>("There aren't any words found with the usage: '" + usage + "'.", HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(words);
            }
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>("There aren't any words found with the usage: '" + usage + ", because this usage does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/filtered-alphabetically")
    public ResponseEntity<?> findWordsFilteredAlphabetically() {
        List<Word> words = wordService.findWordsFilteredAlphabetically();
        if (words.isEmpty()) {
            return new ResponseEntity<>("No words found.", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(words);
        }
    }

    @GetMapping("/filter/letter/{letter}")
    public ResponseEntity<?> findWordsByLetter(@PathVariable char letter) {
        try {
            List<Word> words = wordService.findWordsByLetter(letter);
            if (words.isEmpty()) {
                return new ResponseEntity<>("No words found starting with letter: '" + letter + "'.", HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(words);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid character: '" + letter + "'.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{word}")
    public ResponseEntity<Word> getByWord(@PathVariable String word) {

        try{
            Word foundWord = wordService.findByWord(word);
            return ResponseEntity.ok(foundWord);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addWord(@RequestBody Word word) {

        Word existingWord = wordService.findWord(word.getWord());

        if (existingWord != null) {
            return new ResponseEntity<>("The word '" + existingWord.getWord() + "' already exists in the dictionary of Slang De Quebec.", HttpStatus.CONFLICT);
        } else {
            Word savedWord = wordService.addWord(word);
            return new ResponseEntity<>(savedWord, HttpStatus.CREATED);
        }
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> updateWord(@PathVariable String id, @RequestBody Map<String, Object> updates) {

        try {
            Optional<Word> optionalExistingWord  = wordService.findById(id);

            if (!optionalExistingWord .isPresent()) {
                return new ResponseEntity<>("Word with ID '" + id + "' not found", HttpStatus.NOT_FOUND);
            }

            Word existingWord = optionalExistingWord.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "word": existingWord.setWord((String) value); break;
                    case "gender":
                        try {
                            Gender gender = Gender.valueOf(((String) value).toUpperCase());
                            existingWord.setGender(gender);
                        } catch (IllegalArgumentException e) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid gender value: " + value);
                        }
                        break;
                    case "origin":
                        try {
                            Origin origin = Origin.valueOf(((String) value).toUpperCase());
                            existingWord.setOrigin(origin);
                        } catch (IllegalArgumentException e) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid origin value: " + value);
                        }
                        break;
                    case "partsOfSpeech":
                        try {
                            PartsOfSpeech partsOfSpeech = PartsOfSpeech.valueOf(((String) value).toUpperCase());
                            existingWord.setPartsOfSpeech(partsOfSpeech);
                        } catch (IllegalArgumentException e) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parts of speech value: " + value);
                        }
                        break;
                    case "usage":
                        try {
                            Usage usage = Usage.valueOf(((String) value).toUpperCase());
                            existingWord.setUsage(usage);
                        } catch (IllegalArgumentException e) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid usage value: " + value);
                        }
                        break;
                    case "definition": existingWord.setDefinition((String) value); break;
                }
            });
            final Word updatedWord = wordService.save(existingWord);
            existingWord.setId(id);
            return ResponseEntity.ok(updatedWord);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The word with the id '" + id + "' is not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWordById(@PathVariable String id) {

        boolean exists = wordService.existsById(id);

        if (!exists) {
            return new ResponseEntity<>("Word cannot be deleted, because its ID does not exist.", HttpStatus.NOT_FOUND);
        }
        wordService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
