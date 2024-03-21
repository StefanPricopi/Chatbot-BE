package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.UpdateChatbotFAQ;
import nl.fontys.s3.domain.UpdateChatbotFAQRequest;
import nl.fontys.s3.persistence.ChatbotFAQRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class UpdateChatbotFAQImpl implements UpdateChatbotFAQ {
    private final ChatbotFAQRepository faqRepository;

    @Override
    public void updateChatbotFAQ(UpdateChatbotFAQRequest request) {
        Optional<ChatbotFAQEntity> faqOptional = faqRepository.findById(request.getFAQID());
        if (faqOptional.isPresent()) {
            ChatbotFAQEntity faq = faqOptional.get();
            updateFields(request, faq);
        } else {

        }
    }
    private void updateFields(UpdateChatbotFAQRequest request, ChatbotFAQEntity faq) {
        faq.setQuestion(request.getQuestion());
        faq.setAnswer(request.getAnswer());
        faq.setCategory(request.getCategory());
        faqRepository.save(faq);
    }
}
