package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.CreateChatUC;
import nl.fontys.s3.business.exceptions.UserNotFoundException;
import nl.fontys.s3.domain.ChatDomains.CreateChatResponse;
import nl.fontys.s3.domain.ChatDomains.MessageRequest;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.MessageEntity;
import nl.fontys.s3.persistence.entity.UserEntity;
import nl.fontys.s3.persistence.impl.ChatlogRepoImpl;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateChatUCImpl implements CreateChatUC {
    //xx
    private ChatlogRepository chatlogRepository;
    private UserRepository userRepository;


    @Override
    public CreateChatResponse createChat(long customerId, MessageRequest messageRequest) throws UserNotFoundException {
        // create new chat entity.
        Optional<UserEntity> customer = userRepository.findById(customerId);
        if(customer.isEmpty())
        {
           throw new UserNotFoundException();
        }

        Long id = chatlogRepository.createChat(customer.get());
        chatlogRepository.logChat(id, MessageEntity.builder()
                        .message(messageRequest.getMessage())
                        .sendBy(messageRequest.getSendBy())
                        .dateTime(messageRequest.getDateTime())
                .build());
        return CreateChatResponse.builder()
                .chat_id(id)
                .build();
    }
}
