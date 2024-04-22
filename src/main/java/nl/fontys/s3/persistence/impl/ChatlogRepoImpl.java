package nl.fontys.s3.persistence.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatlogRepoImpl implements ChatlogRepository {

    /// TEMP Chatsaves instead of DB atm
    private final List<ChatEntity>  chatEntities;

    public ChatlogRepoImpl()
    {
        chatEntities = new ArrayList<>();

        demoData();
    }


    private boolean doesExists(long id)
    {
        Optional<ChatEntity> tmp = chatEntities.stream()
                .filter(chatEntity -> chatEntity.getId() == id)
                .findFirst();

        return !tmp.isEmpty();
    }

    private void demoData()
    {
        SendByDTO tempCustomer = SendByDTO.builder()
                .userId(1L)
                .userName("Demo Customer")
                .role("Customer")
                .email("DemoCustomer@gmail.com")
                .build();

        SendByDTO tempBot = SendByDTO.builder()
                .userId(2L)
                .userName("Demo BOT RESP")
                .role("Customer Service")
                .email("TestCS@gmail.com")
                .build();



        List<MessageEntity> msgTst = new ArrayList<>();
        msgTst.add(MessageEntity.builder()
                .sendBy(tempCustomer)
                .message_id(1)
                .message("Need some help Demo")
                .build());

        msgTst.add(MessageEntity.builder()
                .sendBy(tempBot)
                .message_id(2)
                .message("What can I help you with?")
                .build());

        msgTst.add(MessageEntity.builder()
                .sendBy(tempCustomer)
                .message_id(3)
                .message("No clue, just need to see if this works!")
                .build());

        UserEntity tmpUser = UserEntity.builder()
                .email("Test@gmail.com")
                .role("customer service")
                .userName("Test")
                .password("123")
                .build();

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(1)
                .messages(msgTst)
                        .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(2)
                .messages(msgTst)
                        .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(3)
                .messages(msgTst)
                .isOpen(true)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(4)
                .messages(msgTst)
                .isOpen(true)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(5)
                .messages(msgTst)
                .isOpen(true)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(6)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(7)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(8)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(9)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(10)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(11)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(12)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(13)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(14)
                .messages(msgTst)
                .isOpen(false)
                .build());

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(15)
                .messages(msgTst)
                .isOpen(false)
                .build());

    }


    @Override
    public Long createChat(UserEntity user) {

        Long chat_id = chatEntities.size() + 1l;

        chatEntities.add(ChatEntity.builder()
                        // Underneath is temporary!
                        .id(chat_id)
                        .customer(user)
                        .messages(new ArrayList<>())
                .build());

        return chat_id;
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
    public List<ChatEntity> retrieveAllChats() {
        return chatEntities;
    }

    @Override
    public void deleteChat(long chatId) throws Exception {
        try
        {
            chatEntities.removeIf(chatEntity -> chatEntity.getId() == chatId);
        }
        catch(Exception e)
        {
            throw new Exception("chat doesn't exist.");
        }
    }
}
