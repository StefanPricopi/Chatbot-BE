package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.domain.ChatbotFAQ;
import nl.fontys.s3.domain.GetAllChatbotFAQResponse;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetChatbotFAQImpl implements GetChatbotFAQ {

    //@Autowired
    @Qualifier("chatbotFAQJpaRepository")
    private ChatbotFAQJpaRepository faqRepository;

    // Keyword mapping
    private final Map<String, List<String>> keywordMap = Map.of(
            "reset", List.of("reset", "change", "update", "modify", "password"),
            "change", List.of("change", "update", "modify", "password"),
            "update", List.of("update", "modify", "password")
            // Add more mappings as needed
    );

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
    public List<ChatbotFAQEntity> getFAQsByKeyword(String keyword) {
        return faqRepository.findByQuestionContainingIgnoreCase(keyword);
    }
}