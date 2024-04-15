package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.CreateChatUC;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.UserRepository;
import nl.fontys.s3.persistence.entity.UserEntity;
import nl.fontys.s3.persistence.impl.ChatlogRepoImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateChatUCImpl implements CreateChatUC {

    private ChatlogRepository chatlogRepository;
    private UserRepository userRepository;


    @Override
    public void createChat(long customerId) {
        // create new chat entity.
        Optional<UserEntity> customer = userRepository.findById(customerId);
        chatlogRepository.createChat(customer.get());
    }
}
