package net.engineeringdigest.journalApp.sheduler;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiments;
import net.engineeringdigest.journalApp.model.SentimentData;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserSheduler {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

//    @Scheduled(cron="*/5 * * * * *")
    public void fetchUserAndSendMail(){
        List<User> users = userRepository.getUserSA();
        for (User  user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

            log.debug(":User  {}, Total Journal Entries: {}", user.getUserName(), journalEntries.size());

            // Map sentiments directly from all journal entries
            List<Sentiments> sentiments = journalEntries.stream()
                    .map(JournalEntry::getSentiments)
                    .filter(sentiment -> sentiment != null) // Only include non-null sentiments
                    .collect(Collectors.toList());

            log.debug("Mapped Sentiments: {}", sentiments);

            // Count occurrences of each sentiment
            Map<Sentiments, Integer> sentimentCounts = new HashMap<>();
            for (Sentiments sentiment : sentiments) {
                sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }

            // Find the most frequent sentiment
            Sentiments mostFrequentCount = null;
            int max = 0;
            for (Map.Entry<Sentiments, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    mostFrequentCount = entry.getKey();
                }
            }

            // Send email if a most frequent sentiment is found
            if (mostFrequentCount != null) {
                log.debug("Most frequent sentiment: {}", mostFrequentCount);
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("weekly_sentiments " + mostFrequentCount).build();
                kafkaTemplate.send("weekly_sentiments",sentimentData.getEmail(),sentimentData);
            }
        }
    }
}
