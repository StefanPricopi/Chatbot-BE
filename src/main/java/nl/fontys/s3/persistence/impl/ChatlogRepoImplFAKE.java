package nl.fontys.s3.persistence.impl;

import nl.fontys.s3.domain.ChatDomains.SendByDTO;
import nl.fontys.s3.persistence.ChatlogRepositoryFAKE;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ChatlogRepoImplFAKE implements ChatlogRepositoryFAKE {

    /// TEMP Chatsaves instead of DB atm
    private final List<ChatEntity>  chatEntities;

    public ChatlogRepoImplFAKE()
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
        Set<String> customerRoles = new HashSet<>();
        customerRoles.add("CUSTOMER");
        Set<String> customerServiceRoles = new HashSet<>();
        customerRoles.add("CUSTOMER_SERVICE");

        SendByDTO tempCustomer = SendByDTO.builder()
                .userId(1L)
                .username("Demo Customer")
                .roles(customerRoles)
                .email("DemoCustomer@gmail.com")
                .build();

        SendByDTO tempBot = SendByDTO.builder()
                .userId(2L)
                .username("Demo BOT RESP")
                .roles(customerServiceRoles)
                .email("TestCS@gmail.com")
                .build();



        List<MessageEntity> msgTst = new ArrayList<>();
        msgTst.add(MessageEntity.builder()
                .sendBy(null)
                .id(1)
                .message("Need some help Demo")
                .build());

        msgTst.add(MessageEntity.builder()
                .sendBy(null)
                .id(2)
                .message("What can I help you with?")
                .build());

        msgTst.add(MessageEntity.builder()
                .sendBy(null)
                .id(3)
                .message("No clue, just need to see if this works!")
                .build());

        UserEntity tmpUser = UserEntity.builder()
                .email("Test@gmail.com")
                .roles(customerServiceRoles.toString())
                .username("Test")
                .password("123")
                .build();

        chatEntities.add(ChatEntity.builder()
                .customer(tmpUser)
                .id(1)
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
                        .dateTime(LocalDateTime.now())
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
    public void updateStatus(int id, boolean status) {
        Optional<ChatEntity> tmp = chatEntities.stream().filter(chatEntity -> chatEntity.getId() == id).findFirst();
        if(!tmp.isEmpty())
        {
            tmp.get().setOpen(status);
        }
        else
        {
            System.out.println("Oh, chat with that id doesn't exist");
        }
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
