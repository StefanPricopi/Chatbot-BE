package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.UpdateChatbotFAQ;
import nl.fontys.s3.domain.UpdateChatbotFAQRequest;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class UpdateChatbotFAQImpl implements UpdateChatbotFAQ {
    @Autowired
    @Qualifier("chatbotFAQRepositoryImpl")
    private final ChatbotFAQJpaRepository faqRepository;

    @Override
    public void updateChatbotFAQ(UpdateChatbotFAQRequest request) {
        Optional<ChatbotFAQEntity> faqOptional = faqRepository.findById(Math.toIntExact(request.getFAQID()));
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
