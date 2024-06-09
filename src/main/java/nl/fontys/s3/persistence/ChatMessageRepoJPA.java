package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepoJPA extends JpaRepository<MessageEntity, Long> {
}
