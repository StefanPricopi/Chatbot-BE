package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.CreateChatUC;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.impl.ChatlogRepoImpl;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateChatUCImpl implements CreateChatUC {

    private ChatlogRepository chatlogRepository;

    @Override
    public void createChat(long customerId) {
        // create new chat entity.
        chatlogRepository.createChat(customerId);
    }
}
