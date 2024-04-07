package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "words")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    private String id;
    private String word;
    private String gender;
    private String origin;
    private String partsOfSpeech;
    private String usage;
    private String definition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", gender=" + gender +
                ", origin=" + origin +
                ", partsOfSpeech=" + partsOfSpeech +
                ", usage=" + usage +
                ", definition='" + definition + '\'' +
                '}';
    }
}
