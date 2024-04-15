package nl.fontys.s3.persistence.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.fontys.s3.persistence.ChatbotFAQJpaRepository;
import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public abstract class  ChatbotFAQRepositoryImpl implements ChatbotFAQJpaRepository {

    private final ChatbotFAQJpaRepository repository;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public ChatbotFAQRepositoryImpl(@Qualifier("chatbotFAQJpaRepository") ChatbotFAQJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public <S extends ChatbotFAQEntity> S save(S faq) {
        return repository.save(faq);
    }

    @Override
    public List<ChatbotFAQEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ChatbotFAQEntity> findById(Integer faqID) {
        return repository.findById(faqID);
    }

    @Override
    public void deleteById(Integer faqID) {
        repository.deleteById(faqID);
    }

    @Override
    public List<ChatbotFAQEntity> findByQuestionContainingIgnoreCase(String keyword) {
        return repository.findByQuestionContainingIgnoreCase(keyword);
    }




}
