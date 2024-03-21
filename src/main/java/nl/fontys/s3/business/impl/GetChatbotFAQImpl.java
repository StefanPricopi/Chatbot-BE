package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.GetChatbotFAQ;
import nl.fontys.s3.domain.ChatbotFAQ;
import nl.fontys.s3.domain.GetAllChatbotFAQResponse;
import nl.fontys.s3.persistence.ChatbotFAQRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetChatbotFAQImpl implements GetChatbotFAQ {
    private final ChatbotFAQRepository faqRepository;
    @Override
    public GetAllChatbotFAQResponse getFAQ() {
        List<ChatbotFAQEntity> results = faqRepository.findAll();

        final GetAllChatbotFAQResponse response = new GetAllChatbotFAQResponse();
        List<ChatbotFAQ> faqs = results.stream()
                .map(FAQConverter::convert)
                .toList();
        response.setChatbotFAQS(faqs);

        return response;
    }
}
