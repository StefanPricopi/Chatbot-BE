package nl.fontys.s3.persistence;


import nl.fontys.s3.persistence.entity.ChatbotFAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotFAQJpaRepository extends JpaRepository<ChatbotFAQEntity, Integer> {
     List<ChatbotFAQEntity> findByQuestionContainingIgnoreCase(String keyword);
}