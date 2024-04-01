package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;

import java.util.List;
import java.util.Optional;

public interface ChatlogRepository {

    void createChat(long customerId);

    void logChat(long chatId, MessageEntity msg);

    Optional<ChatEntity> retrieveChat(long chatId);

    List<ChatEntity> retrieveAllChats();

    void deleteChat(long chatId) throws Exception;
}
