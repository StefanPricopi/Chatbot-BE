package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.UpdateStatusUC;
import nl.fontys.s3.domain.ChatDomains.UpdateChatStatusRequest;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
import nl.fontys.s3.persistence.ChatlogRepositoryFAKE;
import nl.fontys.s3.persistence.entity.ChatEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateChatStatusUCImpl implements UpdateStatusUC {

    private final ChatlogRepoJPA chatlogRepositoryFAKE;

    @Override
    public void updateStatus(UpdateChatStatusRequest status) throws Exception {
        Optional<ChatEntity> chat = chatlogRepositoryFAKE.findById(status.getChatId());
        if(chat.isEmpty())
        {
            throw new Exception("Chat not found");
        }

        chat.get().setOpen(status.isStatus());
        chatlogRepositoryFAKE.save(chat.get());
    }
}
