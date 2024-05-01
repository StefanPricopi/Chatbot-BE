package nl.fontys.s3.business.impl;

import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeywordMapGenerator {

    private final ChatbotFAQJpaRepository faqRepository;

    @Autowired
    public KeywordMapGenerator(ChatbotFAQJpaRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public Map<String, List<String>> generateKeywordMapFromDatabase() {
        List<ChatbotFAQEntity> faqs = faqRepository.findAll();
        System.out.println("Retrieved " + faqs.size() + " FAQs from the database");

        return generateKeywordMap(faqs);
    }

    private Map<String, List<String>> generateKeywordMap(List<ChatbotFAQEntity> faqs) {
        Map<String, List<String>> keywordMap = new HashMap<>();

        for (ChatbotFAQEntity faq : faqs) {
            String category = faq.getCategory();
            String question = faq.getQuestion();

            List<String> keywords = extractKeywords(question);

            // Update the keyword map
            keywordMap.computeIfAbsent(category, k -> new ArrayList<>()).addAll(keywords);
        }

        // Print each category and its associated keywords
        for (Map.Entry<String, List<String>> entry : keywordMap.entrySet()) {
            System.out.println("Category: " + entry.getKey());
            System.out.println("Keywords: " + entry.getValue());
        }

        return keywordMap;
    }

    private List<String> extractKeywords(String question) {
        // Split the question text by whitespace
        String[] tokens = question.split("\\s+");

        // Create a set to store unique keywords
        Set<String> uniqueKeywords = new HashSet<>();

        for (String token : tokens) {
            // Remove punctuation and convert to lowercase
            String cleanedToken = token.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

            // Skip empty tokens
            if (!cleanedToken.isEmpty()) {
                uniqueKeywords.add(cleanedToken);
            }
        }

        // Convert set to list and return
        return new ArrayList<>(uniqueKeywords);
    }
}
