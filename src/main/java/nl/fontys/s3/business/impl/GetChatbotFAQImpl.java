package nl.fontys.s3.business.impl;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.business.impl.FAQConverter;
import nl.fontys.s3.business.impl.KeywordMapGenerator;
import nl.fontys.s3.domain.ChatbotFAQ;
import nl.fontys.s3.domain.GetAllChatbotFAQResponse;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetChatbotFAQImpl implements GetChatbotFAQ {

    private final ChatbotFAQJpaRepository faqRepository;
    private final KeywordMapGenerator keywordMapGenerator;

    private Map<String, List<String>> keywordMap;
    private Map<String, Integer> keywordWeights;

    @PostConstruct
    public void init() {
        // Generate keyword map and weights dynamically during initialization
        keywordMap = keywordMapGenerator.generateKeywordMapFromDatabase();
        keywordWeights = loadKeywordWeights(); // Assuming you have a method to load weights from configuration or database
    }

    @Override
    public GetAllChatbotFAQResponse getFAQ() {
        List<ChatbotFAQEntity> results = faqRepository.findAll();
        List<ChatbotFAQ> faqs = results.stream()
                .map(FAQConverter::convert)
                .collect(Collectors.toList());

        final GetAllChatbotFAQResponse response = new GetAllChatbotFAQResponse();
        response.setChatbotFAQS(faqs);

        return response;
    }

    @Override
    public String processUserQuery(String userInput) {
        // Preprocess user input (optional)
        String processedInput = userInput.toLowerCase();

        // Track the FAQ with the maximum matching score
        ChatbotFAQEntity maxMatchedFAQ = null;
        int maxMatchingScore = 0;

        // Retrieve all FAQs from the database
        List<ChatbotFAQEntity> allFaqs = faqRepository.findAll();

        // Iterate through all FAQs
        for (ChatbotFAQEntity faq : allFaqs) {
            // Calculate matching score for the current FAQ
            int matchingScore = calculateMatchingScoreWithWeights(processedInput, faq.getQuestion());

            // Update maxMatchedFAQ if the current FAQ has a higher matching score
            if (matchingScore > maxMatchingScore) {
                maxMatchingScore = matchingScore;
                maxMatchedFAQ = faq;
            }
        }

        // If a FAQ with matching keywords is found, return its answer
        if (maxMatchedFAQ != null) {
            return maxMatchedFAQ.getAnswer();
        }

        return "Sorry, I couldn't find an answer to your question.";
    }



    // Method to check if the input contains any of the specified keywords
    private boolean containsAnyKeyword(String input, List<String> keywords) {
        for (String keyword : keywords) {
            if (input.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    // Method to retrieve FAQs containing a specific keyword
    public List<ChatbotFAQEntity> getFAQsByKeyword(String keyword) {
        return faqRepository.findByQuestionContainingIgnoreCase(keyword);
    }

    // Method to calculate matching score with keyword weights
    private int calculateMatchingScoreWithWeights(String input, String question) {
        int score = 0;
        String[] tokens = question.toLowerCase().split("\\s+");
        Set<String> processedKeywords = new HashSet<>();

        // Check for presence of keywords in the input and calculate score
        for (String token : tokens) {
            if (input.contains(token) && !processedKeywords.contains(token)) {
                processedKeywords.add(token); // Add processed keyword to set
                int keywordWeight = keywordWeights.getOrDefault(token, 1);
                score += keywordWeight;
            }
        }

        return score;
    }
    // Method to load keyword weights (e.g., from configuration or database)
    private Map<String, Integer> loadKeywordWeights() {
        Map<String, Integer> weights = new HashMap<>();
        weights.put("password", 50);
        weights.put("create", 4);
        weights.put("cmr", 50);
        weights.put("cancel", 4);
        weights.put("active", 4);
        weights.put("damage", 3);
        weights.put("vehicle", 3);
        weights.put("pick", 3);
        weights.put("bid", 4);
        // Load more weights as needed
        return weights;
    }
}
