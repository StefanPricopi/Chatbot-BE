package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.LogChatUC;
import nl.fontys.s3.domain.ChatDomains.MessageRequest;
import nl.fontys.s3.persistence.ChatMessageRepoJPA;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
import nl.fontys.s3.persistence.ChatlogRepositoryFAKE;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LogChatUCImpl implements LogChatUC {

    private ChatMessageRepoJPA chatlogRepositoryJPA;
    private ChatlogRepoJPA chatlogRepoJPA;
    private UserRepository userRepository;

    @Override
    public void logChat(long chat_id, MessageRequest msg) throws Exception {

        System.out.print("So the date time we got: " + msg.getDateTime());
        Optional<ChatEntity> foundChat = chatlogRepoJPA.findById(chat_id);
        Optional<UserEntity> foundUser = userRepository.findById(msg.getUser_id());

        if(foundChat.isEmpty())
        {
            throw new Exception("Chat not found");
        }

        if(foundUser.isEmpty())
        {
            throw new Exception("User doesn't exist!");
        }

        MessageEntity messageEntity = MessageEntity.builder()
                .chat(foundChat.get())
                .message(msg.getMessage())
                .sendBy(foundUser.get())
                .build();

        chatlogRepositoryJPA.save(messageEntity);
    }
}
