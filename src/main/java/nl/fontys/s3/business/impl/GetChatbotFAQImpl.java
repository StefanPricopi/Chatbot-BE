package nl.fontys.s3.business.impl;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.domain.ChatbotFAQ;
import nl.fontys.s3.domain.chatbotFAQ.GetAllChatbotFAQResponse;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetChatbotFAQImpl implements GetChatbotFAQ {

    private final ChatbotFAQJpaRepository faqRepository;
    private final KeywordMapGenerator keywordMapGenerator;
    private final BidService bidService;

    private Map<String, List<String>> keywordMap;
    private Map<String, Integer> keywordWeights;

    @PostConstruct
    public void init() {
        keywordMap = keywordMapGenerator.generateKeywordMapFromDatabase();
        keywordWeights = keywordMapGenerator.generateWordWeightsFromDatabase();
        System.out.println("Keyword maps initialized successfully");
    }

    @Autowired
    public GetChatbotFAQImpl(ChatbotFAQJpaRepository faqRepository, KeywordMapGenerator keywordMapGenerator, BidService bidService) {
        this.faqRepository = faqRepository;
        this.keywordMapGenerator = keywordMapGenerator;
        this.bidService = bidService;
    }

    @Override
    public GetAllChatbotFAQResponse getFAQ() {
        List<ChatbotFAQEntity> results = faqRepository.findAll();
        List<ChatbotFAQ> faqs = results.stream()
                .map(FAQConverter::convert)
                .collect(Collectors.toList());

        GetAllChatbotFAQResponse response = new GetAllChatbotFAQResponse();
        response.setChatbotFAQS(faqs);

        System.out.println("Retrieved " + faqs.size() + " FAQs from the database");

        keywordMap.forEach((category, keywords) -> {
            faqs.stream()
                    .filter(faq -> faq.getCategory().equals(category))
                    .forEach(faq -> {
                        System.out.println("Category: " + category);
                        System.out.println("Question: " + faq.getQuestion());
                        System.out.println("Keywords: " + keywords);
                    });
        });

        return response;
    }

    @Override
    public List<ChatbotFAQEntity> getFAQsByKeyword(String keyword) {
        // Implementation goes here
        return null;
    }

    @Override
    public String processUserQuery(String userInput, int userId, int attempts, boolean endOfConversation) {
        // Preprocess user input (optional)
        String processedInput = userInput.toLowerCase();

        // Check if the query is bid-related
        String response;
        if (isBidRelated(processedInput)) {
            response = bidService.getAnswerForBidQuestion(processedInput, userId);
        } else {
            // Calculate the best matching FAQ for the processed input
            response = calculateBestMatchingFAQ(processedInput);
        }

        if (response == null || response.equals("Sorry, I couldn't find an answer to your question.")) {
            attempts++;
            if (attempts >= 3) {
                return "I don’t understand you, can I connect you to an employee?";
            } else {
                return "I couldn’t find an answer to your question. Can you please provide more details? [Attempts: " + attempts + "]";
            }
        } else {
            if (endOfConversation) {
                return response + "\n\nWas this helpful? [YES/NO]";
            } else {
                return response;
            }
        }
    }

    private boolean isBidRelated(String input) {
        // Define bid-related keywords
        List<String> bidKeywords = List.of("bid", "bids", "auction", "offer");

        // Check if the input contains any bid-related keywords
        for (String keyword : bidKeywords) {
            if (input.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String calculateBestMatchingFAQ(String input) {
        // Preprocess user input (optional)
        String processedInput = input.toLowerCase();

        // Track the FAQ with the maximum matching score
        ChatbotFAQEntity maxMatchedFAQ = null;
        int maxMatchingScore = 0;

        // Retrieve all FAQs from the database
        List<ChatbotFAQEntity> allFaqs = faqRepository.findAll();

        // Iterate through all FAQs
        for (ChatbotFAQEntity faq : allFaqs) {
            // Calculate matching score for the current FAQ
            int matchingScore = calculateMatchingScore(processedInput, faq.getQuestion());

            // Log the matching score for the current FAQ
            System.out.println("Matching score for FAQ '" + faq.getQuestion() + "' is: " + matchingScore);

            // Update maxMatchedFAQ if the current FAQ has a higher matching score
            if (matchingScore > maxMatchingScore) {
                maxMatchingScore = matchingScore;
                maxMatchedFAQ = faq;
            }
        }

        // If a FAQ with matching keywords is found
        if (maxMatchedFAQ != null) {
            return maxMatchedFAQ.getAnswer();
        }

        return "Sorry, I couldn't find an answer to your question.";
    }

    private int calculateMatchingScore(String input, String question) {
        List<String> userKeywords = keywordMapGenerator.extractKeywords(input);
        List<String> faqKeywords = keywordMapGenerator.extractKeywords(question);

        // Calculate the intersection of keywords between user query and FAQ
        userKeywords.retainAll(faqKeywords);

        // The matching score is the size of the intersection
        return userKeywords.size();
    }
}
