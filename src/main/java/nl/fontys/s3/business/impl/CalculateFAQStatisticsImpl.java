package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CalculateFAQStatistics;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.domain.FAQStatistics;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
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
public class CalculateFAQStatisticsImpl implements CalculateFAQStatistics {
    private final ChatlogRepoJPA chatlogRepoJPA;
    private final GetChatbotFAQ getChatbotFAQ;

    public List<FAQStatistics> calculateMostAskedFAQs(int topN) {
        List<ChatEntity> allChatLogs = chatlogRepoJPA.findAll();
        Map<String, List<String>> keywordMap = getChatbotFAQ.getKeywordMap();

        for (Map.Entry<String, List<String>> entry : keywordMap.entrySet()) {
            String category = entry.getKey();
            List<String> keywords = entry.getValue();
            System.out.println("Category: " + category);
            System.out.println("Keywords: " + keywords);
        }

        return calculateFAQStatisticsFromChatLogs(allChatLogs, keywordMap, topN);
    }

    private List<FAQStatistics> calculateFAQStatisticsFromChatLogs(List<ChatEntity> chatLogs, Map<String, List<String>> keywordMap, int topN) {
        Map<String, Integer> faqQuestionCountMap = new HashMap<>();

        for (ChatEntity chatLog : chatLogs) {
            for (MessageEntity message : chatLog.getMessages()) {
                boolean isUserMessage = !isBotMessage(message) && isUserQuestion(message.getMessage(), keywordMap);
                if (isUserMessage) {
                    String faqQuestion = findFAQForUserQuestion(message.getMessage(), keywordMap);
                    faqQuestionCountMap.put(faqQuestion, faqQuestionCountMap.getOrDefault(faqQuestion, 0) + 1);
                }
            }
        }

        List<FAQStatistics> faqStatisticsList = new ArrayList<>();
        faqQuestionCountMap.forEach((faqQuestion, count) -> faqStatisticsList.add(new FAQStatistics(faqQuestion, count)));
        faqStatisticsList.sort((f1, f2) -> Integer.compare(f2.getCount(), f1.getCount()));

        return faqStatisticsList.subList(0, Math.min(topN, faqStatisticsList.size()));
    }

    private boolean isUserQuestion(String message, Map<String, List<String>> keywordMap) {
        for (List<String> keywords : keywordMap.values()) {
            for (String keyword : keywords) {
                if (message.toLowerCase().contains(keyword.toLowerCase()) && keyword.length() >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isBotMessage(MessageEntity message) {
        UserEntity sender = message.getSendBy();
        return sender.getRoles().contains("Customer Service");
    }

    private String findFAQForUserQuestion(String userQuestion, Map<String, List<String>> keywordMap) {
        for (Map.Entry<String, List<String>> entry : keywordMap.entrySet()) {
            String faqQuestion = entry.getKey();
            List<String> keywords = entry.getValue();
            for (String keyword : keywords) {
                if (userQuestion.toLowerCase().contains(keyword.toLowerCase())) {
                    return faqQuestion;
                }
            }
        }
        return userQuestion;
    }

    public int calculateOutOfOfficeChats() {
        List<ChatEntity> allChatLogs = chatlogRepoJPA.findAll();
        int outOfOfficeChatCount = 0;

        for (ChatEntity chatLog : allChatLogs) {
            for (MessageEntity message : chatLog.getMessages()) {
                if (message.getDateTime() != null && isOutOfOfficeHours(message.getDateTime())) {
                    outOfOfficeChatCount++;
                }
            }
        }
        return outOfOfficeChatCount;
    }

    private boolean isOutOfOfficeHours(LocalDateTime timestamp) {
        if (timestamp == null) {
            return false;
        }
        LocalTime start = LocalTime.of(7, 0);
        LocalTime end = LocalTime.of(18, 0);
        LocalTime messageTime = timestamp.toLocalTime();
        return messageTime.isBefore(start) || messageTime.isAfter(end);
    }

    public List<String> getFailedQuestions() {
        List<ChatEntity> allChatLogs = chatlogRepoJPA.findAll();
        List<String> failedQuestions = new ArrayList<>();

        for (ChatEntity chatLog : allChatLogs) {
            for (MessageEntity message : chatLog.getMessages()) {
                if (!message.getChat().isOpen()) {
                    failedQuestions.add(message.getMessage());
                }
            }
        }

        return failedQuestions;
    }
}
