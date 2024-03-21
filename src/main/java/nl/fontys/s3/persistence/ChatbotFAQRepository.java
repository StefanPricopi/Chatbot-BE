package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;

import java.util.List;
import java.util.Optional;

public interface ChatbotFAQRepository {
    ChatbotFAQEntity save(ChatbotFAQEntity faq);
    List<ChatbotFAQEntity> findAll();
    Optional<ChatbotFAQEntity> findById(int faqID);
    void deleteByFAQid(int faqID);
}
