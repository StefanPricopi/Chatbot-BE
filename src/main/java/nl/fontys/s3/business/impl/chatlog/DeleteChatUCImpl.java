package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.DeleteChatLogUC;
import nl.fontys.s3.persistence.ChatlogRepoJPA;
import nl.fontys.s3.persistence.entity.ChatEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class DeleteChatUCImpl implements DeleteChatLogUC {

    private ChatlogRepoJPA chatlogRepo;

    @Override
    public void deleteChat(long id) throws Exception {
        Optional<ChatEntity> chat = chatlogRepo.findById(id);

        if(chat.isEmpty())
        {
            throw new Exception("chat doesn't exist");
        }
//
//            chat.get().setCustomer(null);
//            chat.get().setMessages(null);
//            chatlogRepo.save(chat.get());

        chatlogRepo.deleteById(id);

    }
}
