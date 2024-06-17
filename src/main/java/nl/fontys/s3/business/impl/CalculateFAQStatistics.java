package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.domain.FAQStatistics;
import nl.fontys.s3.persistence.ChatlogRepositoryFAKE;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CalculateFAQStatistics {
    private final ChatlogRepositoryFAKE chatlogRepositoryFAKE;
    private final GetChatbotFAQ getChatbotFAQ;

    public List<FAQStatistics> calculateMostAskedFAQs(int topN) {
        // Retrieve all chat logs
        List<ChatEntity> allChatLogs = chatlogRepositoryFAKE.retrieveAllChats();

        // Retrieve the keyword map
        Map<String, List<String>> keywordMap = getChatbotFAQ.getKeywordMap();
        for (Map.Entry<String, List<String>> entry : keywordMap.entrySet()) {
            String category = entry.getKey();
            List<String> keywords = entry.getValue();
            System.out.println("Category: " + category);
            System.out.println("Keywords: " + keywords);
        }

        // Calculate FAQ statistics based on chat logs
        return calculateFAQStatisticsFromChatLogs(allChatLogs, keywordMap, topN);
    }

    private List<FAQStatistics> calculateFAQStatisticsFromChatLogs(List<ChatEntity> chatLogs, Map<String, List<String>> keywordMap, int topN) {
        // Map to store FAQ questions and their counts
        Map<String, Integer> faqQuestionCountMap = new HashMap<>();

        // Count occurrences of each FAQ question
        for (ChatEntity chatLog : chatLogs) {
            for (MessageEntity message : chatLog.getMessages()) {
                // Check if the message is a user question and contains relevant keywords
                boolean isUserMessage = !isBotMessage(message) && isUserQuestion(message.getMessage(), keywordMap);
                if (isUserMessage) {
                    // Replace user question with FAQ question
                    String faqQuestion = findFAQForUserQuestion(message.getMessage(), keywordMap);
                    // Increment count for the FAQ question
                    faqQuestionCountMap.put(faqQuestion, faqQuestionCountMap.getOrDefault(faqQuestion, 0) + 1);
                }
            }
        }

        // Convert map to list of FAQStatistics
        List<FAQStatistics> faqStatisticsList = new ArrayList<>();
        faqQuestionCountMap.forEach((faqQuestion, count) -> faqStatisticsList.add(new FAQStatistics(faqQuestion, count)));

        // Sort the list by count
        faqStatisticsList.sort((f1, f2) -> Integer.compare(f2.getCount(), f1.getCount()));

        // Return top N FAQs
        return faqStatisticsList.subList(0, Math.min(topN, faqStatisticsList.size()));
    }

    private boolean isUserQuestion(String message, Map<String, List<String>> keywordMap) {
        // Check if the message contains any keyword from the keyword map
        for (List<String> keywords : keywordMap.values()) {
            for (String keyword : keywords) {
                if (message.toLowerCase().contains(keyword.toLowerCase()) && keyword.length() >= 3) {
                    return true; // It's a user question
                }
            }
        }
        return false; // It's not a user question
    }

    private boolean isBotMessage(MessageEntity message) {
        UserEntity sender = message.getSendBy();
        // Check if the sender's role indicates a bot
        return sender.getRoles().contains("Customer Service");
    }

    private String findFAQForUserQuestion(String userQuestion, Map<String, List<String>> keywordMap) {
        // Iterate through the FAQ questions and check if the user question contains any relevant keyword
        for (Map.Entry<String, List<String>> entry : keywordMap.entrySet()) {
            String faqQuestion = entry.getKey();
            List<String> keywords = entry.getValue();
            for (String keyword : keywords) {
                if (userQuestion.toLowerCase().contains(keyword.toLowerCase())) {
                    return faqQuestion; // Return the corresponding FAQ question
                }
            }
        }
        return userQuestion; // If no match found, return the original user question
    }

    public int calculateOutOfOfficeChats() {
        List<ChatEntity> allChatLogs = chatlogRepositoryFAKE.retrieveAllChats();
        System.out.println(allChatLogs);
        int outOfOfficeChatCount = 0;

        for (ChatEntity chatLog : allChatLogs) {
            for (MessageEntity message : chatLog.getMessages()) {
                if (isOutOfOfficeHours(message.getDateTime())) {
                    outOfOfficeChatCount++;
                }
            }
        }
        return outOfOfficeChatCount;
    }

    private boolean isOutOfOfficeHours(LocalDateTime timestamp) {
        LocalTime start = LocalTime.of(7, 0);
        LocalTime end = LocalTime.of(18, 0);
        LocalTime messageTime = timestamp.toLocalTime();
        return messageTime.isBefore(start) || messageTime.isAfter(end);
    }

}
