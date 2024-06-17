package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatlogRepoJPA extends JpaRepository<ChatEntity, Long> {
}
