package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.DeleteChatbotFAQ;
import nl.fontys.s3.persistence.ChatbotFAQRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteChatbotFAQImpl implements DeleteChatbotFAQ {
    private final ChatbotFAQRepository chatbotFAQRepository;
    @Override
    public void deleteFAQ(Long FAQId) {
        this.chatbotFAQRepository.deleteByFAQid(FAQId);
    }
}
