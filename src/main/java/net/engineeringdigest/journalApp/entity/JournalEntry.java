package net.engineeringdigest.journalApp.entity;

import lombok.*;
import net.engineeringdigest.journalApp.enums.Sentiments;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("Journal_Entries")
@Data // @Getter @Setter @constructors equals() hashcode() toString()  by using the @Data annotation"
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    private String journalTitle;
    private String content;
    private LocalDateTime date;
    private Sentiments sentiments;

}
