package nl.fontys.s3.persistence.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatlogRepoImpl implements ChatlogRepository {

    /// TEMP Chatsaves instead of DB atm
    private List<ChatEntity> chatEntities;

    public ChatlogRepoImpl()
    {
        chatEntities = new ArrayList<>();
    }


    @Override
    public void createChat(long customerId) {
        chatEntities.add(ChatEntity.builder()
                        .id(1)
                        .customer_id(customerId)
                .build());
    }

    @Override
    public void logChat(long chatId, MessageEntity msg) {
        // Finds correct chat object then adds it to that msg array
        Optional<ChatEntity> tmp = chatEntities.stream().filter(chatEntity -> chatEntity.getId() == chatId).findFirst();
        if(!tmp.isEmpty())
        {
            tmp.get().getMessages().add(msg);
        }
    }

    @Override
    public Optional<ChatEntity> retrieveChat(long chatId) {
        return chatEntities.stream()
                .filter(chatEntity -> chatEntity.getId() == chatId)
                .findFirst();
    }

    @Override
    public List<ChatEntity> retrieveAllChats(long chatId) {
        return chatEntities;
    }

    @Override
    public void deleteChat(long chatId) {
        chatEntities.removeIf(chatEntity -> chatEntity.getId() == chatId);
    }
}
