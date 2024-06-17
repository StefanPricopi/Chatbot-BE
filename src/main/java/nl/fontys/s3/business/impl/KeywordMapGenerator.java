package nl.fontys.s3.business.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;

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

            // Log extracted keywords
            logExtractedKeywords(category, question, keywords);
        }

        return keywordMap;
    }

    private void logExtractedKeywords(String category, String question, List<String> keywords) {
        System.out.println("Category: " + category);
        System.out.println("Question: " + question);
        System.out.println("Keywords: " + keywords);
    }

    public List<String> extractKeywords(String question) {
        // Define non-essential words to be ignored
        Set<String> nonEssentialWords = new HashSet<>(Arrays.asList("can", "i", "where", "my", "the", "what", "to", "how", "is", "a", "am", "at", "in", "on", "of", "for", "with", "from", "by", "you", "your", "they", "their", "that", "this", "these", "those", "it", "its", "him", "his", "her", "hers", "she", "he", "we", "us", "our", "them", "who", "whom", "whose", "which", "when", "why", "but", "or", "and", "nor", "so", "yet", "either", "neither", "both", "all", "any", "some", "more", "most", "few", "fewer", "least"));

        // Split the question by whitespace, remove punctuation, and convert to lowercase
        List<String> tokens = Arrays.stream(question.split("\\s+"))
                .map(token -> token.replaceAll("[^a-zA-Z0-9]", "").toLowerCase())
                .filter(token -> !token.isEmpty() && !nonEssentialWords.contains(token))
                .collect(Collectors.toList());

        // Return unique keywords
        return new ArrayList<>(new HashSet<>(tokens));
    }

    public synchronized Map<String, Integer> generateWordWeightsFromDatabase() {
        Map<String, Integer> wordWeights = new HashMap<>();
        List<ChatbotFAQEntity> allFaqs = faqRepository.findAll();
        System.out.println("Retrieved " + allFaqs.size() + " FAQs from the database");

        Map<String, Integer> wordCounts = allFaqs.stream()
                .flatMap(faq -> Arrays.stream(faq.getQuestion().toLowerCase().split("\\s+")))
                .map(token -> token.replaceAll("[^a-zA-Z0-9]", ""))
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toMap(token -> token, token -> 1, Integer::sum));

        Map<String, Integer> importantKeywords = createImportantKeywordsMap();

        wordCounts.forEach((word, count) -> {
            int weight = importantKeywords.containsKey(word) ? importantKeywords.get(word) * count : count;
            wordWeights.put(word, weight);
        });

        return wordWeights;
    }

    private Map<String, Integer> createImportantKeywordsMap() {
        Map<String, Integer> importantKeywords = new HashMap<>();
        importantKeywords.put("cancel", 10);
        importantKeywords.put("active", 1);
        importantKeywords.put("report", 10);
        importantKeywords.put("damage", 10);
        importantKeywords.put("reset", 10);
        importantKeywords.put("password", 10);
        importantKeywords.put("create", 10);
        importantKeywords.put("account", 10);
        importantKeywords.put("send", 10);
        importantKeywords.put("cmr", 10);
        importantKeywords.put("bid", 10);
        // Add more important keywords and their weights as needed
        return importantKeywords;
    }
}
