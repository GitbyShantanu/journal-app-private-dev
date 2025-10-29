package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository; // Db se SentimentAnalysis via Email opted users fetch karne ke liye custom repo

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 ? * SUN")
    public void fetchUserAndSendSaMail() {
        List<User> usersForSA = userRepository.getUserForSA(); // DB se sentiment analysis enabled users ki list

        for(User user : usersForSA) { // Har user ke liye
            List<JournalEntry> journalEntryList = user.getJournalEntryList(); // User ki saari journal entries

            // Pichle 7 din ki entries ka content nikalo
            List<String> filteredList = journalEntryList.stream()

                    // Filter: Sirf recent entries rakho (pichle 7 din ki)
                    .filter(x -> x.getDate()
                            //Kya entry last 7 days ke baad ki hai? true/false
                            .isAfter(LocalDateTime.now()  // Aaj ka date-time lo Usme se 7 din minus karo
                                    .minus(7, ChronoUnit.DAYS)))

                    .map(x -> x.getContent()) // Sirf content(text) nikalo
                    .collect(Collectors.toList()); // List banao(wrap up)

            // Sab entries ko jodke ek hi string banao
            String entry = String.join(" ", filteredList);
            // AI se mood analyze karo
            String sentiment = sentimentAnalysisService.getSentiment(entry);

            //User ko email bhejo uske weekly mood report ke saath
            emailService.sendEmail(
                    user.getEmail(),  // To: User ka email address
                    "Sentiment for last 7 days",  // Subject: Email ka subject
                    "Your mood in last week was "+ sentiment  // Body: "Your mood was Positive"
            );
        }

    }
}
