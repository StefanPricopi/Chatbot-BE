package nl.fontys.s3.persistence;

import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ChatlogRepositoryFAKE {

    Long createChat(UserEntity user);

    void logChat(long chatId, MessageEntity msg);

    Optional<ChatEntity> retrieveChat(long chatId);

    List<ChatEntity> retrieveAllChats();

    void updateStatus(int id, boolean status);

    void deleteChat(long chatId) throws Exception;
}
