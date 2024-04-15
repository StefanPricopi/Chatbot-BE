package nl.fontys.s3.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.DeleteChatbotFAQ;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteChatbotFAQImpl implements DeleteChatbotFAQ {
    @Autowired
    @Qualifier("chatbotFAQRepositoryImpl")
    private final ChatbotFAQJpaRepository chatbotFAQRepository;
    public void deleteFAQ(int faqId) {
        chatbotFAQRepository.deleteById(faqId);
    }
}
