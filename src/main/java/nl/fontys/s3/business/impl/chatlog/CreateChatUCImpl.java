package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.CreateChatUC;
import nl.fontys.s3.business.exceptions.UserNotFoundException;
import nl.fontys.s3.domain.ChatDomains.CreateChatResponse;
import nl.fontys.s3.domain.ChatDomains.MessageRequest;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
import nl.fontys.s3.persistence.ChatlogRepositoryFAKE;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.ChatEntity;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateChatUCImpl implements CreateChatUC {
    //xx
    private ChatlogRepoJPA chatlogRepoJPA;
    private UserRepository userRepository;


    @Override
    public CreateChatResponse createChat(long customerId, MessageRequest messageRequest) throws UserNotFoundException {
        // create new chat entity.
        Optional<UserEntity> customer = userRepository.findById(customerId);
        if(customer.isEmpty())
        {
           throw new UserNotFoundException();
        }

        System.out.println(customer.get().getUsername());

        ChatEntity chatEntity = ChatEntity.builder()
                .customer(customer.get())
                .isOpen(false)
                .build();

        MessageEntity tempMsg = MessageEntity.builder()
                .message(messageRequest.getMessage())
                .chat(chatEntity)
                .sendBy(customer.get())
                .build();

        chatEntity.setMessages(List.of(tempMsg));


        ChatEntity savedChat = chatlogRepoJPA.save(chatEntity);

        Long id = savedChat.getId();
        return CreateChatResponse.builder()
                .chat_id(id)
                .build();
    }
}
