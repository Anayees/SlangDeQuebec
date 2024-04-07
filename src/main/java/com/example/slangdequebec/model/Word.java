package com.example.slangdequebec.model;

import com.example.slangdequebec.util.Gender;
import com.example.slangdequebec.util.Origin;
import com.example.slangdequebec.util.PartsOfSpeech;
import com.example.slangdequebec.util.Usage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "words")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    private String id;
    @NonNull
    private String word;
    private Gender gender;
    private Origin origin;
    private PartsOfSpeech partsOfSpeech;
    private Usage usage;
    private String definition;

}
