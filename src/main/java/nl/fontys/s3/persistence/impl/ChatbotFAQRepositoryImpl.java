package nl.fontys.s3.persistence.impl;

import nl.fontys.s3.persistence.ChatbotFAQRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class ChatbotFAQRepositoryImpl implements ChatbotFAQRepository {

    private static int NEXT_ID = 1;

    private final List<ChatbotFAQEntity> savedFAQs;

    public ChatbotFAQRepositoryImpl(List<ChatbotFAQEntity> savedFAQs) {
        this.savedFAQs = savedFAQs;
    }

    @Override
    public ChatbotFAQEntity save(ChatbotFAQEntity faq) {
        if (faq.getFAQID() == -1) {
            faq.setFAQID(NEXT_ID);
            NEXT_ID++;
            this.savedFAQs.add(faq);
        }
        return faq;
    }

    @Override
    public List<ChatbotFAQEntity> findAll() {
        return Collections.unmodifiableList(this.savedFAQs);
    }

    @Override
    public Optional<ChatbotFAQEntity> findById(int faqID) {
        return this.savedFAQs.stream()
                .filter(faqEntity -> faqEntity.getFAQID() == faqID)
                .findFirst();
    }

    @Override
    public void deleteByFAQid(int faqID) {
        this.savedFAQs.removeIf(faqEntity -> faqEntity.getFAQID()== faqID);
    }
}
