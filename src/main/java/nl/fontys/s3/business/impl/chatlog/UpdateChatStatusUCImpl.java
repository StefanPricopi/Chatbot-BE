package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.UpdateStatusUC;
import nl.fontys.s3.domain.ChatDomains.UpdateChatStatusRequest;
import nl.fontys.s3.persistence.ChatlogRepository;
import nl.fontys.s3.persistence.entity.ChatEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateChatStatusUCImpl implements UpdateStatusUC {

    private final ChatlogRepository chatlogRepository;

    @Override
    public void updateStatus(UpdateChatStatusRequest status) {
        Optional<ChatEntity> chat = chatlogRepository.retrieveChat(status.getChatId());
        if(!chat.isEmpty())
        {
            System.out.println("So the status of this request: " + status.isStatus());

            chatlogRepository.updateStatus(status.getChatId(), status.isStatus());
        }
        else {
            System.out.println("Oh no something went wrong");
        }
    }
}
