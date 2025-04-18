package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly_sentiments", groupId = "weekly_sentiments_group")
    public void consume(SentimentData sentimentData){
        sendEmails(sentimentData);
    }

    private void sendEmails(SentimentData sentimentData){
        emailService.sendEmail(sentimentData.getEmail(), "sentiment for 5 sec", sentimentData.getSentiment());
    }


}
