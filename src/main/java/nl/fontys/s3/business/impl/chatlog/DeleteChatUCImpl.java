package nl.fontys.s3.business.impl.chatlog;

import lombok.AllArgsConstructor;
import nl.fontys.s3.business.ChatLogPck.DeleteChatLogUC;
import nl.fontys.s3.persistence.ChatlogRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeleteChatUCImpl implements DeleteChatLogUC {

    private ChatlogRepository chatlogRepository;

    @Override
    public void deleteChat(long id) {
        try
        {
            chatlogRepository.deleteChat(id);
        }
        catch(Exception e)
        {

        }
    }
}
